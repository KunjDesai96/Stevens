package edu.stevens.cs522.chatserver.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.net.InetAddresses;

import java.net.InetAddress;
import java.util.Date;

import edu.stevens.cs522.base.DateUtils;
import edu.stevens.cs522.base.InetAddressUtils;

public class Peer implements Parcelable {

    // Will be database key
   // public long id;

    public String name;

    // Last time we heard from this peer.
    public Date timestamp ;

    // Where we heard from them
    public InetAddress address;

    public int port;

    public Peer(String name, Date timestamp, InetAddress address, int port) {
        super();
        this.name = name;
        this.timestamp = timestamp;
        this.address = address;
        this.port = port;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        // TODO
        out.writeString(name);
        DateUtils.writeDate(out,timestamp);
        InetAddressUtils.writeAddress(out,address);
        out.writeInt(port);

    }

    public Peer(Parcel in) {
        // TODO
        name = in.readString();
        timestamp = DateUtils.readDate(in);
        address = InetAddressUtils.readAddress(in);
        port = in.readInt();
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
