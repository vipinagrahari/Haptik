package io.github.vipinagrahari.haptik.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import io.github.vipinagrahari.haptik.data.db.DbContract.MessageEntry;
import io.github.vipinagrahari.haptik.data.model.Message;

/**
 * Created by vipin on 20/11/16.
 */

public class DbHelper extends SQLiteOpenHelper {


    static final String DATABASE_NAME = "haptik.db";
    private static final int DATABASE_VERSION = 2;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DATABASE ON CREATE");

        final String SQL_CREATE_MESSAGE_TABLE = "CREATE TABLE " + MessageEntry.TABLE_NAME + "(" +
                MessageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MessageEntry.COLUMN_BODY + " TEXT NOT NULL, " +
                MessageEntry.COLUMN_USER_NAME + " TEXT NOT NULL, " +
                MessageEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                MessageEntry.COLUMN_IMG_URL + " TEXT, " +
                MessageEntry.COLUMN_IS_FAVOURITE + " INTEGER DEFAULT 0, " +
                MessageEntry.COLUMN_TIME_STAMP + " STRING NOT NULL );";


        db.execSQL(SQL_CREATE_MESSAGE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MessageEntry.TABLE_NAME);
        onCreate(db);

    }


}