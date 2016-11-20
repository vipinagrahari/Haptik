package io.github.vipinagrahari.haptik.data.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import io.github.vipinagrahari.haptik.data.db.DbContract.MessageEntry;

/**
 * Created by vipin on 20/11/16.
 */

public class DataProvider extends ContentProvider {

    static final int MESSAGE = 1;
    static final int MESSAGE_STATS = 2;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbHelper dbHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DbContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, DbContract.PATH_MESSAGE, MESSAGE);
        uriMatcher.addURI(authority, DbContract.PATH_MESSAGE_STAT , MESSAGE_STATS);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case MESSAGE: {

                retCursor = dbHelper.getReadableDatabase().query(
                        MessageEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case MESSAGE_STATS: {
                retCursor = dbHelper.getReadableDatabase().query(
                        MessageEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        "username", //
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);


        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);


        return retCursor;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,String groupBy,String having,
                        String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {

            case MESSAGE:

                retCursor = dbHelper.getReadableDatabase().query(
                        MessageEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        groupBy,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("NOT IMPLEMENTED: " + uri);


        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Nullable
    @Override
    public String getType(Uri uri) {


        System.out.println(uri);

        switch (sUriMatcher.match(uri)) {
            case MESSAGE:
            case MESSAGE_STATS:
                return MessageEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case MESSAGE: {
                long _id = db.insert(MessageEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MessageEntry.buildDreamUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }


            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;

    }


    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MESSAGE:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MessageEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted;
        if (selection == null) selection = "1";

        switch (sUriMatcher.match(uri)) {
            case MESSAGE:
                rowsDeleted = db.delete(MessageEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MESSAGE:
                rowsUpdated = db.update(MessageEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            getContext().getContentResolver().notifyChange(DbContract.STAT_URI, null);
        }
        return rowsUpdated;
    }

}