package edu.stevens.cs522.chat.managers;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import edu.stevens.cs522.chat.async.IEntityCreator;
import edu.stevens.cs522.chat.async.IQueryListener;
import edu.stevens.cs522.chat.async.QueryBuilder;
import edu.stevens.cs522.chat.contracts.MessageContract;
import edu.stevens.cs522.chat.entities.ChatMessage;
import edu.stevens.cs522.chat.entities.Peer;


/**
 * Created by dduggan.
 */

public class MessageManager extends Manager<ChatMessage> {

    private static final int LOADER_ID = 1;
    public static final int SINGLE_MSG_LOADER_ID = 10;
    private static final IEntityCreator<ChatMessage> creator = new IEntityCreator<ChatMessage>() {
        @Override
        public ChatMessage create(Cursor cursor) {
            return new ChatMessage(cursor);
        }
    };

    public MessageManager(Context context) {
        super(context, creator, LOADER_ID);
    }

    public void getAllMessagesAsync(IQueryListener<ChatMessage> listener) {
        // TODO use QueryBuilder to complete this
        QueryBuilder.executeQuery(
                tag,
                (Activity) context,
                MessageContract.CONTENT_URI, LOADER_ID,
                new IEntityCreator<ChatMessage>()
                {
                    @Override
                    public ChatMessage create(Cursor cursor)
                    {
                        return null;
                    }
                },
                listener
        );
    }
    public void getMessagesByPeerAsync(Peer peer, IQueryListener<ChatMessage> listener)
    {
        // TODO use QueryBuilder to complete this
        // Remember to reset the loader!
        String name = peer.name;
        String selection = "(" + MessageContract.SENDER + "='" + name + "')";
        ;
        QueryBuilder.executeQuery(
                tag,
                (Activity) context,
                MessageContract.CONTENT_URI,
                null,
                selection,
                null,
                null,
                SINGLE_MSG_LOADER_ID,
                new IEntityCreator<ChatMessage>()
                {
                    public ChatMessage create(Cursor cursor)
                    {
                        return null;
                    }
                },
                listener
        );
    }

    public void persistAsync(ChatMessage Message) {
        // TODO
    }
    public void updateSequenceNumber(ChatMessage chatMessage, long messageId)
    {
        ContentResolver cr = this.getSyncResolver();
        ContentValues values = new ContentValues();
        chatMessage.writeToProvider(values);

        String where = MessageContract.ID + "=?";
        String[] whereCondition = {((Long) messageId).toString()};

        cr.update(MessageContract.CONTENT_URI, values, where, whereCondition);
    }
    public long persist(ChatMessage message) {
        // Synchronous version, executed on background thread
        ContentResolver cr = this.getSyncResolver();

        ContentValues values = new ContentValues();
        message.writeToProvider(values);

        cr.insert(MessageContract.CONTENT_URI, values);

        return message.id;
    }

}
