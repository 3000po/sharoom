package kr.sam1000po.sharoom;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.Range;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        //calendar.init(today, nextYear.getTime())
        //        .withSelectedDate(today);

        calendar.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);
        calendar.
    }
}
