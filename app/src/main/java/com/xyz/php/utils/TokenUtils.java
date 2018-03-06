package com.xyz.php.utils;

import android.text.TextUtils;

import com.xyz.php.constants.AppConst;
import com.xyz.php.constants.SPConst;

/**
 * 03/03/2018
 */

public class TokenUtils {

    /**
     * 保存 token 到 SharedPreferences, AES 对称加密
     *
     * @param token
     */
    public static void saveToken(String token) {
        if (TextUtils.isEmpty(token)) {
            return;
        }
        SPUtils.putString(SPConst.TOKEN, AES.encrypt(token, AppConst.SALT));
    }

    public static String getToken() {
        String token = SPUtils.getString(SPConst.TOKEN);
        return !TextUtils.isEmpty(token) ? "Bearer " + AES.decrypt(token, AppConst.SALT) : "";
    }
}
