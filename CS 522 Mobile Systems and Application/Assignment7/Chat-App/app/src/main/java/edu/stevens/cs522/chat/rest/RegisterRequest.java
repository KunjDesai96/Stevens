package edu.stevens.cs522.chat.rest;

import android.os.Parcel;
import android.text.format.DateUtils;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by dduggan.
 */

public class RegisterRequest extends Request {

    public String chatname;

    public UUID appID;

    public RegisterRequest(String chatname, UUID clientID) {
        super(0, clientID);
        this.chatname = chatname;
        this.appID = clientID;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> headers = new HashMap<>();
        // TODO add headers
        headers.put(APP_ID_HEADER, appID.toString());
        headers.put(TIMESTAMP_HEADER, Long.toString(timestamp.getTime()));
        headers.put(LONGITUDE_HEADER, Double.toString(longitude));
        headers.put(LATITUDE_HEADER, Double.toString(latitude));
        return  headers;
    }

    @Override
    public String getRequestEntity() throws IOException {
        return null;
    }

    @Override
    public Response getResponse(HttpURLConnection connection, JsonReader rd) throws IOException{
        return new RegisterResponse(connection);
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
        super.writeToParcel(dest, flags);
        dest.writeString(chatname);
        dest.writeString(String.valueOf(appID));
        dest.writeLong(this.timestamp != null ? this.timestamp.getTime() : -1);
    }

    public RegisterRequest(Parcel in) {
        super(in);
        this.chatname = in.readString();
        this.appID = UUID.fromString(in.readString());
        long tmpTimestamp = in.readLong();
        this.timestamp = tmpTimestamp == -1 ? null : new Date(tmpTimestamp);


    }

    public static Creator<RegisterRequest> CREATOR = new Creator<RegisterRequest>() {
        @Override
        public RegisterRequest createFromParcel(Parcel source) {
            return new RegisterRequest(source);
        }

        @Override
        public RegisterRequest[] newArray(int size) {
            return new RegisterRequest[size];
        }
    };

}
