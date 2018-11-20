package com.zst.ynh.utils;

import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    // 判断字符串对象为null或者""
    public static boolean isBlank(String str) {
        return (str == null || str.length()==0 || "null".equals(str));
    }

    // 判断EditText内容为null或者""
    public static boolean isBlankEdit(TextView view) {
        return (view == null || view.getText() == null || view.getText().length()==0);
    }

    // 判断是否是手机号码
    public static boolean isMobileNO(String mobiles) {
        if (isBlank(mobiles))
            return false;
        Pattern p = Pattern.compile("^(86)?1[0-9]{10}$");
        // ^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    // 电话号码部分加*号
    public static String changeMobile(String telephone) {
        if (isBlank(telephone)) {
            return "";
        }
        if (!isMobileNO(telephone))
            return telephone;
        return telephone.substring(0, 3) + "****"
                + telephone.substring(7, telephone.length());
    }
}
