package com.nineg.testcalendarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nineg.testcalendarview.calendar.CalendarViewMonth;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarViewMonth cmv =  (CalendarViewMonth) findViewById(R.id.calendar_view);
        cmv.setup(2017, Calendar.AUGUST);
//        cmv.
    }
}
