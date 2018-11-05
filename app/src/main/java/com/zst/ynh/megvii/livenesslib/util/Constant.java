
package com.zst.ynh.megvii.livenesslib.util;

import android.os.Environment;

import com.zst.ynh.BuildConfig;


public class Constant {

    public static String cacheText = "livenessDemo_text";
    public static String cacheImage = "livenessDemo_image";
    public static String cacheCampareImage = "livenessDemo_campareimage";
    public static String dirName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+ BuildConfig.APPLICATION_ID;
}