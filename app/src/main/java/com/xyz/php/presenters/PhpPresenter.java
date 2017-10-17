package com.xyz.php.presenters;

import android.text.TextUtils;

import com.xyz.core.base.BaseEntity;
import com.xyz.core.http.HttpSubscriber;
import com.xyz.php.abs.IPhpPresenter;
import com.xyz.php.abs.IPhpView;
import com.xyz.php.models.PhpRequest;
import com.xyz.php.models.entity.BMIEntity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 2017/10/17.
 */
public class PhpPresenter implements IPhpPresenter {

    private IPhpView phpView;

    public PhpPresenter(IPhpView phpView) {
        this.phpView = phpView;
        this.phpView.initUI();
        getRemoteData();
    }

    private boolean checkNotNull(BMIEntity entity) {
        if (TextUtils.isEmpty(entity.weight)) {
            this.phpView.toastNegative("重量不能为空");
            return false;
        }
        return true;
    }

    @Override
    public void getRemoteData() {
        PhpRequest.getBmi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscriber<BaseEntity>(phpView.getActivity()) {
                    @Override
                    protected void onSuccess(BaseEntity baseEntity) {
                        phpView.onRequestSuccess(baseEntity);
                    }

                    @Override
                    protected void onFail(String msg) {
                        phpView.toastNegative(msg);
                    }
                });
    }

    @Override
    public void getDBData() {

    }

    /**
     * 提交用户输入数据
     */
    @Override
    public void submitBmi() {
        BMIEntity userInput = phpView.getUserInput();
        if (checkNotNull(userInput)) {
            PhpRequest.calculateBMI(userInput)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HttpSubscriber<BMIEntity>(phpView.getActivity()) {
                        @Override
                        protected void onSuccess(BMIEntity entity) {
                            phpView.onSubmitSuccess(entity);
                        }

                        @Override
                        protected void onFail(String msg) {
                            phpView.toastNegative(msg);
                        }
                    });
        }
    }
}
