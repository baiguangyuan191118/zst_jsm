package com.zst.ynh.utils;

import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* 字符串正则表达式 或 隐藏
* */

public class StringUtil {


    // 判断EditText内容为null或者""
    public static boolean isBlankEdit(TextView view) {
        return (view == null || view.getText() == null || view.getText().length()==0);
    }

    public static String toNum(String telephone) {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(telephone);
        telephone = m.replaceAll("").trim();
        return telephone;
    }

    // 电话号码部分加*号
    public static String changeMobile(String telephone) {
        if (StringUtils.isEmpty(telephone)) {
            return "";
        }
        if (!RegexUtils.isMobileExact(telephone))
            return telephone;
        return telephone.substring(0, 3) + "****"
                + telephone.substring(7, telephone.length());
    }

    public static Boolean isPwd(String pwd){
        return RegexUtils.isMatch("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$",pwd);
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

    /**
     * list转string
     * @param list
     * @return
     */
    public static String ListToString(List<?> list) {
        StringBuffer sb = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null || list.get(i) == "") {
                    continue;
                }
                // 如果值是list类型则调用自己
                if (list.get(i) instanceof List) {
                    sb.append(ListToString((List<?>) list.get(i)));
                } else if (list.get(i) instanceof Map) {
                    sb.append(MapToString((Map<?, ?>) list.get(i)));
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return  sb.toString();
    }
    /**
     * Map转换String
     *
     * @param map
     *            :需要转换的Map
     * @return String转换后的字符串
     */
    public static String MapToString(Map<?, ?> map) {
        StringBuffer sb = new StringBuffer();
        // 遍历map
        for (Object obj : map.keySet()) {
            if (obj == null) {
                continue;
            }
            Object key = obj;
            Object value = map.get(key);
            if (value instanceof List<?>) {
                sb.append(key.toString() + ListToString((List<?>) value));
            } else if (value instanceof Map<?, ?>) {
                sb.append(key.toString()
                        + MapToString((Map<?, ?>) value));
            } else {
                sb.append(key.toString()+ value.toString());
            }
        }
        return sb.toString();
    }


    /********
     * 两个版本号比较   true需要更新 false不需要更新  mVersion<sVersion
     * @param mVersion
     * @param sVersion
     * @return  true需要更新   false不需要更新
     */
    public static boolean compareAppVersion(String mVersion,String sVersion)
    {
        String[] strs1 = mVersion.split("\\.");
        String[] strs2 = sVersion.split("\\.");

        int length = strs1.length > strs2.length ? strs1.length : strs2.length;

        for(int i = 0; i < length ;i++){
            int vi1 = 0;
            int vi2 = 0;
            if(strs1.length >= i+1){
                vi1 = Integer.parseInt(strs1[i]);
            }
            if(strs2.length >= i+1){
                vi2 = Integer.parseInt(strs2[i]);
            }
            if(vi1 < vi2){
                return true;
            }else if(vi1 > vi2){
                return false;
            }
        }
        return false;
    }

}
