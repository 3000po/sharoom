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

        init(); // �������ʱ�ȭ
    }

    @Override
    protected void onResume() {

        super.onResume();

        // �� �����带 ����
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

    // ------------------ ��������� �Լ�-------------------

    private void init() {

    }

    private void start_thread() {

        Toast.makeText(this, "OnResume���� ���ξ����带 �����ŵ�ϴ�.", Toast.LENGTH_SHORT).show();

        DisplayHandler.postDelayed(new Runnable() {

            public void run() {

                /*
                // �ý���ȯ���ҵ��
                if (Data.setting_login) {

                    // �ڵ��α���

                } else {

                    // ����ڰ��Ծ�Ƽ��Ƽ�� �̵�

                    Intent intent = new Intent(MainActivity.this, Activity_calendar.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);

                }
                */
            }
        }, 3000); // �ð�����

    }

    // ---------------- ������̺�Ʈ�ڵ鷯 --------------------

    public void buttonClicked(View v) {

        Toast.makeText(this, "����ڹ�ư onclick button�� ���������ϴ�.", Toast.LENGTH_SHORT).show();

        switch (v.getId()) {

		/*
		 * case R.id.reconnect:
		 *
		 * //�ٽ� �õ��ϱ�
		 *
		 * DisplayHandler.postDelayed(Decision, 0);
		 *
		 * break;
		 */

        }

    }

}
