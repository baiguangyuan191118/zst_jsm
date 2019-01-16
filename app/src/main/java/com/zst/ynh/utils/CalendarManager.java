package com.zst.ynh.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.zst.ynh.bean.CalendarBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import static com.zst.ynh.config.SPkey.PREF_DATE_LIST;

/**
 * Created by yangxiangjie on 2017/11/13.
 * 日历插入日程的工具类
 */

public enum CalendarManager {

    INSTANCE;
    private static String CALENDAR_URL = "content://com.android.calendar/calendars";
    private static String CALENDER_EVENT_URL = "content://com.android.calendar/events";
    private static String CALENDER_REMINDER_URL = "content://com.android.calendar/reminders";


    private static String CALENDARS_NAME = "test";
    private static String CALENDARS_ACCOUNT_NAME = "test@gmail.com";
    private static String CALENDARS_ACCOUNT_TYPE = "com.android.exchange";
    private static String CALENDARS_DISPLAY_NAME = "测试账户";
    private static long ONE_HOUR = 60 * 60 * 1000;
    private static final int REQUEST_CALENDAR_STATE = 100;

    public static String YNH_TITLE = "由你花还款提醒";


    public void requestCalendarPermission(final Activity context, final List<CalendarBean> date) {
        if (XXPermissions.isHasPermission(context, Permission.Group.CALENDAR)) {
            checkCalendarEvent(context, date);
        } else {
            XXPermissions.with(context)//可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    .permission(Permission.Group.CALENDAR)
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (!isAll) {
                                ToastUtils.showShort("为了您能正常使用，请授权");
                            } else {
                                checkCalendarEvent(context, date);
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if (quick) {
                                ToastUtils.showShort("被永久拒绝授权，请手动授予权限");
                                //如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.gotoPermissionSettings(context);
                            } else {
                                ToastUtils.showShort("获取权限失败");
                            }
                        }
                    });
        }
    }

    private void checkCalendarEvent(Activity context, List<CalendarBean> date) {
        if (CalendarManager.INSTANCE.checkAndAddCalendarAccount(context) != -1 && date != null) {

            Set<String> stringSet = SPUtils.getInstance().getStringSet(PREF_DATE_LIST, null);
            if (stringSet != null && stringSet.size() != 0) {//删除已经完成的事件
                List<String> temp = new ArrayList<>();
                for (String sp : stringSet) {
                    for (CalendarBean calendarBean : date) {
                        if (calendarBean.title.equals(sp)) {
                            break;
                        } else {
                            temp.add(sp);

                        }
                    }
                }
                for (String t : temp) {
                    stringSet.remove(t);
                    CalendarManager.INSTANCE.deleteCalEvent(context, new String[]{t});
                }
            }
            for (CalendarBean d : date) {
                CalendarManager.INSTANCE.addCalEvent(context, d.date, d.title);
            }

        }
    }

    /**
     * 检查是否有现有存在的账户
     *
     * @param context 上下文
     * @return -1 ：当前没有账户
     */
    private int checkCalendarAccount(Context context) {
        Cursor userCursor = context.getContentResolver().query(Uri.parse(CALENDAR_URL), null, null, null, null);
        try {
            if (userCursor == null) {
                //查询返回空值
                return -1;
            }
            int count = userCursor.getCount();
            if (count > 0) {
                //存在现有账户，取第一个账户的id返回
                userCursor.moveToFirst();
                return userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
            } else {
                return -1;
            }
        } finally {
            if (userCursor != null) {
                userCursor.close();
            }
        }
    }

    /**
     * 添加账户
     *
     * @param context 上下文
     * @return -1 : 添加失败
     */
    private long addCalendarAccount(Context context) {
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME);

        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE);
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME);
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = Uri.parse(CALENDAR_URL);
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
                .build();

        Uri result = context.getContentResolver().insert(calendarUri, value);
        long id = result == null ? -1 : ContentUris.parseId(result);
        return id;
    }

    /**
     * 检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
     *
     * @param context 上下文
     * @return -1： 访问不到日历也创建不了日历账户
     */
    public int checkAndAddCalendarAccount(Context context) {
        int oldId = checkCalendarAccount(context);
        if (oldId >= 0) {
            return oldId;
        } else {
            long addId = addCalendarAccount(context);
            if (addId >= 0) {
                return checkCalendarAccount(context);
            } else {
                return -1;
            }
        }
    }


    /**
     * 添加日程
     *
     * @param context 上下文
     * @param date    格式：2017—11-16 日期
     */
    public void addCalEvent(Context context, String date, String title) {
        Uri uri = null;
        if (TextUtils.isEmpty(date) || !date.contains("-") || date.split("-").length < 3) {
            return;
        }

        Cursor eventCursor = context.getContentResolver().query(Uri.parse(CALENDER_EVENT_URL), null, null, null, null);
        if (eventCursor.getCount() > 0) {
            //遍历所有事件，找到title跟需要查询的title一样的项
            for (eventCursor.moveToFirst(); !eventCursor.isAfterLast(); eventCursor.moveToNext()) {
                String startTime = eventCursor.getString(eventCursor.getColumnIndex(CalendarContract.Events.DTSTART));
                if (TimeUtils.getTimeSpan(Long.parseLong(startTime), TimeUtils.getNowMills(), TimeConstants.DAY) < 0) {//今天之前的事件跳过
                    continue;
                }
                startTime = TimeUtils.getString(Long.parseLong(startTime), new SimpleDateFormat("yyyy-MM-dd"), 0, TimeConstants.DAY);
                long day = TimeUtils.getTimeSpan(startTime, date, new SimpleDateFormat("yyyy-MM-dd"), TimeConstants.DAY);
                String eventTitle = eventCursor.getString(eventCursor.getColumnIndex(CalendarContract.Events.TITLE));
                if (title.equals(eventTitle) && day == 0) {//时间的title和日期一样时，事件已添加
                    return;
//                    int id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID));//取得id
                }
            }
        }

        int year, month, day;
        String[] split = date.split("-");
        year = Integer.valueOf(split[0]);
        month = Integer.valueOf(split[1]) == 0 ? Integer.valueOf(split[1]) : Integer.valueOf(split[1]) - 1;
        day = Integer.valueOf(split[2]);

        //先定义一个URL，到时作为调用系统日历的uri的参数
        String calanderRemiderURL = "";
        if (Build.VERSION.SDK_INT >= 8) {
            calanderRemiderURL = "content://com.android.calendar/reminders";
        } else {
            calanderRemiderURL = "content://calendar/reminders";
        }
        long calID = checkAndAddCalendarAccount(context);
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        //注意，月份的下标是从0开始的
        beginTime.set(year, month, day, 10, 0);
        //插入日历时要取毫秒计时
        startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, day, 23, 59);
        endMillis = endTime.getTimeInMillis();

        //插入事件
        ContentValues eValues = new ContentValues();
        //插入提醒，与事件配合起来才有效
        ContentValues rValues = new ContentValues();
        //获取默认时区
        TimeZone tz = TimeZone.getDefault();

        //插入日程
        eValues.put(CalendarContract.Events.DTSTART, startMillis);
        eValues.put(CalendarContract.Events.DTEND, endMillis);
        eValues.put(CalendarContract.Events.TITLE, title);
//        eValues.put(CalendarContract.Events.DESCRIPTION, des);
        eValues.put(CalendarContract.Events.CALENDAR_ID, calID);
//        eValues.put(CalendarContract.Events.EVENT_LOCATION, "计算机学院");
        eValues.put(CalendarContract.Events.EVENT_TIMEZONE, tz.getID());
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            uri = context.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, eValues);
        }
        if (uri != null) {
            Set<String> stringSet = SPUtils.getInstance().getStringSet(PREF_DATE_LIST, null);
            if (!title.equals(YNH_TITLE)) {
                if (stringSet == null) {
                    stringSet = new HashSet<>();
                }
                stringSet.add(title);
                SPUtils.getInstance().put(PREF_DATE_LIST, stringSet);
            }

        } else {
            return;
        }

        //插完日程之后必须再插入以下代码段才能实现提醒功能
        // 得到当前表的_id
        String myEventsId = uri.getLastPathSegment();
        rValues.put("event_id", myEventsId);
        //提前10分钟提醒
        rValues.put("minutes", 10);
        //如果需要有提醒,必须要有这一行
        rValues.put("method", 1);
        context.getContentResolver().insert(Uri.parse(calanderRemiderURL), rValues);
    }


    /**
     * 删除日程
     *
     * @param context 上下文
     * @param title   日程标题
     */
    public boolean deleteCalEvent(Context context, String[] title) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            int rows = context.getContentResolver().delete(CalendarContract.Events.CONTENT_URI, CalendarContract.Events.TITLE + "=?", title);
            if (-1 == rows) {
                return false;
            } else {
                return true;
            }
        }

    }

}
