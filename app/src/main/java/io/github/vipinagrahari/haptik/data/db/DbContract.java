package io.github.vipinagrahari.haptik.data.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by vipin on 20/11/16.
 */

public class DbContract {

    public static final String CONTENT_AUTHORITY = "io.github.vipinagrahari.haptik";

    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MESSAGE = "message";
    public static final String PATH_MESSAGE_STAT = "message_stat";

    public static final Uri STAT_URI = BASE_URI.buildUpon().appendPath(PATH_MESSAGE_STAT).build();


    public static final class MessageEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_MESSAGE).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MESSAGE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MESSAGE;
        public static final String TABLE_NAME = "dream";

        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_USER_NAME = "username";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMG_URL = "image";
        public static final String COLUMN_TIME_STAMP = "timestamp";
        public static final String COLUMN_IS_FAVOURITE = "is_favourite";



        public static Uri buildDreamUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}