package com.xyz.core.http;

import android.content.Context;
import android.util.Log;

import com.xyz.core.entity.BaseEntity;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 2017/2/7.
 */
public abstract class HttpSubscriber<T extends BaseEntity> extends Subscriber<T> {

    private static final String TAG = "HTTP";

    private Context context;

    public HttpSubscriber(Context context) {
        this.context = context;
    }

    protected abstract void onSuccess(T entity);

    protected abstract void onFail(String msg);

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, e.getMessage(), e);
        if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            onFail("网络不给力，请检查网络设置");
        } else {
            onFail(e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        switch (t.code) {
            case 0:
                onFail(t.message);
                break;
            case 1:
            case 2:
            case 3:
                onSuccess(t);
                break;
            default:
                onFail(t.message);
                break;
        }
    }

    @Override
    public void onCompleted() {
    }
}