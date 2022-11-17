package com.tungjj.user.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.tungjj.user.exception.InvalidDateFormat;

public class DateFormat {
    public static Date getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        return date;
    }

    public static String toDateString(Date date, String type) {
        if(date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        String result = null;
        try {
            result = sdf.format(date);
        } catch (Exception e) {
            throw new InvalidDateFormat("Wrong date format. Date must be format: " + type);
        }
        return result;
    }
}
