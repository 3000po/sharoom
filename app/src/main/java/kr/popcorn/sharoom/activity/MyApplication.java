package kr.popcorn.sharoom.activity;

import android.app.Application;

/**
 * Created by parknature on 16. 3. 7..
 */
public class MyApplication extends Application {
    private String mGlobalString;

    public String getGlobalString()
    {
        return mGlobalString;
    }

    public void setGlobalString(String globalString)
    {
        this.mGlobalString = globalString;
    }


}
