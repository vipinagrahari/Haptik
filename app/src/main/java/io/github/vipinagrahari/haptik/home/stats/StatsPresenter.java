package io.github.vipinagrahari.haptik.home.stats;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.util.List;

import io.github.vipinagrahari.haptik.BasePresenter;
import io.github.vipinagrahari.haptik.data.db.DbContract;
import io.github.vipinagrahari.haptik.data.model.Message;

import static io.github.vipinagrahari.haptik.data.db.DbContract.STAT_URI;


/**
 * Created by vipin on 19/11/16.
 */

public class StatsPresenter implements BasePresenter,LoaderManager.LoaderCallbacks<Cursor> {

    final int STAT_LOADER=0;
    /*
    Projection to be used for fetching messages from DB
     */
    private static final String[] STAT_COLUMNS = {
            DbContract.MessageEntry.TABLE_NAME + "." + DbContract.MessageEntry._ID,
            DbContract.MessageEntry.COLUMN_NAME,
            DbContract.MessageEntry.COLUMN_USER_NAME,
            DbContract.MessageEntry.COLUMN_IMG_URL,
            "count ("+DbContract.MessageEntry.COLUMN_BODY+") AS total",
            "sum ( " + DbContract.MessageEntry.COLUMN_IS_FAVOURITE+") AS favourite"

    };

    StatsContract.View mView;
    Context mContext;
    LoaderManager mLoaderManager;

    public StatsPresenter( @NonNull StatsContract.View statsView,@NonNull Context context,@NonNull LoaderManager loaderManager) {
        mView=statsView;
        mContext=context;
        mLoaderManager=loaderManager;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mLoaderManager.initLoader(STAT_LOADER,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id==STAT_LOADER) {
            return new CursorLoader(
                        mContext,
                        STAT_URI,
                        STAT_COLUMNS,
                        null,
                        null,
                        null
             );
            }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mView.showStats(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // NOP
    }

}
