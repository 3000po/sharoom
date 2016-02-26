package kr.popcorn.sharoom.activity;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import kr.popcorn.sharoom.R;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        TabSpec tabSpecTab1 = tabHost.newTabSpec("TAB1").setIndicator("강아지");
        tabSpecTab1.setContent(R.id.tab1);
        tabHost.addTab(tabSpecTab1);

        TabSpec tabSpecTab2 = tabHost.newTabSpec("TAB2").setIndicator("고양이");
        tabSpecTab2.setContent(R.id.tab2);
        tabHost.addTab(tabSpecTab2);

        TabSpec tabSpecTab3 = tabHost.newTabSpec("TAB3").setIndicator("토끼");
        tabSpecTab3.setContent(R.id.tab3);
        tabHost.addTab(tabSpecTab3);

        TabSpec tabSpecTab4 = tabHost.newTabSpec("TAB4").setIndicator("말");
        tabSpecTab4.setContent(R.id.tab1);
        tabHost.addTab(tabSpecTab4);

        tabHost.setCurrentTab(0);

    }
}
