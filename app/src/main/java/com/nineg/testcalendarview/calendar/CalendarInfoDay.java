package com.nineg.testcalendarview.calendar;

import java.util.Calendar;

/**
 * Created by nineg on 2017/8/14.
 */

public class CalendarInfoDay extends CalendarInfo {
    boolean mIsHeader = false;
    boolean mOutOfMonthRange = false;

    public CalendarInfoDay(Calendar calendar) {
        super(calendar);
    }

    public CalendarInfoDay(int year, int month, int day) {
        super(year, month, day);
    }
}
