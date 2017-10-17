package com.xyz.php;

import com.xyz.core.base.AbstractApplication;

/**
 * 17/10/2017
 */
public class BaseApplication extends AbstractApplication {

    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
