package kr.popcorn.sharoom.activity.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import kr.popcorn.sharoom.helper.Helper_rentListAdapter;

public final class TestFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";

    final int NOTICE = 0;
    final int CALENDAR = 1;
    final int COMUNICATION = 2;
    final int ETC = 3;

    private View view;

    public RecyclerView recyclerView;
    public Helper_rentListAdapter rentListAdapter;

    public static TestFragment newInstance(String content) {
        TestFragment fragment = new TestFragment();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();

        return fragment;
    }

    private String mContent = "???";

    private void setAdapterView(LayoutInflater inflater, ViewGroup container, int cases){
/*        view = inflater.inflate(R.layout.list);
        if( cases == 1 ){
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

        if(mContent.equalsIgnoreCase("f")){ //Ignore Lower Upper case
            //setAdapterView(inflater, container, 1);
        }else if(mContent.equalsIgnoreCase("w")){
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
        }else if(mContent.equalsIgnoreCase("r")){
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
        }else if(mContent.equalsIgnoreCase("y")){;
            setAdapterView(inflater, container, 4);
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

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
