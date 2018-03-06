package com.xyz.php.presenters;

import android.text.TextUtils;

import com.xyz.php.abs.presenters.IRegisterPresenter;
import com.xyz.php.abs.views.IRegisterView;
import com.xyz.php.config.DoTransform;
import com.xyz.php.config.HttpSubscriber;
import com.xyz.php.entities.UserEntity;
import com.xyz.php.models.UserRequest;

/**
 * 18/10/2017
 */
public class RegisterPresenter implements IRegisterPresenter {

    private IRegisterView registerView;

    public RegisterPresenter(IRegisterView registerView) {

        this.registerView = registerView;
    }

    @Override
    public void register(String username, String mobile, String password, String repeatPassword) {
        UserRequest.register(username, mobile, password, repeatPassword)
                .compose(DoTransform.<UserEntity>applyScheduler(registerView.getActivity(), true))
                .compose(registerView.getActivity().<UserEntity>bindToLifecycle())
                .subscribe(new HttpSubscriber<UserEntity>(registerView.getActivity()) {

                    @Override
                    protected void onSuccess(UserEntity userEntity) {
                        registerView.onRegisterSuccess(userEntity);
                    }

                    @Override
                    protected void onFail(String msg) {
                        registerView.onRegisterFailed(msg);
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
        }
    }
}
