package com.xyz.php.config;

import android.support.v4.app.FragmentActivity;

/**
 * 2017/10/17.
 */
public abstract class HttpSubscriber<T extends BaseEntity> extends HttpAbstractSubscriber<T> {

    private FragmentActivity activity;

    public HttpSubscriber(FragmentActivity activity) {
        this.activity = activity;
    }
}
