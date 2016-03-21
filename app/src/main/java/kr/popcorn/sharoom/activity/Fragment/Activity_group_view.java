package kr.popcorn.sharoom.activity.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.helper.Helper_server;

public class Activity_group_view extends FragmentActivity {


    TestFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);

        AsyncHttpClient client = Helper_server.getInstance();
        final PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        Helper_server.logout(myCookieStore);
        client.setCookieStore(myCookieStore);

        mAdapter = new TestFragmentAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mPager.setOffscreenPageLimit(5);

        mIndicator = (IconPageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);



        init();
    }

    private void init() {
    }
}
