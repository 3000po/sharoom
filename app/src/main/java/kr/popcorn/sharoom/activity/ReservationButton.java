package kr.popcorn.sharoom.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import kr.popcorn.sharoom.R;

public class ReservationButton extends Dialog {

    private Button reservationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        mParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        getWindow().setAttributes(mParams);
        setContentView(R.layout.activity_reservation_button);

        reservationBtn = (Button)findViewById(R.id.reservationBtn);
        reservationBtn.setBackgroundResource(R.drawable.selector_facillitiesbtn);
        reservationBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.reservationBtn:
                        Toast.makeText(getContext(), "예약버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    public ReservationButton(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

    }

}
