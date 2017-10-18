package com.xyz.php.constants;

import com.xyz.php.R;
import com.xyz.php.config.BaseApplication;

/**
 * 2017/10/18.
 */
public class UrlConst {

    public static String baseUrl(){
        return BaseApplication.getInstance().getString(R.string.base_url);
    }
}
