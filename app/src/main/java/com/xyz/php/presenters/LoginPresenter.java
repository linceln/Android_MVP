package com.xyz.php.presenters;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.xyz.php.abs.presenters.ILoginPresenter;
import com.xyz.php.abs.views.ILoginView;
import com.xyz.php.config.HttpSubscriber;
import com.xyz.php.constants.AppConst;
import com.xyz.php.constants.RequestCode;
import com.xyz.php.constants.SPConst;
import com.xyz.php.entities.UserEntity;
import com.xyz.php.models.UserRequest;
import com.xyz.php.models.db.User;
import com.xyz.php.utils.AES;
import com.xyz.php.utils.SPUtils;
import com.xyz.php.views.activities.RegisterActivity;

import org.litepal.crud.DataSupport;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 2017/10/18.
 */
public class LoginPresenter implements ILoginPresenter {

    private ILoginView loginView;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        getLastUserMobile();
    }

    private void getLastUserMobile() {
        User user = DataSupport.order("updated_at desc").findFirst(User.class);
        loginView.onShowHistoryMobile(user != null ? user.mobile : null);
    }

    @Override
    public void signIn() {
        final String mobile = loginView.getMobile();
        String password = loginView.getPassword();
        if (checkNotNull(mobile, password)) {
            UserRequest.login(mobile, password)
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
        SPUtils.putString(SPConst.TOKEN, AES.encrypt(userEntity.token, AppConst.SALT));

        User user = DataSupport.where("mobile == ?", userEntity.mobile).findFirst(User.class);
        if (user == null) {
            user = new User();
        }
        user.mobile = userEntity.mobile;
        user.username = userEntity.username;
        user.token = AES.encrypt(userEntity.token, AppConst.SALT);
        user.updated_at = System.currentTimeMillis();
        user.avatars = userEntity.avatars;
        user.save();
    }

    private boolean checkNotNull(String mobile, String password) {
        if (TextUtils.isEmpty(mobile)) {
            loginView.onValidate("Mobile cannot be empty");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            loginView.onValidate("Password cannot be empty");
            return false;
        }
        return true;
    }
}