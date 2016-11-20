package io.github.vipinagrahari.haptik.home.messages;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.github.vipinagrahari.haptik.R;
import io.github.vipinagrahari.haptik.data.api.ServiceGenerator;
import io.github.vipinagrahari.haptik.data.db.DbContract;
import io.github.vipinagrahari.haptik.data.db.DbContract.MessageEntry;
import io.github.vipinagrahari.haptik.data.model.Message;
import io.github.vipinagrahari.haptik.home.HomeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by vipin on 19/11/16.
 */

public class MessageListPresenter implements MessagesContract.Presenter,LoaderManager.LoaderCallbacks<Cursor>{

    private static final String[] MESSAGE_COLUMNS = {
           MessageEntry.TABLE_NAME + "." + MessageEntry._ID,
           MessageEntry.COLUMN_NAME,
           MessageEntry.COLUMN_USER_NAME,
           MessageEntry.COLUMN_IMG_URL,
           MessageEntry.COLUMN_BODY,
           MessageEntry.COLUMN_IS_FAVOURITE,
            MessageEntry.COLUMN_TIME_STAMP
    };

    final int MESSAGE_LOADER=0;
    MessagesContract.View mView;
    List<Message> messageList;
    Context mContext;
    LoaderManager mLoaderManager;
    boolean mRefreshFlag;


    public MessageListPresenter( @NonNull MessagesContract.View messageListView,Context context,LoaderManager loaderManager,boolean refreshFlag) {
        mView=messageListView;
        mContext=context;
        mRefreshFlag=refreshFlag;
        System.out.println("REFRSH"+refreshFlag);
        mLoaderManager=loaderManager;
        mView.setPresenter(this);
    }


    @Override
    public void loadMessages() {

    Call<JsonObject> getMessages= ServiceGenerator.getInstance().getMessages();


        getMessages.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonArray artists=response.body().getAsJsonObject().get("messages")
                        .getAsJsonArray();
                Gson gson=new Gson();
                Type type=new TypeToken<List<Message>>() {}.getType();
                messageList=gson.fromJson(artists,type);
                saveAllMessages(messageList);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Show some message
            }
        });

    }

    /**
     * This method gets list of messages and stores that into local DB using content provider
     *
     * @param messageList - List of messages fetched from server
     *
     */

    @Override
    public void saveAllMessages(List<Message> messageList) {
        List<ContentValues> contentValues= new ArrayList<ContentValues>();
        for(Message message:messageList) contentValues.add(message.getContentValues());
        mContext.getContentResolver().delete(MessageEntry.CONTENT_URI,null,null);
        mContext.getContentResolver().bulkInsert(MessageEntry.CONTENT_URI,contentValues.toArray(new ContentValues[0]));

        //Reset the refresh flag so that next time data is fetched from loal DB instead of server
        SharedPreferences preferences=mContext.getSharedPreferences(HomeActivity.class.getName(),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("first_launch",false);
        editor.commit();
    }


    /**
     * This method marks a message as favourite in sqlite DB. If message is not a favourite it will be marked favourite
     *                    and vice-versa.
     * @param messageId - unique ID of message in DB
     * @param isFavourite - current favourite status of message.
     */

    @Override
    public void toggleFavourite(long messageId,boolean isFavourite) {
        ContentValues contentValues=new ContentValues();
        // If message is already a favourite then remove from favourite and vice-versa

        contentValues.put(MessageEntry.COLUMN_IS_FAVOURITE,isFavourite?0:1);


        System.out.println(contentValues.describeContents());
        int result=mContext.getContentResolver().update(
                MessageEntry.CONTENT_URI,
                contentValues,MessageEntry._ID+" =? ",
                new String[]{String.valueOf(messageId)
                });

        // If the result(number of rows updated) is greater than 0 then display a successful message else Unsuccessful

         mView.showToast(mContext.getString(
                 result!=0?
                 R.string.message_success:
                         R.string.message_failure
         ));

    }

    /**
     * If the refresh flag is set then fetch data from server otherwise load data from local DB
     */

    @Override
    public void start() {
        if(mRefreshFlag) loadMessages();
        mLoaderManager.initLoader(MESSAGE_LOADER,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id==MESSAGE_LOADER) {
            return new CursorLoader(
                    mContext,
                    MessageEntry.CONTENT_URI,
                    MESSAGE_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mView.showMessages(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // NOP
    }
}
