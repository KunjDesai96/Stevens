package edu.stevens.cs522.chat.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import edu.stevens.cs522.base.DatagramSendReceive;
import edu.stevens.cs522.base.DateUtils;
import edu.stevens.cs522.base.InetAddressUtils;
import edu.stevens.cs522.chat.R;
import edu.stevens.cs522.chat.async.IContinue;
import edu.stevens.cs522.chat.entities.Message;
import edu.stevens.cs522.chat.entities.Peer;
import edu.stevens.cs522.chat.managers.MessageManager;
import edu.stevens.cs522.chat.managers.PeerManager;

import static android.app.Activity.RESULT_OK;


public class ChatService extends Service implements IChatService {

    protected static final String TAG = ChatService.class.getCanonicalName();

    protected static final String SEND_TAG = "ChatSendThread";

    protected static final String RECEIVE_TAG = "ChatReceiveThread";

    protected IBinder binder = new ChatBinder();

    protected SendHandler sendHandler;

    protected Thread receiveThread;

    protected DatagramSendReceive chatSocket;

    protected boolean socketOK = true;

    protected boolean finished = false;

    protected PeerManager peerManager;

    protected MessageManager messageManager;

    protected int chatPort;

    @Override
    public void onCreate() {

        chatPort = this.getResources().getInteger(R.integer.app_port);

        peerManager = new PeerManager(this);
        messageManager = new MessageManager(this);

        try {
            chatSocket = new DatagramSendReceive(chatPort);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to init client socket.", e);
        }

        // TODO initialize the thread that sends messages
        HandlerThread sendThread = new HandlerThread(TAG, Process.THREAD_PRIORITY_BACKGROUND);
        sendThread.start();
        sendHandler = new SendHandler(sendThread.getLooper());
        // end TODO

        receiveThread = new Thread(new ReceiverThread());
        receiveThread.start();
    }

    @Override
    public void onDestroy() {
        finished = true;
        sendHandler.getLooper().getThread().interrupt();  // No-op?
        sendHandler.getLooper().quit();
        receiveThread.interrupt();
        chatSocket.close();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public final class ChatBinder extends Binder {

        public IChatService getService() {
            return ChatService.this;
        }

    }

    @Override
    public void send(InetAddress destAddress, int destPort, String sender, String messageText, ResultReceiver receiver) {
        android.os.Message message = sendHandler.obtainMessage();
        // TODO send the message to the sending thread (add a bundle with params)
        Bundle bundle = new Bundle();
        bundle.putString(SendHandler.DEST_ADDRESS, String.valueOf(destAddress));
        bundle.putInt(SendHandler.DEST_PORT, destPort);
        bundle.putString(SendHandler.CHAT_NAME, sender);
        bundle.putString(SendHandler.CHAT_MESSAGE, messageText);
        bundle.putSerializable(SendHandler.RECEIVER, (ResultReceiverWrapper) receiver);
        message.setData(bundle);
        sendHandler.sendMessage(message);
    }


    private final class SendHandler extends Handler {

        public static final String CHAT_NAME = "edu.stevens.cs522.chat.services.extra.CHAT_NAME";
        public static final String CHAT_MESSAGE = "edu.stevens.cs522.chat.services.extra.CHAT_MESSAGE";
        public static final String DEST_ADDRESS = "edu.stevens.cs522.chat.services.extra.DEST_ADDRESS";
        public static final String DEST_PORT = "edu.stevens.cs522.chat.services.extra.DEST_PORT";
        public static final String RECEIVER = "edu.stevens.cs522.chat.services.extra.RECEIVER";

        public SendHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(android.os.Message message) {

            try {
                InetAddress destAddr;

                int destPort;

                byte[] sendData;

                ResultReceiver receiver;

                Bundle data = message.getData();


                // TODO get data from message (including result receiver)
                destPort = data.getInt(DEST_PORT);
                String sender = data.getString(CHAT_NAME);
                String messageText = data.getString(CHAT_MESSAGE);
                String finalMessage = sender+":"+messageText;
                // End todo

                sendData = finalMessage.getBytes();

                destAddr = InetAddress.getByName(data.getString(DEST_ADDRESS).substring(1));

                Log.d(TAG, String.format("Sending data from address %s:%d", chatSocket.getInetAddress(), chatSocket.getPort()));

                DatagramPacket sendPacket = new DatagramPacket(sendData,
                        sendData.length, destAddr, destPort);
                chatSocket.send(sendPacket);

                Log.i(TAG, "Sent packet: " + sender);
                ResultReceiverWrapper receiverWrapper = (ResultReceiverWrapper) data.getSerializable(RECEIVER);
                receiver = new ResultReceiver(this);
                receiver.send(RESULT_OK, null);
                receiverWrapper.onReceiveResult(RESULT_OK,null);

            } catch (UnknownHostException e) {
                Log.e(TAG, "Unknown host exception"+ e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IO exception", e);
            }

        }
    }

    private final class ReceiverThread implements Runnable {

        public void run() {

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            while (!finished && socketOK) {

                try {

                    chatSocket.receive(receivePacket);
                    Log.i(TAG, "Received a packet");

                    InetAddress sourceIPAddress = receivePacket.getAddress();
                    Log.i(TAG, "Source IP Address: " + sourceIPAddress);

                    String msgContents[] = new String(receivePacket.getData(), 0, receivePacket.getLength()).split(":");

                    final Message message = new Message();
                    message.sender = msgContents[0];
                    message.timestamp = DateUtils.now();
                    message.messageText = msgContents[1];

                    Log.i(TAG, "Received from " + message.sender + ": " + message.timestamp);

                    Peer sender = new Peer();
                    sender.name = message.sender;
                    sender.timestamp = DateUtils.now();
                    sender.address = receivePacket.getAddress();

                    // TODO upsert the peer and message into the content provider.
                    // For this assignment, must use synchronous manager operations
                    // (no callbacks) because now we are on a background thread

                    peerManager.persist(sender);
                    messageManager.persist(message);

                } catch (Exception e) {

                    Log.e(TAG, "Problems receiving packet.", e);
                    socketOK = false;
                }

            }

        }

    }

}
