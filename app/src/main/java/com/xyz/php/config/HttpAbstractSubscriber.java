package com.xyz.php.config;

import android.util.Log;

import com.xyz.php.BuildConfig;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * 2017/2/7.
 */
abstract class HttpAbstractSubscriber<T extends BaseEntity> implements Subscriber<T> {

    private static final String TAG = "HTTP";

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        if (t.statusCode >= 200 && t.statusCode < 300) {
            switch (t.code) {
                case 0:
                    onFail(t.msg);
                    break;
                case 1:
                    onSuccess(t);
                    break;
                case 1001:
                    on1001();
                    break;
                default:
                    onFail(t.msg);
                    break;
            }
        } else {
            if (BuildConfig.DEBUG) {
                onFail(t.statusText);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, e.getMessage(), e);
        if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            onFail("网络不给力，请检查网络设置～");
        } else {
            if (BuildConfig.DEBUG) {
                onFail(e.getMessage());
            }
        }
    }

    @Override
    public void onComplete() {
    }

    protected abstract void onSuccess(T t);

    protected abstract void onFail(String msg);

    protected abstract void on1001();
}