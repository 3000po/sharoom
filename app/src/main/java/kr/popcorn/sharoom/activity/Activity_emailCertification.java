package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.facebook.appevents.AppEventsLogger;

import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.helper.Helper_find;

/**
 * Created by Administrator on 2016-03-26.
 */
public class Activity_emailCertification extends Activity {

    public String email = "whrltkd789@naver.com";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailcertification);

        emailAlert();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    public void emailAlert() {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Activity_emailCertification.this);
        alert_confirm.setMessage("이메일인증을 하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Helper_find help= new Helper_find(email);
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'No'
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }


}
