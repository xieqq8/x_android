/**
 * copyright(C)2014-
 */
package com.xxx.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author
 * @code: create：15-10-20下午4:35
 * class:
 */
public class DateUtil {
    public static long MINSPACE = 5 * 60 * 1000;//five minute.

    public static String parseSpaceTime(long t, long lt) {
        if ((lt - t) < MINSPACE) return null;
        return parseTime(t, lt);
    }

    public static String parseTimeYYmmDD(long t) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(t);
        String month;
        int m = date.get(Calendar.MONTH) + 1;
        if (m < 10) {
            month = String.valueOf("0" + m);
        } else {
            month = String.valueOf(m);
        }

        String day;
        int d = date.get(Calendar.DAY_OF_MONTH);
        if (d < 10) {
            day = String.valueOf("0" + d);
        } else {
            day = String.valueOf(d);
        }
        return date.get(Calendar.YEAR) + "-" + month + "-" + day;
    }

    public static String parseTime(long t, long lt) {
        if (lt == 0) {
            Calendar date = Calendar.getInstance();
            return date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DAY_OF_MONTH);
        }

        String txt;
        long time = (lt - t) / 1000;
        if (time < 60) {
            txt = "刚刚";
        } else if (time < 60 * 60) {
            int min = (int) Math.floor(time / 60);
            txt = min + "分钟前";
        } else {
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(lt);
            int cDay = date.get(Calendar.DAY_OF_MONTH);
            date.setTimeInMillis(t);
            int hours = date.get(Calendar.HOUR_OF_DAY);
            int minutes = date.get(Calendar.MINUTE);
            String htime = (hours > 9 ? "" : "0") + hours + ":" + (minutes > 9 ? "" : "0") + minutes;

            int dayOff = cDay - date.get(Calendar.DAY_OF_MONTH);
            if (time < 60 * 60 * 24) {
                int h = (int) Math.floor(time / (60 * 60));
                txt = h + "小时前";
                if (dayOff == 1)
//                    txt = "昨天 "+htime;
                    txt = "昨天 ";
            } else if (time < 60 * 60 * 24 * 2) {
//                txt = "昨天 "+htime;
                txt = "昨天";
                if (dayOff == 2)
//                    txt = "前天 "+htime;
                    txt = "前天 ";
            } else {
                int year = date.get(Calendar.YEAR);
                String rtime = year + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DAY_OF_MONTH);
                if (time < 60 * 60 * 24 * 3) {
//                    txt = "前天 "+htime;
                    txt = "前天 ";
                    if (dayOff > 2)
                        txt = rtime;
                } else {
                    txt = rtime;
                }
            }
        }
        return txt;
    }

    public static String parseSpaceTime(long t) {
        String txt;
        long current = System.currentTimeMillis();
        long time = (current - t) / 1000;
        if (time < 60) {
            txt = "刚刚";
        } else if (time < 60 * 60) {
            int min = (int) Math.floor(time / 60);
            txt = min + "分钟前";
        } else {
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(current);
            int cDay = date.get(Calendar.DAY_OF_MONTH);
            date.setTimeInMillis(t);
            int hours = date.get(Calendar.HOUR_OF_DAY);
            int minutes = date.get(Calendar.MINUTE);
            String htime = (hours > 9 ? "" : "0") + hours + ":" + (minutes > 9 ? "" : "0") + minutes;

            int dayOff = cDay - date.get(Calendar.DAY_OF_MONTH);
            if (time < 60 * 60 * 24) {
                int h = (int) Math.floor(time / (60 * 60));
                txt = h + "小时前";
                if (dayOff == 1)
//                    txt = "昨天 "+htime;
                    txt = "昨天";
            } else if (time < 60 * 60 * 24 * 2) {
//                txt = "昨天 "+htime;
                txt = "昨天";
                if (dayOff == 2)
//                    txt = "前天 "+htime;
                    txt = "前天";
            } else {
                int year = date.get(Calendar.YEAR);
                String rtime = year + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DAY_OF_MONTH);
                if (time < 60 * 60 * 24 * 3) {
//                    txt = "前天 "+htime;
                    txt = "前天";
                    if (dayOff > 2)
                        txt = rtime;
                } else {
                    txt = rtime;
                }
            }
        }
        return txt;
    }

    public static String parseSpaceDetailedTime(long t) {
        String txt;
        long current = System.currentTimeMillis() / 1000;
        long time = current - t;
        if (time < 60) {
            txt = "刚刚";
        } else if (time < 60 * 60) {
            int min = (int) Math.floor(time / 60);
            txt = min + "分钟前";
        } else {
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(current * 1000);
            int cDay = date.get(Calendar.DAY_OF_MONTH);
            date.setTimeInMillis(t * 1000);
            int hours = date.get(Calendar.HOUR_OF_DAY);
            int minutes = date.get(Calendar.MINUTE);
            String htime = (hours > 9 ? "" : "0") + hours + ":" + (minutes > 9 ? "" : "0") + minutes;

            int dayOff = cDay - date.get(Calendar.DAY_OF_MONTH);
            if (time < 60 * 60 * 24) {
                int h = (int) Math.floor(time / (60 * 60));
                txt = h + "小时前";
                if (dayOff == 1)
                    txt = "昨天 " + htime;
            } else if (time < 60 * 60 * 24 * 2) {
                txt = "昨天 " + htime;
                if (dayOff == 2)
                    txt = "前天 " + htime;
            } else {
                int year = date.get(Calendar.YEAR);
                int month = date.get(Calendar.MONTH) + 1;
                int day = date.get(Calendar.DAY_OF_MONTH);
                String rtime = year + "-" + (month > 9 ? "" : "0") + month + "-" + (day > 9 ? "" : "0") + day;
                if (time < 60 * 60 * 24 * 3) {
                    txt = "前天 " + htime;
                    if (dayOff > 2)
                        txt = rtime + " " + htime;
                } else {
                    txt = rtime + " " + htime;
                }
            }
        }
        return txt;
    }

    public static String formatLeaveTime(long base) {
        long left = base - System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();

        int min = (int) Math.floor(left / 1000 / 60);
        int sec = (int) (left / 1000 - min * 60);
        sb.append(min).append("分").append(sec > 9 ? sec : ("0" + sec)).append("秒");
        return sb.toString();
    }

    public static String AnsTime(long base) {
        StringBuffer sb = new StringBuffer();
        int sec = (int) (base % 60);
        int min = (int) ((base / 60) % 60);
        int hours = (int) (base / 3600);
        if (hours == 0) {
            if (min == 0) {
                if (sec == 0) {
                    sb.append("0秒");
                } else {
                    sb.append(sec).append("秒");
                }
            } else {
                sb.append(min).append("分").append(sec > 9 ? sec : ("0" + sec)).append("秒");
            }
        } else {
            sb.append(hours).append("时").append(min).append("分").append(sec > 9 ? sec : ("0" + sec)).append("秒");
        }
        return sb.toString();
    }

    public static long getTime(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date d = sdf.parse(time);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String formatTime(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    /* 将字符串转为时间戳 */
    public static long getStringToDate(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}
