package com.xyz.php.presenters;

import android.text.TextUtils;

import com.xyz.php.abs.presenters.IRegisterPresenter;
import com.xyz.php.abs.views.IRegisterView;
import com.xyz.php.config.LoadingFragment;
import com.xyz.php.entities.UserEntity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 18/10/2017
 */
public class RegisterPresenterTest implements IRegisterPresenter {

    private IRegisterView view;

    public RegisterPresenterTest(IRegisterView registerView) {
        this.view = registerView;
    }

    @Override
    public void register(String username, String mobile, String password, String repeatPassword) {
        final LoadingFragment loadingFragment = new LoadingFragment();
        Flowable.just(1L)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        loadingFragment.show(view.getActivity().getSupportFragmentManager(), "");
                    }
                })
                .subscribeOn(Schedulers.io())
                .delay(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        loadingFragment.dismiss();
                        UserEntity userEntity = new UserEntity();
                        userEntity.msg = "Test Success";
                        userEntity.mobile = "15859286737";
                        view.onRegisterSuccess(userEntity);
                    }
                });
    }

    @Override
    public void validate(String username, String mobile, String password, String repeatPassword) {
        if (TextUtils.isEmpty(username)) {
            view.onValidateFailed("Username cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            view.onValidateFailed("Mobile cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            view.onValidateFailed("Password cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(repeatPassword)) {
            view.onValidateFailed("Password cannot be empty");
            return;
        }
        view.onValidateSuccess(username, mobile, password, repeatPassword);
    }
}