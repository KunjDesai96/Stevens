package edu.stevens.cs522.chat.rest;

import android.os.Parcel;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by dduggan.
 */

public class PostMessageRequest extends Request {

    public String chatRoom;

    public String message;

    public UUID appID;

    public PostMessageRequest(long senderId, UUID clientID, String chatRoom, String message) {
        super(senderId, clientID);
        this.senderId =senderId;
        this.chatRoom = chatRoom;
        this.message = message;
        this.appID = clientID;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> headers = new HashMap<>();
        // TODO
        headers.put(APP_ID_HEADER, appID.toString());
        headers.put(TIMESTAMP_HEADER, Long.toString(timestamp.getTime()));
        headers.put(LONGITUDE_HEADER, Double.toString(longitude));
        headers.put(LATITUDE_HEADER, Double.toString(latitude));
        return headers;
    }

    @Override
    public String getRequestEntity() throws IOException {
        StringWriter wr = new StringWriter();
        JsonWriter jw = new JsonWriter(wr);
        // TODO write a JSON message of the form:
        // { "room" : <chat-room-name>, "message" : <message-text> }
        jw.beginObject();
        String chatroom = this.chatRoom;
        String messageText = this.message;
        jw.name("chatroom").value(chatroom);
        jw.name("text").value(messageText);
        jw.endObject();
        return wr.toString();
    }

    @Override
    public Response getResponse(HttpURLConnection connection, JsonReader rd) throws IOException{
        return new PostMessageResponse(connection);
    }

    @Override
    public Response process(RequestProcessor processor) {
        return processor.perform(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO

        dest.writeLong(this.senderId);
        dest.writeString(this.chatRoom);
        dest.writeString(this.message);
        dest.writeSerializable(this.timestamp);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(String.valueOf(appID));

    }

    public PostMessageRequest(Parcel in) {
        super(in);
        // TODO
        this.senderId = in.readLong();
        this.chatRoom = in.readString();
        this.message = in.readString();
        this.timestamp = (Date) in.readSerializable();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.appID = UUID.fromString(in.readString());
    }

    public static Creator<PostMessageRequest> CREATOR = new Creator<PostMessageRequest>() {
        @Override
        public PostMessageRequest createFromParcel(Parcel source) {
            return new PostMessageRequest(source);
        }

        @Override
        public PostMessageRequest[] newArray(int size) {
            return new PostMessageRequest[size];
        }
    };

}
