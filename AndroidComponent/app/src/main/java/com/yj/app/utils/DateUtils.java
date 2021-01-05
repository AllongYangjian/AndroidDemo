package com.yj.app.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date getWeekStart(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,1);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return  calendar.getTime();
    }

    public static Date getWeekEnd(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,1);
        calendar.add(Calendar.DAY_OF_YEAR,6);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return  calendar.getTime();
    }
}
