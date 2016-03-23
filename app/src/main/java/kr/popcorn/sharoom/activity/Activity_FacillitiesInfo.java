package kr.popcorn.sharoom.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import kr.popcorn.sharoom.R;

public class Activity_FacillitiesInfo extends Dialog {

    private View.OnClickListener cancelListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        mParams.gravity = Gravity.CENTER;


        getWindow().setAttributes(mParams);

        setContentView(R.layout.activity_facillities_info);
    }

    public Activity_FacillitiesInfo(Context context, View.OnClickListener singleLisener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.cancelListener = singleLisener;
    }

}
