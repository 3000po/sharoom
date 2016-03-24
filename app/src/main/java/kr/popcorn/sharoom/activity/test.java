package kr.popcorn.sharoom.activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import kr.popcorn.sharoom.R;

public class test extends Activity {
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

      //  mPlanetTitles = getResources().getStringArray(R.array.planets_array);
       // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
       // mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
     //   mDrawerList.setAdapter(new ArrayAdapter<String>(this,
         //       R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
      //  mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    }

}
