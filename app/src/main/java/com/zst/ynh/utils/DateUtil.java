package com.zst.ynh.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 获取当前中文月份
     *
     * @return 月份
     */
    public static String getCurrentMonthFormat() {
        String[] month = {"一月", "二月", "三月", "四月",
                "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

        Calendar calendar = Calendar.getInstance();
        //获得当前时间的月份，月份从0开始所以结果要加1
        int pos = calendar.get(Calendar.MONTH);
        return month[pos];
    }


    /**
     * 获取当前天数
     *
     * @return 天数
     */
    public static String getCurrentDayFormat() {
        DateFormat format = new SimpleDateFormat("dd");
        return format.format(new Date());
    }
}
