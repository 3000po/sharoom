package kr.sam1000po.sharoom;

import android.app.Activity;
import android.os.Bundle;

import com.squareup.timessquare.*;

import java.util.Calendar;
import java.util.Date;


public class Activity_calendar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        //calendar.init(today, nextYear.getTime())
        //        .withSelectedDate(today);

        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        /*날짜 선택 안되게 date cannot selectable*/
        calendar.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {
                return false;
            }
        });


        /*특정 날짜 하이라이트 Date highlightƮ
        ArrayList<Date> list = new ArrayList<Date>();
        list.add( new Date());
        calendar.highlightDates(list);
        */

        CalendarCellDecorator cellDecorator = new CalendarCellDecorator() {
            @Override
            public void decorate(CalendarCellView calendarCellView, Date date) {

            }
        }

        //calendar.setDecorators(LIST);

        calendar.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);


    }
}
