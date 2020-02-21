/*********************************************************************

    Chat server: accept chat messages from clients.
    
    Sender name and GPS coordinates are encoded
    in the messages, and stripped off upon receipt.

    Copyright (c) 2017 Stevens Institute of Technology

**********************************************************************/
package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.sql.Date;

import edu.stevens.cs522.base.DatagramSendReceive;
import edu.stevens.cs522.base.DateUtils;
import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.entities.Message;
import edu.stevens.cs522.chatserver.entities.Peer;
import edu.stevens.cs522.chatserver.providers.ChatProvider;

public class ChatServer extends Activity implements OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

	final static public String TAG = ChatServer.class.getCanonicalName();
		
	/*
	 * Socket used both for sending and receiving
	 */
	private DatagramSendReceive serverSocket;
//  private DatagramSocket serverSocket;

	/*
	 * True as long as we don't get socket errors
	 */
	private boolean socketOK = true; 

    /*
     * UI for displayed received messages
     */
	private ListView messageList;

    private SimpleCursorAdapter messagesAdapter;

    private Button next;

    static final private int LOADER_ID = 1;

	/*
	 * Called when the activity is first created. 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        /**
         * Let's be clear, this is a HACK to allow you to do network communication on the messages thread.
         * This WILL cause an ANR, and is only provided to simplify the pedagogy.  We will see how to do
         * this right in a future assignment (using a Service managing background threads).
         */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            /*
             * Get port information from the resources.
             */
            int port = getResources().getInteger(R.integer.app_port);

            // serverSocket = new DatagramSocket(port);

            serverSocket = new DatagramSendReceive(port);

        } catch (Exception e) {
            throw new IllegalStateException("Cannot open socket", e);
        }

        setContentView(R.layout.messages);

        // TODO use SimpleCursorAdapter (with flags=0) to display the messages received.
        messageList = findViewById(R.id.message_list);
        String [] from = new String[] {MessageContract.SENDER, MessageContract.MESSAGE_TEXT};
        int [] to = new int[] {android.R.id.text1, android.R.id.text2};
        messagesAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_expandable_list_item_2,null,from,to,0);
        messageList.setAdapter(messagesAdapter);

        // TODO bind the button for "next" to this activity as listener
        next = findViewById(R.id.next);
        next.setOnClickListener(this);
        // TODO use loader manager to initiate a query of the database
        getLoaderManager().initLoader(LOADER_ID,null,this);

	}



    public void onClick(View v) {
		
		byte[] receiveData = new byte[1024];

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		try {

			serverSocket.receive(receivePacket);
			Log.i(TAG, "Received a packet");

			InetAddress sourceIPAddress = receivePacket.getAddress();
			Log.i(TAG, "Source IP Address: " + sourceIPAddress);

			String msgContents[] = new String(receivePacket.getData(), 0, receivePacket.getLength()).split(":");

			Message message = new Message();
            String sender = message.sender = msgContents[0];
            message.timestamp = DateUtils.now();
            message.messageText = msgContents[1];

            Log.i(TAG, "Received from " + message.sender + ": " + msgContents[1]);


            /*
             * TODO upsert the peer and message into the content provider.
             */
            // For this assignment, OK to do CP insertion on the main thread.
                ContentValues messageData = new ContentValues();
                message.writeToProvider(messageData);
                getContentResolver().insert(MessageContract.CONTENT_URI,messageData);
                ContentValues peerData = new ContentValues();
                Peer peer = new Peer();
                peer.name = sender;
                peer.timestamp = DateUtils.now();
                peer.address = sourceIPAddress;
                peer.writeToProvider(peerData);
                getContentResolver().insert(PeerContract.CONTENT_URI,peerData);
                getLoaderManager().restartLoader(LOADER_ID,null,this);
            /*
             * End TODO
             */


        } catch (Exception e) {
			
			Log.e(TAG, "Problems receiving packet: " + e.getMessage(), e);
			socketOK = false;
		} 

	}

	/*
	 * Close the socket before exiting application
	 */
    public void closeSocket() {
        if (serverSocket != null) {
            serverSocket.close();
            serverSocket = null;
        }
    }
	/*
	 * If the socket is OK, then it's running
	 */
	boolean socketIsOK() {
		return socketOK;
	}

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        // TODO use a CursorLoader to initiate a query on the database
        CursorLoader cursorLoader = new CursorLoader(this,MessageContract.CONTENT_URI,null,null,null,null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        // TODO populate the UI with the result of querying the provider
       messagesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        // TODO reset the UI when the cursor is empty
        messagesAdapter.swapCursor(null);
    }

    public void onDestroy() {
        super.onDestroy();
        closeSocket();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // TODO inflate a menu with PEERS option
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.chatserver_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {

            case R.id.peers:
                // TODO PEERS provide the UI for viewing list of peers
                Intent intent = new Intent(ChatServer.this,ViewPeersActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return false;
    }

}