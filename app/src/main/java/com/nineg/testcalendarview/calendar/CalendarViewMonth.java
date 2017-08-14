package com.nineg.testcalendarview.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.Calendar;

/**
 * Created by nineg on 2017/8/12.
 */

public class CalendarViewMonth extends ViewGroup {
//    private TextView[] mWeekDayHeader;
    private final int mNumberOfX;
    private final int mNumberOfY;

    private int mWidthOfHeader;
    private int mHeightOfHeader;

    private int mWidthOfDayView;
    private int mHeightOfDayView;

    public CalendarViewMonth(Context context) {
        this(context, null);

    }

    public CalendarViewMonth(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarViewMonth(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mNumberOfX = 7;
        mNumberOfY = 6;
        int size = mNumberOfX * mNumberOfY;
        int widthOfHeader = 100;
        int heightOfHeader = 57;

        int widthOfDayView = 68;
        int heightOfDayView = 62;

        //raw y = 0
        for(int x = 0; x < mNumberOfX; ++x) {
            CalendarViewDay cdv = new CalendarViewDay(context);
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.width = widthOfHeader;
            lp.height = heightOfHeader;
            cdv.setText(CalendarUtils.getDayOfWeekHeader(x + Calendar.SUNDAY));
            addView(cdv, lp);
        }

        for(int y = 1; y < mNumberOfY; ++y) {
            for (int x = 0; x < mNumberOfX; ++x) {
                CalendarViewDay cdv = new CalendarViewDay(context);
                LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.width = widthOfDayView;
                lp.height = heightOfDayView;
                addView(cdv, lp);
            }
        }

        mWidthOfHeader = widthOfHeader;
        mHeightOfHeader = heightOfHeader;

        mWidthOfDayView = widthOfDayView;
        mHeightOfDayView = heightOfDayView;
    }

    private CalendarInfo mCalendarInfo;
    private int mCurrentMonth;
    private int mCurrentYear;

    private Calendar mMinDate;
    private Calendar mMaxDate;
    //@params month must be
    public void setup(CalendarInfo calendarInfo) {
        mCalendarInfo = calendarInfo;
        mCurrentYear = calendarInfo.year;
        mCurrentMonth = calendarInfo.month;

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(mCurrentYear, mCurrentMonth, 1);
        int firstWeekDay = CalendarUtils.getDayOfWeek(calendar); // 1 for Sunday
        int daysInLastMonth = (firstWeekDay - Calendar.SUNDAY);
        CalendarUtils.addDays(calendar, -1 * daysInLastMonth);

        mMinDate = (Calendar) calendar.clone();

        for(int y = 1; y < mNumberOfY; ++y) {
            for (int x = 0; x < mNumberOfX; ++x) {
                int index = x + y * mNumberOfX;
                CalendarViewDay cdv = (CalendarViewDay) getChildAt(index);
                CalendarInfoDay infoDay = new CalendarInfoDay(calendar);
                infoDay.mOutOfMonthRange = CalendarUtils.getMonth(calendar) != mCurrentMonth;
                cdv.setCalendarInfoDay(infoDay);
                CalendarUtils.addDays(calendar, 1);
            }
        }

        mMaxDate = (Calendar) calendar.clone();
    }


    public CalendarInfo getInfo() {
        return mCalendarInfo;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specSizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specSizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int availableWidth = specSizeWidth - getPaddingStart() - getPaddingEnd();
        int availableHeight = specSizeHeight - getPaddingTop() - getPaddingBottom();

        int childSpaceWidth = availableWidth / mNumberOfX;
        int marginY = (availableHeight - mHeightOfHeader - mHeightOfDayView * (mNumberOfY - 1)) / (mNumberOfY - 1);

        int childCount = getChildCount();
        for(int i = 0; i < childCount; ++i) {
            CalendarViewDay cdv = (CalendarViewDay) getChildAt(i);
            LayoutParams clp = (LayoutParams) cdv.getLayoutParams();
            int cellx = i % mNumberOfX;
            int celly = i / mNumberOfX;
            int leftWithinSpace = Math.max(0, (childSpaceWidth - clp.width) / 2);
            clp.x =  getPaddingStart() + cellx * childSpaceWidth + leftWithinSpace;
            clp.y = getPaddingTop() + marginY * celly + mHeightOfDayView * celly;
            int cdvWidthSpec = MeasureSpec.makeMeasureSpec(clp.width, MeasureSpec.EXACTLY);
            int cdvHeightSpec = MeasureSpec.makeMeasureSpec(clp.height, MeasureSpec.EXACTLY);
            cdv.measure(cdvWidthSpec, cdvHeightSpec);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for(int i = 0; i < childCount; ++i) {
            CalendarViewDay cdv = (CalendarViewDay) getChildAt(i);
            LayoutParams clp = (LayoutParams) cdv.getLayoutParams();
            cdv.layout(clp.x, clp.y, clp.x + clp.width, clp.y + clp.height);
        }
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        int x, y;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
