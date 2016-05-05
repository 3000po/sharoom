package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import kr.popcorn.sharoom.R;

public class Activity_FinishReserv extends Activity {
    private TextView checkReserv;


    Activity_Reservation activity = (Activity_Reservation) Activity_Reservation.rActivity;
    Activity_roomInfo rActivity = (Activity_roomInfo) Activity_roomInfo.rActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_reserv);

        activity.finish();
        rActivity.finish();
        checkReserv = (TextView)findViewById(R.id.CheckReservation);
        checkReserv.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Activity_FinishReserv.this, "예약확방확인.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
