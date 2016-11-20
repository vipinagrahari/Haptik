package io.github.vipinagrahari.haptik.home;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import io.github.vipinagrahari.haptik.R;
import io.github.vipinagrahari.haptik.home.messages.MessageListFragment;
import io.github.vipinagrahari.haptik.home.messages.MessageListPresenter;
import io.github.vipinagrahari.haptik.home.stats.StatsFragment;
import io.github.vipinagrahari.haptik.util.FragmentAdapter;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();

    final int TAB_COUNT = 2;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager = (ViewPager) findViewById(R.id.home_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), TAB_COUNT);
        MessageListFragment messageListFragment = MessageListFragment.newInstance();
        StatsFragment statsFragment = StatsFragment.newInstance();
        fragmentAdapter.setItem(0, messageListFragment, getString(R.string.fragment_message_list));
        fragmentAdapter.setItem(1, statsFragment, getString(R.string.fragment_stats));
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
