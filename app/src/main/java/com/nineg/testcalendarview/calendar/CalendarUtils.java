package com.nineg.testcalendarview.calendar;

import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.APRIL;
import static java.util.Calendar.AUGUST;
import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.JANUARY;
import static java.util.Calendar.FEBRUARY;
import static java.util.Calendar.JULY;
import static java.util.Calendar.JUNE;
import static java.util.Calendar.MARCH;
import static java.util.Calendar.MAY;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.NOVEMBER;
import static java.util.Calendar.OCTOBER;
import static java.util.Calendar.SEPTEMBER;
import static java.util.Calendar.YEAR;

/**
 * Created by nineg on 2017/8/14.
 */

public class CalendarUtils {

    static Calendar sTempCalendar = Calendar.getInstance();

    public static void setYear(Calendar calendar, int year) {
        calendar.set(YEAR, year);
    }

    public static void setMonth(Calendar calendar, int month) {
        calendar.set(MONTH, month);
    }

    public static int getYear(Calendar calendar) {
        return calendar.get(YEAR);
    }

    public static int getMonth(Calendar calendar) {
        return calendar.get(MONTH);
    }

    public static int getDay(Calendar calendar) {
        return calendar.get(DATE);
    }

    public static int getDayOfWeek(Calendar calendar) {
        return calendar.get(DAY_OF_WEEK);
    }

    public static void addDays(Calendar calendar, int numberOfDays) {
        calendar.add(Calendar.DATE, numberOfDays);
    }

    public static void addMonths(Calendar calendar, int numberOfMonths) {
        calendar.add(Calendar.MONTH, numberOfMonths);
    }

    public static void addYears(Calendar calendar, int numberOfYears) {
        calendar.add(Calendar.YEAR, numberOfYears);
    }

    static int getMonthDaysCount(int year, int month) {
        switch (month) {
            case JANUARY:
            case MARCH:
            case MAY:
            case JULY:
            case AUGUST:
            case OCTOBER:
            case DECEMBER:
                return 31;
            case APRIL:
            case JUNE:
            case SEPTEMBER:
            case NOVEMBER:
                return 30;
            case FEBRUARY:
                if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }


        }
        return 0;
    }

    public static String getDayOfWeekHeader(int dayOfWeek) {
        sTempCalendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return sTempCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    }

    public static String getMonthHeader(int dayOfWeek) {
        sTempCalendar.set(Calendar.MONTH, dayOfWeek);
        return sTempCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
    }

//    static String getDayOfWeekHeader(int i) {
//        switch (i) {
//            case 0:
//                return  "SUN";
//            case 1:
//                return  "MON";
//            case 2:
//                return  "TUE";
//            case 3:
//                return  "WED";
//            case 4:
//                return  "THU";
//            case 5:
//                return  "FRI";
//            case 6:
//                return  "SAT";
//
//        }
//        return "";
//    }
}

