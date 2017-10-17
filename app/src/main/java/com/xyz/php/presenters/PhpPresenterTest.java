package com.xyz.php.presenters;

import com.xyz.php.abs.IPhpPresenter;
import com.xyz.php.abs.IPhpView;
import com.xyz.php.models.PhpRequest;
import com.xyz.php.models.entity.BMIEntity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 2017/10/17.
 */
public class PhpPresenterTest implements IPhpPresenter{

    private IPhpView phpView;

    public PhpPresenterTest(IPhpView phpView) {
        this.phpView = phpView;
        this.phpView.initUI();
    }

    private boolean checkNotNull(BMIEntity entity) {
        return true;
    }

    /**
     * 提交用户输入数据
     *
     * @param entity 用户输入数据
     */
    public void submitBmi(BMIEntity entity) {

        if (checkNotNull(entity)) {
            PhpRequest.calculateBMI(entity)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BMIEntity>() {
                        @Override
                        public void onSubscribe(Subscription s) {
                        }

                        @Override
                        public void onNext(BMIEntity entity) {
                            phpView.onRequestSuccess(entity);
                        }

                        @Override
                        public void onError(Throwable t) {
                            phpView.onRequestFail(t.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
