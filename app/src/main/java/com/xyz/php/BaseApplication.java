package com.xyz.php;

import com.xyz.core.base.AbstractApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("realm.core")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
