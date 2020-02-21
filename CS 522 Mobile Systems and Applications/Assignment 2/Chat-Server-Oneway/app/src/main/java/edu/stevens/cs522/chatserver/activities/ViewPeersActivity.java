package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.entities.Peer;


public class ViewPeersActivity extends Activity  {

    public static final String PEERS_KEY = "peers";

    private ArrayList<Peer> peers;
    private  ArrayAdapter <String> peerArrayAdapter;
    private ListView peer_list;
    private ArrayList <String> name = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peers);

        peers = getIntent().getParcelableArrayListExtra(PEERS_KEY);
        for (int i = 0; i < peers.size(); i++)
        {
            Peer peer = peers.get(i);
            name.add(peer.name);
        }

        // TODO display the list of peers, set this activity as onClick listener
        peer_list = findViewById(R.id.peer_list);
        peerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,name);
        peer_list.setAdapter(peerArrayAdapter);

        peer_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Peer peer = peers.get(position);
                Intent intent = new Intent(ViewPeersActivity.this, ViewPeerActivity.class);
                intent.putExtra(ViewPeerActivity.PEER_KEY, peer);
                startActivity(intent);
            }
        });

    }


}
