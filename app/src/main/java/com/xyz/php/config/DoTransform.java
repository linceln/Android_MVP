package com.xyz.php.config;

import android.support.v4.app.FragmentActivity;

import com.xyz.php.utils.LogUtil;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DoTransform {

    public static <T> FlowableTransformer<T, T> applyScheduler(FragmentActivity activity, boolean hasState) {
        return hasState ? DoTransform.<T>applySchedulerWithState(activity) : DoTransform.<T>applyScheduler();
    }

    private static <T> FlowableTransformer<T, T> applyScheduler() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(Schedulers.io());
            }
        };
    }

    private static <T> FlowableTransformer<T, T> applySchedulerWithState(final FragmentActivity activity) {

        final LoadingFragment[] loadingFragment = new LoadingFragment[1];

        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Consumer<T>() {
                            @Override
                            public void accept(T t) throws Exception {
                                // Start loading
                                LogUtil.e("Start loading");
                                loadingFragment[0] = new LoadingFragment();
                                activity.getSupportFragmentManager().beginTransaction()
                                        .add(loadingFragment[0], "")
                                        .commitAllowingStateLoss();
                            }
                        })
                        .doOnError(new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                // End loading, show error
                                LogUtil.e("End loading, show error");
                                activity.getSupportFragmentManager()
                                        .beginTransaction()
                                        .remove(loadingFragment[0])
                                        .commitAllowingStateLoss();
                                loadingFragment[0] = null;
                            }
                        })
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                // End loading, show content
                                LogUtil.e("End loading, show content");
                                activity.getSupportFragmentManager()
                                        .beginTransaction()
                                        .remove(loadingFragment[0])
                                        .commitAllowingStateLoss();
                                loadingFragment[0] = null;
                            }
                        });
            }
        };
    }
}