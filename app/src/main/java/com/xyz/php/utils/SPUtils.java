package com.xyz.php.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xyz.php.config.BaseApplication;
import com.xyz.php.constants.SPConst;

/**
 * 2017/10/23.
 */
public class SPUtils {

    public static void putString(String key, String value) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(SPConst.SP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(SPConst.SP_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static String getString(String key) {
        return getString(key, "");
    }
}
