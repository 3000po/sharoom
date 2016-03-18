package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;

import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.helper.Helper_rentListAdapter;
import kr.popcorn.sharoom.helper.Helper_roomData;

/**
 * Created by user on 16. 3. 2.
 */public class Activity_roomList extends Activity {

    public RecyclerView recyclerView;
    public Helper_rentListAdapter contactAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<Helper_roomData> list = (ArrayList<Helper_roomData>) getIntent().getSerializableExtra("list");


        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).
                color(Color.LTGRAY).sizeResId(R.dimen.divider).marginResId(R.dimen.leftmargin, R.dimen.rightmargin).build());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        contactAdapter = new Helper_rentListAdapter(this,
                list, (LinearLayoutManager) recyclerView.getLayoutManager());
        recyclerView.setAdapter(contactAdapter);
    }
}
