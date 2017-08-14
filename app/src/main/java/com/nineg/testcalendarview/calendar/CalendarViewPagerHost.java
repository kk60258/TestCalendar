package com.nineg.testcalendarview.calendar;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineg.testcalendarview.R;

import java.util.Calendar;
import java.util.Stack;

/**
 * Created by jason on 8/14/17.
 */

public class CalendarViewPagerHost extends FrameLayout {

    private CalendarInfo mToday;
    private final CalendarInfo mMinCalendar;
    private final CalendarInfo mMaxCalendar;
    private CalendarInfoItems mItems;

    private CalendarViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;

    public CalendarViewPagerHost(@NonNull Context context) {
        this(context, null);
    }

    public CalendarViewPagerHost(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarViewPagerHost(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Calendar minCalendar = Calendar.getInstance();
        CalendarUtils.setYear(minCalendar, 2010);
        CalendarUtils.setMonth(minCalendar, Calendar.JANUARY);
        mMinCalendar = new CalendarInfo(minCalendar);

        Calendar maxCalendar = Calendar.getInstance();
        CalendarUtils.setYear(maxCalendar, 2050);
        CalendarUtils.setMonth(maxCalendar, Calendar.DECEMBER);
        mMaxCalendar = new CalendarInfo(maxCalendar);
        mItems = new CalendarInfoItems(mMinCalendar, mMaxCalendar);
    }

    private int getTotalMonthCount() {
        return mItems.getCount();
    }

    private CalendarInfo getToday() {
        if (mToday == null) {
            mToday = new CalendarInfo(Calendar.getInstance());
        }
        return mToday;
    }

    public void setCurrentMonth(@Nullable CalendarInfo day, boolean useSmoothScroll) {
        if (day == null) {
            return;
        }
        int index = mItems.indexOf(day);
        mViewPager.setCurrentItem(index, useSmoothScroll);
    }

    private String getHeaderString(CalendarInfo info) {
        StringBuilder sb = new StringBuilder()
                .append(CalendarUtils.getMonthHeader(info.month))
                .append(" ")
                .append(String.valueOf(info.year));
        return sb.toString();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mViewPager = (ViewPager) findViewById(R.id.calendar_view_pager);
        mViewPagerAdapter = new CalendarViewPagerAdapter();
        mViewPager.setAdapter(mViewPagerAdapter);
        setCurrentMonth(getToday(), false);
    }

    class CalendarViewPagerAdapter extends PagerAdapter {
        private Stack<CalendarViewMonth> bin = new Stack<>();

        CalendarViewMonth getCachedView() {
            if (bin.isEmpty())
                return null;
            return  bin.pop();
        }

        void cacheView(CalendarViewMonth view) {
            bin.add(view);
        }

        @Override
        public int getCount() {
            return getTotalMonthCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        private CalendarViewMonth createView(Context context) {
            CalendarViewMonth result = new CalendarViewMonth(context);
            return result;
        }

        private void bindView(CalendarViewMonth view, int position) {
            view.setup(mItems.getItem(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (container == null)
                return null;

            CalendarViewMonth result = getCachedView();
            if (result == null) {
                Context context = container.getContext();
                result = createView(context);
            }
            bindView(result, position);

            container.addView(result);
            return result;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            cacheView((CalendarViewMonth) object);
        }

        @Override
        public int getItemPosition(Object object) {
            CalendarViewMonth view = (CalendarViewMonth) object;
            return mItems.indexOf(view.getInfo());
        }
    }


    public class CalendarInfoItems {
        private SparseArray<CalendarInfo> mItemCache = new SparseArray();
        private CalendarInfo min, max;
        private int count;

        public CalendarInfoItems(@NonNull CalendarInfo min, @NonNull CalendarInfo max) {
            this.min = min;
            this.max = max;
            this.count = indexOf(max) - indexOf(min) + 1;
        }

        public int getCount() {
            return count;
        }

        public int indexOf(CalendarInfo day) {
            int yDiff = day.year - min.year;
            int mDiff = day.month - min.month;

            return (yDiff * 12) + mDiff;
        }

        public CalendarInfo getItem(int position) {

            CalendarInfo re = mItemCache.get(position);
            if (re != null) {
                return re;
            }

            int numY = position / 12;
            int numM = position % 12;

            Calendar calendar = min.toCalendar();
            CalendarUtils.addYears(calendar, numY);
            CalendarUtils.addMonths(calendar, numM);

            CalendarInfo info = new CalendarInfo(calendar);
            mItemCache.put(position, info);
            return info;
        }
    }
}
