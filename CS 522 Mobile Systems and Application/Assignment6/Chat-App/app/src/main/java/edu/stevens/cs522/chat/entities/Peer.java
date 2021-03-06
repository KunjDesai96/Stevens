package edu.stevens.cs522.chat.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.stevens.cs522.base.DateUtils;
import edu.stevens.cs522.base.InetAddressUtils;
import edu.stevens.cs522.chat.contracts.PeerContract;
import edu.stevens.cs522.chat.entities.Persistable;

/**
 * Created by dduggan.
 */

public class Peer implements Parcelable, Persistable {

    // Will be database key
    public long id;

    public String name;

    // Last time we heard from this peer.
    public Date timestamp;

    // Where we heard from them
    public InetAddress address;
    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    public Peer() {
    }

    public Peer(Cursor cursor) throws ParseException, UnknownHostException {
        // TODO
        this.id = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(PeerContract.PEER_ID)));
        this.name = PeerContract.getPeerName(cursor);
        this.timestamp = PeerContract.getPeerTimeStamp(cursor);
        this.address = InetAddress.getByName(cursor.getString(cursor.getColumnIndexOrThrow(PeerContract.PEER_ADDRESS)).substring(1));

    }

    public Peer(Parcel in) {
        // TODO
        name = in.readString();
        long tmpTimestamp = in.readLong();
        this.timestamp = tmpTimestamp == -1 ? null : new Date(tmpTimestamp);
        address = InetAddressUtils.readAddress(in);
    }

    @Override
    public void writeToProvider(ContentValues out) {
        // TODO
        PeerContract.putPeerName(out,name);
        PeerContract.putPeerTimeStamp(out,timestamp);
        PeerContract.putPeerAddress(out,address.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        // TODO
        out.writeString(name);
        out.writeLong(this.timestamp != null ? this.timestamp.getTime() : -1);
        InetAddressUtils.writeAddress(out,address);

    }

    public static final Creator<Peer> CREATOR = new Creator<Peer>() {

        @Override
        public Peer createFromParcel(Parcel source) {
            // TODO
            return new Peer(source);
        }

        @Override
        public Peer[] newArray(int size) {
            // TODO
            return new Peer[size];
        }

    };
}
