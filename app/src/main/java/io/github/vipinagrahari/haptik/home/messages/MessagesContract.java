package io.github.vipinagrahari.haptik.home.messages;

import android.database.Cursor;

import java.util.List;

import io.github.vipinagrahari.haptik.BasePresenter;
import io.github.vipinagrahari.haptik.BaseView;
import io.github.vipinagrahari.haptik.data.model.Message;


/**
 * Created by vipin on 19/11/16.
 */

interface MessagesContract {

    interface View extends BaseView<Presenter> {

        void showMessages(Cursor data);

        void showToast(String string);
    }

    interface Presenter extends BasePresenter {

        void loadMessages();

        void saveAllMessages(List<Message> messageList);

        void toggleFavourite(long messageId,boolean isFavourite);

    }







}
