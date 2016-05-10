package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import kr.popcorn.sharoom.R;

public class Activity_finishJoin extends Activity {

    private TextView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_join);

        loginBtn = (TextView) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(Activity_finishJoin.this, Activity_login.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(loginIntent);
                finish();
            }
        });
    }
}
