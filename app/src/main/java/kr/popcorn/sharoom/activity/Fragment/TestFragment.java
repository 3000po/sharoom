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
import kr.popcorn.sharoom.activity.TabView.TabView_registerAdapter;
import kr.popcorn.sharoom.activity.TabView.TabView_rentListAdapter;
import kr.popcorn.sharoom.activity.TabView.TabView_reservationAdapter;
import kr.popcorn.sharoom.helper.Helper_roomData;
import kr.popcorn.sharoom.helper.Helper_userData;

public final class TestFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";

    final int ROOMLIST = 0;
    final int RESERVATONROOM = 1;
    final int REGISTER = 2;
    final int MyInformation = 5;

    private View view;
    private View view_register;
    private View info;

    public RecyclerView recyclerView;
    public RecyclerView recyclerView_register;

    private TabView_rentListAdapter rentListAdapter;
    private TabView_myselfAdapter myselfAdapter;
    private TabView_reservationAdapter reservationAdapter;
    private TabView_registerAdapter registerAdapter;

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
        view_register = inflater.inflate(R.layout.activity_list2, container, false);
        info = inflater.inflate(R.layout.activity_myself_adapter,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView_register = (RecyclerView) view_register.findViewById(R.id.list_register);
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
        Helper_roomData second = new Helper_roomData();
        Helper_roomData third = new Helper_roomData();
        first.roomname ="방 화면 예시 1";
        second.roomname = "방 화면 예시 2";
        second.roomimage = R.drawable.room2;
        third.roomimage = R.drawable.room3;
        third.roomname = "방 화면 예시 3";

        Helper_roomData first2 = new Helper_roomData();
        Helper_roomData second2 = new Helper_roomData();
        Helper_roomData third2 = new Helper_roomData();

        first2.roomimage = R.drawable.room2;
        second2.roomimage = R.drawable.room3;
        third2.roomimage = R.drawable.room1;

        first2.roomname = "일정 : 03/01 ~ 03/22 인원 : 1명 예시입니다";
        second2.roomname = "일정 : 03/05 ~ 03/08 인원 : 3명 예시입니다";
        third2.roomname = "일정 : 03/25 ~ 03/29 인원 : 2명 예시입니다";

        Helper_roomData first3 = new Helper_roomData();
        Helper_roomData second3 = new Helper_roomData();
        Helper_roomData third3 = new Helper_roomData();

        first3.roomimage = R.drawable.room3;
        second3.roomimage = R.drawable.room2;
        third3.roomimage = R.drawable.room1;

        first3.roomname = "등록한 방 예시 1";
        second3.roomname = "등록한 방 예시 2";
        third3.roomname = "등록한 방 예시 3";

        ArrayList<Helper_roomData> se = new ArrayList<Helper_roomData>();
        se.add(first);
        se.add(second);
        se.add(third);

        ArrayList<Helper_roomData> se2 = new ArrayList<Helper_roomData>();
        se2.add(first2);
        se2.add(second2);
        se2.add(third2);

        ArrayList<Helper_roomData> se3 = new ArrayList<Helper_roomData>();
        se3.add(first3);
        se3.add(second3);
        se3.add(third3);

        Helper_userData user = new Helper_userData();

        switch (cases){
            case ROOMLIST:
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                rentListAdapter = new TabView_rentListAdapter(getActivity(),
                        se,
                        (LinearLayoutManager) recyclerView.getLayoutManager());
                recyclerView.setAdapter(rentListAdapter);

                break;

            case MyInformation:
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                myselfAdapter = new TabView_myselfAdapter(getActivity(),
                        user,
                        (LinearLayoutManager) recyclerView.getLayoutManager());
                recyclerView.setAdapter(myselfAdapter);
                break;

            case RESERVATONROOM:
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                reservationAdapter = new TabView_reservationAdapter(getActivity(),
                        se2,
                        (LinearLayoutManager) recyclerView.getLayoutManager());
                recyclerView.setAdapter(reservationAdapter);
                break;
            case REGISTER:
                recyclerView_register.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView_register.setItemAnimator(new DefaultItemAnimator());

                registerAdapter = new TabView_registerAdapter(getActivity(),
                        se3,
                        (LinearLayoutManager) recyclerView_register.getLayoutManager());
                recyclerView_register.setAdapter(registerAdapter);
                break;
        }
        return ;
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
        else if(mContent.equalsIgnoreCase("b")){
            setAdapterView(inflater, container, RESERVATONROOM);
            return view;
        }
        else if(mContent.equalsIgnoreCase("c")){
            setAdapterView(inflater, container, REGISTER);
            return view_register;
        }else if(mContent.equalsIgnoreCase("e")){
            setAdapterView(inflater, container, MyInformation);
            //return info;
            return view;

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
