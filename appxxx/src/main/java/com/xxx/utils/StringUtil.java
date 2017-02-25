package com.xxx.utils;

import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lousx on 2016/8/2.
 */
public class StringUtil {

    //判断是否为手机号
    public static boolean isMobile(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    //判读值是否为null
    public static void setNoNull(TextView textView, String text) {
        if (text != null && !text.equals("null")) {
            textView.setText(text);
        } else {
            textView.setText("");
        }
    }

    //判读值是否为null
    public static boolean isNull(String text) {
        if (text != null && !text.equals("null") && !text.equals(""))
            return true;
        return false;
    }

    /**
     * 验证身份证号是否符合规则
     *
     * @param text 身份证号
     * @return
     */
    public static boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }


    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    //判断email格式是否正确
    public static String numString(String num) {
        DecimalFormat fnum  =   new DecimalFormat("##0.0");
        StringBuffer attention_num = new StringBuffer();
        if (num == null || num.equals("")) {
            num = "0";
        }

        if (Integer.parseInt(num) >= 1000) {
            attention_num .append(fnum.format(Float.valueOf(num) / 1000));
            attention_num .append("k");
        } else if (Integer.parseInt(num) >= 10000) {
            attention_num .append(fnum.format(Float.valueOf(num) / 10000));
            attention_num.append("万");
        } else {
            attention_num.append(num);
        }
        return attention_num.toString();
    }
}
