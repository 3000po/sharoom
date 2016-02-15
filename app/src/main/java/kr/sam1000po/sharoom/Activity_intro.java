package kr.sam1000po.sharoom;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class Activity_intro extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        init(); // 디자인초기화

    }

    @Override
    protected void onResume() {

        super.onResume();

        // 주 쓰레드를 실행
        start_thread();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    // ------------------ 사용자정의 함수-------------------

    private void init() {

    }

    private void start_thread() {

        Toast.makeText(this, "OnResume에서 메인쓰레드를 실행시킵니다.", Toast.LENGTH_SHORT).show();

        DisplayHandler.postDelayed(new Runnable() {

            public void run() {

                /*
                // 시스템환경요소등록
                if (Data.setting_login) {

                    // 자동로그인

                } else {

                    // 사용자가입액티버티로 이동

                    Intent intent = new Intent(MainActivity.this, Activity_calendar.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);

                }
                */
            }
        }, 3000); // 시간지정

    }

    // ---------------- 사용자이벤트핸들러 --------------------

    public void buttonClicked(View v) {

        Toast.makeText(this, "사용자버튼 onclick button이 눌러졌습니다.", Toast.LENGTH_SHORT).show();

        switch (v.getId()) {

		/*
		 * case R.id.reconnect:
		 *
		 * //다시 시도하기
		 *
		 * DisplayHandler.postDelayed(Decision, 0);
		 *
		 * break;
		 */

        }

    }

}
