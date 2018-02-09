package com.xyz.php.presenters;

import android.text.TextUtils;

import com.xyz.php.abs.presenters.IRegisterPresenter;
import com.xyz.php.abs.views.IRegisterView;
import com.xyz.php.config.DoTransform;
import com.xyz.php.entities.UserEntity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * 18/10/2017
 */
public class RegisterPresenterTest implements IRegisterPresenter {

    private IRegisterView registerView;

    public RegisterPresenterTest(IRegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public void register(String username, String mobile, String password, String repeatPassword) {
        Flowable.timer(3, TimeUnit.SECONDS)
                .compose(DoTransform.<Long>applyScheduler(true))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        UserEntity userEntity = new UserEntity();
                        userEntity.msg = "Test Success";
                        userEntity.mobile = "15859286737";
                        registerView.onRegisterSuccess(userEntity);
                    }
                });
    }

    @Override
    public void validate(String username, String mobile, String password, String repeatPassword) {
        if (TextUtils.isEmpty(username)) {
            registerView.onValidateFailed("Username cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            registerView.onValidateFailed("Mobile cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            registerView.onValidateFailed("Password cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(repeatPassword)) {
            registerView.onValidateFailed("Password cannot be empty");
            return;
        }
        registerView.onValidateSuccess(username, mobile, password, repeatPassword);
    }
}