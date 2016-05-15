package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.activity.View.User.Activity_user_reservation;
import kr.popcorn.sharoom.activity.View.User.Activity_user_infoRoom;

public class Activity_FinishReserv extends Activity {
    private TextView checkReserv;


    Activity_user_reservation activity = (Activity_user_reservation) Activity_user_reservation.rActivity;
    Activity_user_infoRoom rActivity = (Activity_user_infoRoom) Activity_user_infoRoom.rActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_reserv);

        activity.finish();
        checkReserv = (TextView)findViewById(R.id.CheckReservation);
        checkReserv.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Activity_FinishReserv.this, "예약확방확인.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
