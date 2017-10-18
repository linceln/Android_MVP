package com.xyz.core.base;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * 17/10/2017
 */
public abstract class AbstractApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
