package io.github.vipinagrahari.haptik.home.messages;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.vipinagrahari.haptik.R;
import io.github.vipinagrahari.haptik.data.model.Message;
import io.github.vipinagrahari.haptik.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageListFragment extends Fragment implements MessagesContract.View{

    final String PREF_FIRST_LAUNCH="first_launch";
    MessagesContract.Presenter mPresenter;

    RecyclerView rvMessages;
    MessagesAdapter messagesAdapter;
    boolean mRefreshDataFlag; // Data is fetched from server if set.
    public MessageListFragment() {
        // Required empty public constructor
    }

    public static MessageListFragment newInstance() {
       return new MessageListFragment();

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences=getContext().getSharedPreferences(HomeActivity.class.getName(),Context.MODE_PRIVATE);
        // Refresh flag is set only on the first launch of application
        mRefreshDataFlag= preferences.getBoolean(PREF_FIRST_LAUNCH,true);
        mPresenter=new MessageListPresenter(this,getContext(),getLoaderManager(),mRefreshDataFlag);
        messagesAdapter= new MessagesAdapter(null,getContext(),mPresenter);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        Context context=container.getContext();
        View root = inflater.inflate(R.layout.fragment_message_list, container, false);
        rvMessages=(RecyclerView)root.findViewById(R.id.rv_messages);
        rvMessages.setAdapter(messagesAdapter);
        rvMessages.setLayoutManager(new LinearLayoutManager(context));
        rvMessages.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.HORIZONTAL));
        return root;

    }


    @Override
    public void showMessages(Cursor cursor) {
        messagesAdapter.swapCursor(cursor);
    }

    @Override
    public void showToast(String string) {
        Toast.makeText(getContext(), string , Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setPresenter(MessagesContract.Presenter presenter) {
        mPresenter=presenter;
    }
}
