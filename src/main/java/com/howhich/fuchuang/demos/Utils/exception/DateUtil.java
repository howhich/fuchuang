package com.howhich.fuchuang.demos.Utils.exception;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateUtil {
    public static Date date = new Date();
    // DateUtil用于适配MybatisPlus中配合MetaObjectHandler使用
    private static final SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd ");
    private static final SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat timeWithMil = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final SimpleDateFormat timeWithMin = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String getToday(){
        return day.format(date);
    }

    public static String getTimeWithSec(){
        return time.format(date);
    }
    public static String getTimeWithMin(){
        return timeWithMin.format(date);
    }
    public static String getTimeWithMil(){
        return timeWithMil.format(date);
    }
}
