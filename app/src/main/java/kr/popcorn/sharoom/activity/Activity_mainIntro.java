package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import kr.popcorn.sharoom.R;

public class Activity_mainIntro extends Activity {

    public static Activity_mainIntro mActivity;
    private ImageView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_intro);

        mActivity = Activity_mainIntro.this;

        //facebookBtn = (ImageView)findViewById(R.id.iv_facebook);
        //kakaoBtn = (ImageView)findViewById(R.id.iv_kakaotalk);
        loginBtn = (ImageView)findViewById(R.id.iv_start);

        /*facebookBtn.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        kakaoBtn.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */

        loginBtn.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(Activity_mainIntro.this, Activity_login.class);
                startActivity(loginIntent);

            }
        });

    }
}
