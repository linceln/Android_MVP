package com.xyz.php.utils;

import android.widget.Toast;

import com.xyz.php.config.BaseApplication;

/**
 * 2017/10/19.
 */

public class ToastUtils {

    public static void simple(String msg) {
        Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }
}
