package com.xyz.php.config;

import com.xyz.php.BuildConfig;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * 2017/2/7.
 */
abstract class HttpAbstractSubscriber<T extends BaseEntity> implements Subscriber<T> {

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        if (t.statusCode == 200) {
            switch (t.code) {
                case 0:
                    onFail(t.msg);
                    break;
                case 1:
                    onSuccess(t);
                    break;
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            onFail("网络不给力，请检查网络设置");
        } else {
            if (BuildConfig.DEBUG) {
                onFail(e.getMessage());
            } else {
                onFail("网络请求出现异常，请稍后重试");
            }
        }
    }

    @Override
    public void onComplete() {
    }

    protected abstract void onSuccess(T t);

    protected abstract void onFail(String msg);
}