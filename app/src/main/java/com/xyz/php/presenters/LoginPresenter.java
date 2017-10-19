package com.xyz.php.presenters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.xyz.core.http.HttpSubscriber;
import com.xyz.php.abs.presenters.ILoginPresenter;
import com.xyz.php.abs.views.ILoginView;
import com.xyz.php.constants.RequestCode;
import com.xyz.php.entities.UserEntity;
import com.xyz.php.models.UserRequest;
import com.xyz.php.models.local.User;
import com.xyz.php.views.activities.RegisterActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

/**
 * 2017/10/18.
 */
public class LoginPresenter implements ILoginPresenter {

    private ILoginView loginView;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void signIn() {
        final String account = loginView.getAccount();
        String password = loginView.getPassword();
        if (checkNotNull(account, password)) {
            UserRequest.login(account, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HttpSubscriber<UserEntity>(loginView.getActivity()) {
                        @Override
                        protected void onSuccess(UserEntity userEntity) {
                            save(userEntity);
                            loginView.onLoginSuccess(userEntity.msg);
                        }

                        @Override
                        protected void onFail(String msg) {
                            loginView.onLoginFailed(msg);
                        }
                    });
        }
    }

    @Override
    public void startRegisterActivity() {
        FragmentActivity activity = loginView.getActivity();
        activity.startActivityForResult(new Intent(activity, RegisterActivity.class), RequestCode.REGISTER);
    }

    private void save(final UserEntity userEntity) {
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).equalTo("mobile", userEntity.username).findFirst();
        if (user == null) {
            user = new User();
        }

        final User finalUser = user;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                finalUser.mobile = userEntity.mobile != null ? userEntity.mobile : "";
                finalUser.token = userEntity.token;
                finalUser.nickname = userEntity.username;
                realm.copyToRealmOrUpdate(finalUser);
            }
        });
    }

    private boolean checkNotNull(String account, String password) {
        if (TextUtils.isEmpty(account)) {
            loginView.validate("Account cannot be empty");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            loginView.validate("Password cannot be empty");
            return false;
        }
        return true;
    }
}