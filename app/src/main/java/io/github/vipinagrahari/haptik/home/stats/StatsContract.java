package io.github.vipinagrahari.haptik.home.stats;

import android.database.Cursor;

import io.github.vipinagrahari.haptik.BasePresenter;
import io.github.vipinagrahari.haptik.BaseView;

/**
 * Created by vipin on 19/11/16.
 */

public class StatsContract {
    interface View extends BaseView<StatsPresenter> {
        void showStats(Cursor cursor);
    }

}
