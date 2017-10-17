package com.xyz.core.http;

import android.support.v4.app.FragmentActivity;

import com.xyz.core.base.BaseEntity;

/**
 * 2017/10/17.
 */
public abstract class HttpSubscriber<T extends BaseEntity> extends HttpAbstractSubscriber<T> {

    private FragmentActivity activity;

    public HttpSubscriber(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void on1001() {

    }
}
