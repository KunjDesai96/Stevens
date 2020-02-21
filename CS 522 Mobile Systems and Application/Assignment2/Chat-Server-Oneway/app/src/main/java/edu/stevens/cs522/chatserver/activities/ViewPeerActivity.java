package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.entities.Peer;



public class ViewPeerActivity extends Activity {

    public static final String PEER_KEY = "peer";
    private TextView usernametv,timestamptv,addresstv,porttv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        Peer peer = getIntent().getParcelableExtra(PEER_KEY);
        if (peer == null) {
            throw new IllegalArgumentException("Expected peer as intent extra");
        }

        // TODO init the UI

        usernametv = findViewById(R.id.view_user_name);
        timestamptv = findViewById(R.id.view_timestamp);
        addresstv = findViewById(R.id.view_address);
        porttv = findViewById(R.id.view_port);

        usernametv.setText(peer.name);
        timestamptv.setText(peer.timestamp.toString());
        addresstv.setText(peer.address.toString());
        porttv.setText(peer.port.toString());

    }

}
