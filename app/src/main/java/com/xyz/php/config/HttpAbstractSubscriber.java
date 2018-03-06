package com.xyz.php.config;

import com.xyz.php.utils.ToastUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * 2017/2/7.
 */
public abstract class HttpAbstractSubscriber<T extends BaseEntity> implements Subscriber<T> {

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        switch (t.code) {
            case 0:
                onFail(t.message);
                break;
            default:
                onSuccess(t);
                break;
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException || e instanceof IOException) {
            onNetworkError("网络不给力，请检查网络设置");
        }
    }

    @Override
    public void onComplete() {
    }

    protected abstract void onSuccess(T t);

    protected abstract void onFail(String msg);

    protected void onNetworkError(String msg) {
        ToastUtils.simple(msg);
    }
}