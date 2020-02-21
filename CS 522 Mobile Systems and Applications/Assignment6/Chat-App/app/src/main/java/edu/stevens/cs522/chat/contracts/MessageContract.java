package edu.stevens.cs522.chat.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.Date;

import edu.stevens.cs522.base.DateUtils;

/**
 * Created by dduggan.
 */

public class MessageContract extends BaseContract {

    public static final Uri CONTENT_URI = CONTENT_URI(AUTHORITY, "Message");

    public static final Uri CONTENT_URI(long id) {
        return CONTENT_URI(Long.toString(id));
    }

    public static final Uri CONTENT_URI(String id) {
        return withExtendedPath(CONTENT_URI, id);
    }

    public static final String CONTENT_PATH = CONTENT_PATH(CONTENT_URI);

    public static final String CONTENT_PATH_ITEM = CONTENT_PATH(CONTENT_URI("#"));


    public static final String ID = _ID;

    public static final String MESSAGE_TEXT = "message_text";

    public static final String TIMESTAMP = "timestamp";

    public static final String SENDER = "sender";

    // TODO remaining columns in Messages table

    public  static final int CURSOR_LOADER_ID =1;
    public  static  final int SINGLE_MSG_LOADER_ID = 10;
    private static int messageTextColumn = -1;
    public static String SENDER_ID = "senderID";

    public String getMessageText(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(MESSAGE_TEXT);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putMessageText(ContentValues out, String messageText) {
        out.put(MESSAGE_TEXT, messageText);
    }

    // TODO remaining getter and putter operations for other columns
    public static String getId(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(ID);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putId(ContentValues out, String messageID) {
        out.put(ID, messageID);
    }
    private static int timeStampColumn = -1;
    public static Date getTimeStamp(Cursor cursor) {
        if (timeStampColumn < 0) {
            timeStampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        }
//        return cursor.getLong(timeStampColumn);
        return DateUtils.getDate(cursor,timeStampColumn);
    }
    public static void putTimeStamp(ContentValues out, Date timestamp) {
//        out.put(TIMESTAMP, timestamp);
        DateUtils.putDate(out,TIMESTAMP,timestamp);
    }
    public static String getSender(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(SENDER);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putSender(ContentValues out, String messageSender) {
        out.put(SENDER, messageSender);
    }

    public static String getSenderId(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(SENDER);
        }
        return cursor.getString(messageTextColumn);
    }
    public static void putSenderId(ContentValues out, String senderId) {
        out.put(SENDER_ID, senderId);
    }
}
