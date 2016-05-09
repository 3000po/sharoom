package kr.popcorn.sharoom.activity.TabView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;

import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.activity.Fragment.Host.Activity_host_view;

public class Activity_User_to_Host_animation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chage_mode);

        Handler handler = new Handler();
        handler.postDelayed(new splashhandler(), 2000);
    }
    private class splashhandler implements Runnable{
        public void run() {
            startActivity(new Intent(getApplication(), Activity_host_view.class)); // 로딩이 끝난후 이동할 Activity
            Activity_User_to_Host_animation.this.finish(); // 로딩페이지 Activity Stack에서 제거
        }
    }
}
