package com.nineg.testcalendarview;

import android.app.Activity;
import android.os.Bundle;

import com.nineg.testcalendarview.calendar.CalendarViewPagerHost;

import java.util.Calendar;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarViewPagerHost cvph =  (CalendarViewPagerHost) findViewById(R.id.calendar_view_pager_host);
    }
}
