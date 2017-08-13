package com.nineg.testcalendarview.calendar;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by nineg on 2017/8/12.
 */

public class CalendarViewDay extends android.support.v7.widget.AppCompatTextView {
    private CalendarInfo mInfo;
    public CalendarViewDay(Context context) {
        this(context, null);
    }

    public CalendarViewDay(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarViewDay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
    }

    void setCalendarInfoDay(CalendarInfo info) {
        mInfo = info;
        setText(Integer.toString(info.day));
    }

}
