package com.nineg.testcalendarview.calendar;

import java.util.Calendar;

/**
 * Created by nineg on 2017/8/14.
 */

public class CalendarInfo {
    final int year;
    final int month;
    final int day;

    public CalendarInfo(Calendar calendar) {
        this(
                CalendarUtils.getYear(calendar),
                CalendarUtils.getMonth(calendar),
                CalendarUtils.getDay(calendar)
        );
    }


    public CalendarInfo(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
