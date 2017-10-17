package com.xyz.core.base;

import android.app.Application;

import com.facebook.stetho.Stetho;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 17/10/2017
 */
public abstract class AbstractApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("realm.core").build();
        Realm.setDefaultConfiguration(configuration);
    }
}
