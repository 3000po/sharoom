package kr.popcorn.sharoom.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import kr.popcorn.sharoom.R;

/**
 * Created by parknature on 16. 5. 6..
 */
public class Activity_profileView extends Dialog {
    private WindowManager mManager;
    private View mView;
    private View.OnClickListener cancelListener;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        getWindow().setAttributes(mParams);
        setContentView(R.layout.activity_profileview);
    }

    public Activity_profileView(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }
}
