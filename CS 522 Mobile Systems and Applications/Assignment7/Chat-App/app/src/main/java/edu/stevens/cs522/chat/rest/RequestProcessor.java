package edu.stevens.cs522.chat.rest;

import android.content.Context;
import android.net.Uri;
import android.os.ResultReceiver;

import edu.stevens.cs522.chat.entities.ChatMessage;
import edu.stevens.cs522.chat.entities.Peer;
import edu.stevens.cs522.chat.managers.MessageManager;
import edu.stevens.cs522.chat.managers.PeerManager;
import edu.stevens.cs522.chat.settings.Settings;

/**
 * Created by dduggan.
 */

public class RequestProcessor {

    private Context context;

    private RestMethod restMethod;

    public RequestProcessor(Context context) {
        this.context = context;
        this.restMethod = new RestMethod(context);
    }

    public Response process(Request request) {
        return request.process(this);
    }

    public Response perform(RegisterRequest request) {

        Response response = restMethod.perform(request);
        PeerManager peerManager = new PeerManager(context);

        if (response instanceof RegisterResponse) {
            // TODO update the user name and sender id in settings, updated peer record PK
            Settings.saveSenderId(context, (((RegisterResponse) response).getSenderId()));
            Settings.saveChatName(context, request.chatname);

            Peer peer = new Peer();
            peer.name = Settings.getChatName(context);
            peer.latitude = request.latitude;
            peer.longitude = request.longitude;
            peer.timestamp = request.timestamp;
            peer.id = Settings.getSenderId(context);

            peerManager.persist(peer);
        }

        return response;
    }

    public Response perform(PostMessageRequest request) {
        // TODO insert the message into the local database
        MessageManager messageManager = new MessageManager(context);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.chatRoom = request.chatRoom;
        chatMessage.latitude = request.latitude;
        chatMessage.longitude = request.longitude;
        chatMessage.messageText = request.message;
        chatMessage.timestamp = request.timestamp;
        chatMessage.senderId = Settings.getSenderId(context);
        chatMessage.sender = Settings.getChatName(context);

        long messageId = messageManager.persist(chatMessage);

        Response response = restMethod.perform(request);
        if (response instanceof PostMessageResponse) {
            // TODO update the message in the database with the sequence number
            long seqNum =  ((PostMessageResponse) response).getMessageId();
            chatMessage.chatRoom = request.chatRoom;
            chatMessage.latitude = request.latitude;
            chatMessage.longitude = request.longitude;
            chatMessage.messageText = request.message;
            chatMessage.timestamp = request.timestamp;
            chatMessage.senderId = request.senderId;
            chatMessage.sender = Settings.getChatName(context);
            chatMessage.seqNum = seqNum;

            messageManager.updateSequenceNumber(chatMessage, messageId);
        }
        return response;
    }

}
