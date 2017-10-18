package com.xyz.php.presenters;

import android.text.TextUtils;

import com.xyz.core.http.HttpSubscriber;
import com.xyz.php.abs.presenters.IRegisterPresenter;
import com.xyz.php.abs.views.IRegisterView;
import com.xyz.php.entities.UserEntity;
import com.xyz.php.models.UserRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 18/10/2017
 */
public class RegisterPresenter implements IRegisterPresenter {

    private IRegisterView registerView;

    public RegisterPresenter(IRegisterView registerView) {

        this.registerView = registerView;
    }

    @Override
    public void register() {
        String username = registerView.getUsername();
        String mobile = registerView.getMobile();
        String password = registerView.getPassword();
        String repeatPassword = registerView.getRepeatPassword();
        if (validate(username, mobile, password, repeatPassword)) {
            UserRequest.register(username, mobile, password, repeatPassword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(registerView.getActivity().<UserEntity>bindToLifecycle())
                    .subscribe(new HttpSubscriber<UserEntity>(registerView.getActivity()) {
                        @Override
                        protected void onSuccess(UserEntity userEntity) {
                            registerView.onRegisterSuccess(userEntity.msg);
                        }

                        @Override
                        protected void onFail(String msg) {
                            registerView.onRegisterFailed(msg);
                        }
                    });
        }
    }

    private boolean validate(String username, String mobile, String password, String repeatPassword) {
        if (TextUtils.isEmpty(username)) {
            registerView.onInvalidate("Username cannot be empty");
            return false;
        }
        if (TextUtils.isEmpty(mobile)) {
            registerView.onInvalidate("Mobile cannot be empty");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            registerView.onInvalidate("Password cannot be empty");
            return false;
        }
        if (TextUtils.isEmpty(repeatPassword)) {
            registerView.onInvalidate("Password cannot be empty");
            return false;
        }
        return true;
    }
}
