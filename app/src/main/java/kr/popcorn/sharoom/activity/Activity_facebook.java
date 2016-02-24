package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.FacebookActivity;
import com.facebook.appevents.AppEventsLogger;

import kr.popcorn.sharoom.R;

/**
 * Created by user on 16. 2. 22.
 */
public class Activity_facebook extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
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
}
