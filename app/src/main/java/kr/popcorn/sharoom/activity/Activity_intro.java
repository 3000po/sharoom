package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import kr.popcorn.sharoom.R;

public class Activity_intro extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // 주 쓰레드를 실행
        start_thread();
        init(); // 디자인초기화

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        //AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onResume() {

        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        //AppEventsLogger.activateApp(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_intro, menu);
        return true;
    }

    Handler DisplayHandler = new Handler();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {

            case KeyEvent.KEYCODE_BACK:

                moveTaskToBack(true);

                Intent setIntent = new Intent(Intent.ACTION_MAIN);
                setIntent.addCategory(Intent.CATEGORY_HOME);
                setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(setIntent);

            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    private void init() {

    }

    private void start_thread() {

        Toast.makeText(this, "방풀에 오신 것을 환영합니다.", Toast.LENGTH_SHORT).show();

        DisplayHandler.postDelayed(new Runnable() {

            public void run() {

                    Intent intent = new Intent(Activity_intro.this, Activity_login.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
            }
        }, 3000); // 시간지정

    }

    // ---------------- 사용자이벤트핸들러 --------------------

    public void buttonClicked(View v) {

        Toast.makeText(this, "onclick 사용자 버튼", Toast.LENGTH_SHORT).show();

        switch (v.getId()) {

		/*
		 * case R.id.reconnect:
		 *

		 *
		 * DisplayHandler.postDelayed(Decision, 0);
		 *
		 * break;
		 */

        }

    }

}