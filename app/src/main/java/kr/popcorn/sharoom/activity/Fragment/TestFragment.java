package kr.popcorn.sharoom.activity.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

import kr.popcorn.sharoom.R;

import kr.popcorn.sharoom.activity.Activity_editRoom;
import kr.popcorn.sharoom.activity.TabView.TabView_myselfAdapter;
import kr.popcorn.sharoom.activity.TabView.TabView_rentListAdapter;
import kr.popcorn.sharoom.helper.Helper_roomData;

public final class TestFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";

    final int ROOMLIST = 0;
    final int CALENDAR = 1;
    final int COMUNICATION = 2;
    final int MyInformation = 5;

    private View view;
    private View view2;

    public RecyclerView recyclerView;

    public TabView_rentListAdapter rentListAdapter;
    public TabView_myselfAdapter myselfAdapter;



    public static TestFragment newInstance(String content) {
        TestFragment fragment = new TestFragment();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = content;

        return fragment;
    }

    private String mContent = "???";

    private void setAdapterView(LayoutInflater inflater, ViewGroup container, int cases){
        view = inflater.inflate(R.layout.activity_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);


       // inflater.

        /*if( cases == 1 ){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            rentListAdapter = new Helper_rentListAdapter(getActivity(),
                    //TODO 리스트 넣기,
                    (LinearLayoutManager) recyclerView.getLayoutManager());
            recyclerView.setAdapter(rentListAdapter);

            return;
        }else if( cases == 2){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            rentListAdapter = new Helper_rentListAdapter(getActivity(),
                    //TODO 리스트 넣기,
                    (LinearLayoutManager) recyclerView.getLayoutManager());
            recyclerView.setAdapter(rentListAdapter);

            return;
        }
*/

        Helper_roomData first = new Helper_roomData();
        ArrayList<Helper_roomData> se = new ArrayList<Helper_roomData>();
        se.add(first);
        se.add(first);
        se.add(first);
        se.add(first);
        se.add(first);
        se.add(first);
        se.add(first);
        if(cases == ROOMLIST){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            rentListAdapter = new TabView_rentListAdapter(getActivity(),
                    se,
                    (LinearLayoutManager) recyclerView.getLayoutManager());
            recyclerView.setAdapter(rentListAdapter);

            return;
        }
        else if(cases == MyInformation){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            myselfAdapter = new TabView_myselfAdapter(getActivity(),
                    se,
                    (LinearLayoutManager) recyclerView.getLayoutManager());
            recyclerView.setAdapter(myselfAdapter);
            return;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(mContent.equalsIgnoreCase("a")){ //Ignore Lower Upper case
            //setAdapterView(inflater, container, ROOMLIST);
          //  LinearLayout layout = new LinearLayout(getActivity());
          //  layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
         //   layout.setGravity(Gravity.CENTER);

            //Activity l =  (Activity) new Activity_roomList();
            //LinearLayout layout = (LinearLayout) l.findViewById(R.id.roomlist);

            //return inflater.inflate(R.layout.activity_list, container, false);
            setAdapterView(inflater, container, ROOMLIST);
            return view;
        }
        else if(mContent.equalsIgnoreCase("e")){;
            setAdapterView(inflater, container, MyInformation);
            Activity_editRoom abc = new Activity_editRoom();

            //view2 = abc.getLayoutInflater().inflate(R.layout.activity_editroom, container,false);
            view2 = inflater.inflate(R.layout.activity_editroom, container,false);


            return view2;
        }else {

            TextView text = new TextView(getActivity());
            text.setGravity(Gravity.CENTER);
            text.setText(mContent);
            text.setTextSize(20 * getResources().getDisplayMetrics().density);
            text.setPadding(20, 20, 20, 20);

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
            layout.setGravity(Gravity.CENTER);
            layout.addView(text);
            return layout;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);

    }
}
