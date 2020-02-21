package edu.stevens.cs522.chat.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.stevens.cs522.chat.R;
import edu.stevens.cs522.chat.async.IQueryListener;
import edu.stevens.cs522.chat.contracts.MessageContract;
import edu.stevens.cs522.chat.entities.ChatMessage;
import edu.stevens.cs522.chat.entities.Peer;
import edu.stevens.cs522.chat.managers.MessageManager;
import edu.stevens.cs522.chat.managers.TypedCursor;

/**
 * Created by dduggan.
 */

public class ViewPeerActivity extends Activity implements IQueryListener<ChatMessage> {

    public static final String PEER_KEY = "peer";

    TextView username,lastseen,longitude,latitude;
    private SimpleCursorAdapter messageAdapter;

    private MessageManager messageManager;
    ListView viewMessages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        Peer peer = getIntent().getParcelableExtra(PEER_KEY);
        if (peer == null) {
            throw new IllegalArgumentException("Expected peer as intent extra");
        }

        // TODO init the UI
        if (peer == null) {
            throw new IllegalArgumentException("Expected peer as intent extra");
        }else {
            username = findViewById(R.id.view_user_name);
            lastseen = findViewById(R.id.view_timestamp);
            latitude = findViewById(R.id.view_latitude);
            longitude = findViewById(R.id.view_longitude);
            viewMessages = findViewById(R.id.view_messages);

            username.setText(peer.name);
            lastseen.setText(peer.timestamp.toString());
            latitude.setText(peer.latitude.toString());
            longitude.setText(peer.longitude.toString());

            String [] from = new String[] {MessageContract.MESSAGE_TEXT};
            int [] to = new int[] {android.R.id.text1};
            messageAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_expandable_list_item_1,null,from,to,0);
            viewMessages.setAdapter(messageAdapter);


            messageManager = new MessageManager(this);
            messageManager.getMessagesByPeerAsync(peer,this);
        }


    }

    @Override
    public void handleResults(TypedCursor<ChatMessage> results) {
        // TODO
        messageAdapter.swapCursor(results.getCursor());
    }

    @Override
    public void closeResults() {
        // TODO
        messageAdapter.swapCursor(null);
    }
}
