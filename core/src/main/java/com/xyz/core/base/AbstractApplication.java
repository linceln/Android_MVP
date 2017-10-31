package com.xyz.core.base;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * 17/10/2017
 */
public abstract class AbstractApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        Stetho.initializeWithDefaults(this);
    }
}
