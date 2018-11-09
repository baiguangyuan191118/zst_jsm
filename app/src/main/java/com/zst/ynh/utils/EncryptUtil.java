package com.zst.ynh.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.RegexUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncryptUtil {
    // 电话号码过滤特殊字符
    public static String toNum(String telephone) {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(telephone);
        telephone = m.replaceAll("").trim();
        return telephone;
    }
    // 电话号码部分加*号
    public static String changeMobile(String telephone) {
        if (TextUtils.isEmpty(telephone)) {
            return "";
        }
        if (!RegexUtils.isMobileSimple(telephone))
            return telephone;
        return telephone.substring(0, 3) + "****"
                + telephone.substring(7, telephone.length());
    }

    // 姓名加*号
    public static String changeName(String name) {
        if (TextUtils.isEmpty(name)) {
            return "";
        }
        return "*" + name.substring(1, name.length());
    }

    // 身份证号加*号
    public static String changeIdentity(String identity) {
        if (TextUtils.isEmpty(identity)) {
            return "";
        }
        if (identity.length() != 15 && identity.length() != 18) {
            return "身份证号码异常";
        }
        return identity.substring(0, 4) + "************"
                + identity.substring(identity.length() - 2, identity.length());
    }
}
