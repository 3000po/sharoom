package kr.sam1000po.sharoom;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.andexert.calendarlistview.library.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class Activity_calendar extends Activity implements com.andexert.calendarlistview.library.DatePickerController {
    private DayPickerView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        dayPickerView = (DayPickerView) findViewById(R.id.pickerView);
        dayPickerView.setController(this);
    }

    @Override
    public int getMaxYear()
    {
        return 2017;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day)
    {
        Log.e("Day Selected", day + " / " + month + " / " + year);
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays)
    {
        Log.e("Date range selected", selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString());
    }
}
