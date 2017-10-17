package com.xyz.core.http;

public class DoTransform {

//    public static <T extends BaseEntity> Observable.Transformer<T, T> applyScheduler() {
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> tObservable) {
//                return tObservable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }

//    public static <T extends BaseEntity> Observable.Transformer<T, T> applyLoading(final FragmentActivity activity) {
//
//        final LoadingFragment[] loadingFragment = new LoadingFragment[1];
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> tObservable) {
//                return tObservable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnSubscribe(new Action0() {
//
//                            @Override
//                            public void call() {
//                                // start loading
//                                loadingFragment[0] = new LoadingFragment();
//                                activity.getSupportFragmentManager().beginTransaction()
//                                        .add(loadingFragment[0], "")
//                                        .commitAllowingStateLoss();
//                            }
//                        }).doOnCompleted(new Action0() {
//                            @Override
//                            public void call() {
//                                // stop loading
//                                activity.getSupportFragmentManager()
//                                        .beginTransaction()
//                                        .remove(loadingFragment[0])
//                                        .commitAllowingStateLoss();
//                                loadingFragment[0] = null;
//                            }
//                        })
//                        .doOnError(new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                // stop loading
//                                activity.getSupportFragmentManager()
//                                        .beginTransaction()
//                                        .remove(loadingFragment[0])
//                                        .commitAllowingStateLoss();
//                                loadingFragment[0] = null;
//                            }
//                        });
//            }
//        };
//    }
}