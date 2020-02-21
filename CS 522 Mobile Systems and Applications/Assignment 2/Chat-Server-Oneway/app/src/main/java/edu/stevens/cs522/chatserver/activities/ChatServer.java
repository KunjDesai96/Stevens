/*********************************************************************

 Chat server: accept chat messagesAdapter from clients.

 Sender name and GPS coordinates are encoded
 in the messagesAdapter, and stripped off upon receipt.

 Copyright (c) 2017 Stevens Institute of Technology

 **********************************************************************/
package edu.stevens.cs522.chatserver.activities;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.sql.Date;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import edu.stevens.cs522.base.DatagramSendReceive;
import edu.stevens.cs522.base.DateUtils;
import edu.stevens.cs522.base.InetAddressUtils;
import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.entities.Peer;

public class ChatServer extends Activity  {

    final static public String TAG = ChatServer.class.getCanonicalName();

    /*
     * Socket used both for sending and receiving
     */
    // private DatagramSocket serverSocket;
    private DatagramSendReceive serverSocket;

    /*
     * True as long as we don't get socket errors
     */
    private boolean socketOK = true;

    private ArrayList<Peer> peers;

    /*
     * TODO: Declare a listview for messagesAdapter, and an adapter for displaying messagesAdapter.
     */
    private ArrayList<String> messageArrayList;
    private ListView messageList;
    private ArrayAdapter<String> messageAdapter;
    private Toolbar toolbar;
    public static final String PEERS_KEY = "peers";
    InetAddress sourceIPAddress;
    /*
     * End Todo
     */

    Button next;

    /*
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_messages);

        /**
         * Let's be clear, this is a HACK to allow you to do network communication on the view_messages thread.
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

        // List of peers
        peers = new ArrayList<Peer>();

        /*
         * TODO: Initialize the UI.
         */
        next = findViewById(R.id.next);
        messageList = findViewById(R.id.message_list);
        messageArrayList = new ArrayList<String>();
        messageAdapter = new ArrayAdapter<String> ( this, android.R.layout.simple_list_item_1, messageArrayList );

        toolbar = findViewById(R.id.toolbar);
       // setActionBar(toolbar);
        /*
         * End Todo
         */



    next.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {

            byte[] receiveData = new byte[1024];



            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                Log.d(TAG, "Received a packet");

                sourceIPAddress = receivePacket.getAddress();
                Log.d(TAG, "Source IP Address: " + sourceIPAddress);

                String msgContents[] = new String(receivePacket.getData(), 0, receivePacket.getLength()).split(":");
                String name = msgContents[0];
                String message = msgContents[1];
                Log.d(TAG, "Received from " + name + ": " + message);

                /*
                 * TODO: Add message with sender to the display.
                 */
                messageArrayList.add(name + " : " +message);
                messageList.setAdapter(messageAdapter);
                addPeer(new Peer(name,DateUtils.now(),sourceIPAddress,6666));
                /*
                 * End Todo
                 */

            } catch (Exception e) {

                Log.e(TAG, "Problems receiving packet: " + e.getMessage());
                socketOK = false;
            }
        }
    });

}

    /*
     * Close the socket before exiting application
     */
    public void closeSocket() {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }

    /*
     * If the socket is OK, then it's running
     */
    boolean socketIsOK() {
        return socketOK;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeSocket();
    }

    private void addPeer(Peer peer) {
        for (Peer p : peers) {
            if (p.name.equals(peer.name)) {
                return;
            }
        }
        peers.add(peer);
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
                // Send the list of peers to the subactivity as a parcelable list
                Intent intent = new Intent(ChatServer.this,ViewPeersActivity.class);
                intent.putParcelableArrayListExtra(PEERS_KEY, (ArrayList) peers);
                startActivity(intent);
                break;

            default:
        }
        return false;
    }
}