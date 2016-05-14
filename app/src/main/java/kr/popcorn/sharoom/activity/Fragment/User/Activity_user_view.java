package kr.popcorn.sharoom.activity.Fragment.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;

import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.activity.Activity_login;
import kr.popcorn.sharoom.activity.Activity_mainIntro;
import kr.popcorn.sharoom.activity.Activity_mapMenu;
import kr.popcorn.sharoom.helper.Helper_server;
import kr.popcorn.sharoom.helper.Helper_userData;

public class Activity_user_view extends FragmentActivity {

    private TestFragmentAdapter mAdapter;
    private ViewPager mPager;
    private PageIndicator mIndicator;
    private TextView mToptext;
    private ImageView mapMenu;

    public static Activity AActivty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_user_view);

        Helper_userData data = Helper_userData.getInstance(getApplicationContext());

        //Activity_login login = (Activity_login) Activity_login.login_Activity; //login_Activity_finish
        //login.finish();

        AsyncHttpClient client = Helper_server.getInstance();

        AActivty = Activity_user_view.this;

//        Activity_mainIntro activity = (Activity_mainIntro) Activity_mainIntro.mActivity;
//        activity.finish();

        mAdapter = new TestFragmentAdapter(getSupportFragmentManager());
        mapMenu = (ImageView)findViewById(R.id.mapMenu);

        mapMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.mapMenu:
                        Intent mapIntent = new Intent(Activity_user_view.this, Activity_mapMenu.class);
                        startActivity(mapIntent);
                }
            }
        });


        mToptext = (TextView) findViewById(R.id.toptext);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mPager.setOffscreenPageLimit(3);

        mIndicator = (IconPageIndicator) findViewById(R.id.u_indicator);

        mIndicator.setViewPager(mPager);

        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (mPager.getCurrentItem()){
                    case 0 : mToptext.setText("방 리스트");
                        mapMenu.setVisibility(View.VISIBLE);
                        break;
                    case 1 : mToptext.setText("예약 확인");
                        mapMenu.setVisibility(View.GONE);
                        break;
                    case 2 : mToptext.setText("내 정보");
                        mapMenu.setVisibility(View.GONE);
                        break;
                }
            }
        });

//        ImageView email_btn;
//        email_btn = (ImageView) v.findViewById(R.id.confirm1);
//        email_btn.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                if (v.getId() == R.id.confirm1) {
////                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext.get);
////
////                    alert.setTitle("Title");
////                    alert.setMessage("Message");
////
////                    // Set an EditText view to get user input
////                    final EditText input = new EditText(mContext);
////                    alert.setView(input);
////
////                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog, int whichButton) {
////                            String value = input.getText().toString();
////                            value.toString();
////                            // Do something with value!
////                        }
////                    });
////
////
////                    alert.setNegativeButton("Cancel",
////                            new DialogInterface.OnClickListener() {
////                                public void onClick(DialogInterface dialog, int whichButton) {
////                                    // Canceled.
////                                }
////                            });
////
////                    alert.show();
////
////
////
//
//                    Log.i("kisang", "confirm2");
//                    System.out.println("test2");
//                }
//            }});
//        System.out.println("noKisang2");

        init();
    }

    private void init() {
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                new AlertDialog.Builder(this)
                        .setTitle("종료")
                        .setMessage("종료 하시겠어요?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                            }
                        })
                        .setNegativeButton("아니오", null).show();
                return false;
            default:
                return false;
        }
    }
}
