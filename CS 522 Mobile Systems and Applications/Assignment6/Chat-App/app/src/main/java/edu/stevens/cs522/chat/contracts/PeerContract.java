package edu.stevens.cs522.chat.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.Date;

import edu.stevens.cs522.base.DateUtils;

/**
 * Created by dduggan.
 */

public class PeerContract extends BaseContract {

    public static final Uri CONTENT_URI = CONTENT_URI(AUTHORITY, "Peer");

    public static final Uri CONTENT_URI(long id) {
        return CONTENT_URI(Long.toString(id));
    }

    public static final Uri CONTENT_URI(String id) {
        return withExtendedPath(CONTENT_URI, id);
    }

    public static final String CONTENT_PATH = CONTENT_PATH(CONTENT_URI);

    public static final String CONTENT_PATH_ITEM = CONTENT_PATH(CONTENT_URI("#"));


    // TODO define column names, getters for cursors, setters for contentvalues
    public  static final String PEER_ID = "_id";
    public static  final String PEER_NAME = "peer_name";
    public static  final String PEER_TIMESTAMP = "peer_timestamp";
    public static final  String PEER_ADDRESS = "peer_address";
    public static  final String PEER_PORT = "port";
    private static int messageTextColumn = -1;
    public static final int CURSOR_LOADER_ID =2;


    public static String getPeerName(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(PEER_NAME);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putPeerName(ContentValues out, String peerName) {
        out.put(PEER_NAME, peerName);
    }
    public static Date getPeerTimeStamp(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(PEER_TIMESTAMP);
        }
//        return cursor.getLong(timeStampColumn);
        return DateUtils.getDate(cursor,messageTextColumn);
    }
    public static void putPeerTimeStamp(ContentValues out, Date timestamp) {
//        out.put(TIMESTAMP, timestamp);
        DateUtils.putDate(out,PEER_TIMESTAMP,timestamp);
    }

    public static String getPeerAddress(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(PEER_ADDRESS);
        }
        return (cursor.getString(messageTextColumn)).substring(1);
    }

    public static void putPeerAddress(ContentValues out, String peerAddress) {
        out.put(PEER_ADDRESS, peerAddress);
    }

}
