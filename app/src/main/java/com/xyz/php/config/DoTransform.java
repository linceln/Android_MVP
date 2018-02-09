package com.xyz.php.config;

import android.support.v4.app.FragmentActivity;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

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

        final LoadingFragment[] loadingFragments = new LoadingFragment[1];

        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(Subscription subscription) throws Exception {
                                // Start loading
                                loadingFragments[0] = new LoadingFragment();
                                activity.getSupportFragmentManager().beginTransaction()
                                        .add(loadingFragments[0], "")
                                        .commitAllowingStateLoss();
                            }
                        })
                        .doOnError(new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                // End loading, show error
                                activity.getSupportFragmentManager()
                                        .beginTransaction()
                                        .remove(loadingFragments[0])
                                        .commitAllowingStateLoss();
                                loadingFragments[0] = null;
                            }
                        })
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                // End loading, show content
                                activity.getSupportFragmentManager()
                                        .beginTransaction()
                                        .remove(loadingFragments[0])
                                        .commitAllowingStateLoss();
                                loadingFragments[0] = null;
                            }
                        });
            }
        };
    }
}