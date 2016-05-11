package kr.popcorn.sharoom.activity.Fragment.Host;

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

import kr.popcorn.sharoom.R;

import kr.popcorn.sharoom.activity.TabView.TabView_registerAdapter;
import kr.popcorn.sharoom.activity.TabView.TabView_rentListAdapter;
import kr.popcorn.sharoom.activity.TabView.TabView_reservationAdapter;

public final class H_TestFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";

    final int REGISTER = 0;
    final int RESERVATONROOM = 1;
    final int MyInformation = 2;

    private View view;
    private View view_register;
    private View info;

    public RecyclerView recyclerView;
    public RecyclerView recyclerView_register;

    private TabView_rentListAdapter rentListAdapter;
    private TabView_reservationAdapter reservationAdapter;
    private TabView_registerAdapter registerAdapter;

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
        info = inflater.inflate(R.layout.activity_myself,container,false);

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



        switch (cases){
            case RESERVATONROOM:
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                reservationAdapter = new TabView_reservationAdapter(getActivity(),
                        null,
                        (LinearLayoutManager) recyclerView.getLayoutManager());
                recyclerView.setAdapter(reservationAdapter);
                break;
            case REGISTER:
                recyclerView_register.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView_register.setItemAnimator(new DefaultItemAnimator());

                registerAdapter = new TabView_registerAdapter(getActivity(),
                        null,
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
       // else if(mContent.equalsIgnoreCase("c")){
          //  setAdapterView(inflater, container, MyInformation);
            //return info;
          //  return view;

        //}
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
