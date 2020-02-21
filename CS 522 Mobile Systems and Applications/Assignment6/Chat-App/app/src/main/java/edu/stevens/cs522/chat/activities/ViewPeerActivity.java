package edu.stevens.cs522.chat.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import edu.stevens.cs522.chat.R;
import edu.stevens.cs522.chat.async.IQueryListener;
import edu.stevens.cs522.chat.contracts.MessageContract;
import edu.stevens.cs522.chat.entities.Message;
import edu.stevens.cs522.chat.entities.Peer;
import edu.stevens.cs522.chat.managers.MessageManager;
import edu.stevens.cs522.chat.managers.TypedCursor;


public class ViewPeerActivity extends Activity implements IQueryListener<Message> {

    public static final String PEER_KEY = "peer";

    private SimpleCursorAdapter messageAdapter;

    private MessageManager messageManager;

    private TextView username,timestamp,address;

    ListView viewMessages;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        Peer peer = getIntent().getParcelableExtra(PEER_KEY);
        if (peer == null) {
            throw new IllegalArgumentException("Expected peer as intent extra");
        }

        // TODO init the UI
        username = findViewById(R.id.view_user_name);
        timestamp = findViewById(R.id.view_timestamp);
        address = findViewById(R.id.view_address);
        viewMessages = findViewById(R.id.view_messages);
        name = peer.name;
        username.setText(peer.name);
        timestamp.setText(peer.timestamp.toString());
        address.setText(peer.address.toString());

        String [] from = new String[] {MessageContract.MESSAGE_TEXT, MessageContract.TIMESTAMP};
        int [] to = new int[] {android.R.id.text1, android.R.id.text2};
        messageAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_expandable_list_item_2,null,from,to,0);
        viewMessages.setAdapter(messageAdapter);

        messageManager = new MessageManager(this);
        messageManager.getMessagesByPeerAsync(peer,this);
    }

    @Override
    public void handleResults(TypedCursor<Message> results) {
        // TODO
        messageAdapter.swapCursor(results.getCursor());
    }

    @Override
    public void closeResults() {
        // TODO
        messageAdapter.swapCursor(null);
    }

}
