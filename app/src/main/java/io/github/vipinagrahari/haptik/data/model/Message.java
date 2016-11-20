package io.github.vipinagrahari.haptik.data.model;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.vipinagrahari.haptik.data.db.DbContract.MessageEntry;

/**
 * Created by vipin on 19/11/16.
 */

public class Message {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;

    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("image-url")
    @Expose
    private String imageUrl;
    @SerializedName("message-time")
    @Expose
    private String messageTime;

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    private boolean isFavourite;

    /**
     *
     * @return
     * The body
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @param body
     * The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The image-url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     *
     * @return
     * The messageTime
     */
    public String getMessageTime() {
        return messageTime;
    }

    /**
     *
     * @param messageTime
     * The message-time
     */
    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }


    /**
     *
     * @return Returns ContentValues for easy insertion into the database
     */
    public ContentValues getContentValues() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MessageEntry.COLUMN_BODY, getBody());
        contentValues.put(MessageEntry.COLUMN_NAME, getName());
        contentValues.put(MessageEntry.COLUMN_USER_NAME, getUsername());
        contentValues.put(MessageEntry.COLUMN_IMG_URL, getImageUrl());
        contentValues.put(MessageEntry.COLUMN_TIME_STAMP, getMessageTime());
        contentValues.put(MessageEntry.COLUMN_IS_FAVOURITE, isFavourite()?1:0);
        return contentValues;

    }

}