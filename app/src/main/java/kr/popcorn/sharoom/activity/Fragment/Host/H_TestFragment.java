package kr.popcorn.sharoom.activity.Fragment.Host;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import kr.popcorn.sharoom.activity.Fragment.User.Activity_user_view;
import kr.popcorn.sharoom.activity.TabView.TabView_myself;
import kr.popcorn.sharoom.activity.TabView.TabView_registerAdapter;
import kr.popcorn.sharoom.activity.TabView.TabView_rentListAdapter;
import kr.popcorn.sharoom.activity.TabView.TabView_reservationAdapter;
import kr.popcorn.sharoom.floatingactionbutton.FloatingActionButton;
import kr.popcorn.sharoom.helper.Helper_roomData;

public final class H_TestFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";

    final int REGISTER = 0;
    final int RESERVATONROOM = 1;
    final int MyInformation = 2;

    private View view;
    private View view_register;

    public RecyclerView recyclerView;
    public RecyclerView recyclerView_register;

    private TabView_reservationAdapter reservationAdapter;
    private TabView_registerAdapter registerAdapter;

    private FloatingActionButton floatActBtn;


    public static H_TestFragment newInstance(String content) {
        H_TestFragment fragment = new H_TestFragment();

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

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView_register = (RecyclerView) view_register.findViewById(R.id.list_register);

        floatActBtn = (FloatingActionButton) view_register.findViewById(R.id.mFloatingActionButton);

        floatActBtn.setOnClickListener(new FloatingActionButton.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_editRoom.class)); // 로딩이 끝난후 이동할 Activity


            }
        });


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

        first2.roomname = "우리집에서 라면먹고 갈래?";
        second2.roomname = "소주한잔 어때요";
        third2.roomname = " 오늘밤은 혼자있기가 무서워요";

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

        switch (cases){
            case RESERVATONROOM:
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                reservationAdapter = new TabView_reservationAdapter(getActivity(),
                        se,
                        (LinearLayoutManager) recyclerView.getLayoutManager());
                recyclerView.setAdapter(reservationAdapter);
                break;
            case REGISTER:
                recyclerView_register.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView_register.setItemAnimator(new DefaultItemAnimator());

                registerAdapter = new TabView_registerAdapter(getActivity(),
                        se2,
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

        if(mContent.equalsIgnoreCase("a")){
            setAdapterView(inflater, container, REGISTER);
            return view_register;
        }
        if(mContent.equalsIgnoreCase("b")){
            setAdapterView(inflater, container, RESERVATONROOM);
            return view;
        }
        else if(mContent.equalsIgnoreCase("c")){
            TabView_myself tabView_myself = new TabView_myself(getContext());
            return tabView_myself;}
        else {

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
