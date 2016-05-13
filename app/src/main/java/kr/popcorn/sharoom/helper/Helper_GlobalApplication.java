package kr.popcorn.sharoom.helper;


import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.kakao.auth.KakaoSDK;

public class Helper_GlobalApplication extends Application {
    private static Helper_GlobalApplication mInstance;
    private static volatile Activity currentActivity = null;

    public static Activity getCurrentActivity() {
        Log.d("TAG", "++ currentActivity : " + (currentActivity != null ? currentActivity.getClass().getSimpleName() : ""));
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        Helper_GlobalApplication.currentActivity = currentActivity;
    }

    /**
     * singleton
     * @return singleton
     */
    public static Helper_GlobalApplication getGlobalApplicationContext() {
        if(mInstance == null)
            throw new IllegalStateException("this application does not inherit GlobalApplication");
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }
}