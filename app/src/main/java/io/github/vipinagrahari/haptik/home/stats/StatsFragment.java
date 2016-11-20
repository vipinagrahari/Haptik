package io.github.vipinagrahari.haptik.home.stats;


import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import io.github.vipinagrahari.haptik.BasePresenter;
import io.github.vipinagrahari.haptik.BaseView;
import io.github.vipinagrahari.haptik.R;
import io.github.vipinagrahari.haptik.data.model.Message;
import io.github.vipinagrahari.haptik.home.messages.MessageListPresenter;
import io.github.vipinagrahari.haptik.home.messages.MessagesAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment implements StatsContract.View {

    BasePresenter mPresenter;

    RecyclerView rvStats;
    StatsAdapter statsAdapter;
    public StatsFragment() {
        // Required empty public constructor
    }

    public static StatsFragment newInstance() {
        return new StatsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statsAdapter= new StatsAdapter(null);
        mPresenter=new StatsPresenter(this,getContext(),getLoaderManager());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        Context context=container.getContext();
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        rvStats=(RecyclerView)root.findViewById(R.id.rv_stats);
        rvStats.setAdapter(statsAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        rvStats.setLayoutManager(layoutManager);
        rvStats.addItemDecoration(new DividerItemDecoration(context,
                layoutManager.getOrientation()));
        return root;
    }


    @Override
    public void setPresenter(StatsPresenter presenter) {
        mPresenter=presenter;

    }

    @Override
    public void showStats(Cursor cursor) {
        statsAdapter.swapCursor(cursor);
    }
}
