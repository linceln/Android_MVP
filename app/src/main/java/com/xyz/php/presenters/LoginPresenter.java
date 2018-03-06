package com.xyz.php.presenters;

import android.text.TextUtils;

import com.xyz.php.abs.presenters.ILoginPresenter;
import com.xyz.php.abs.views.ILoginView;
import com.xyz.php.config.DoTransform;
import com.xyz.php.config.HttpSubscriber;
import com.xyz.php.constants.AppConst;
import com.xyz.php.entities.UserEntity;
import com.xyz.php.models.UserRequest;
import com.xyz.php.models.db.User;
import com.xyz.php.utils.AES;
import com.xyz.php.utils.TokenUtils;

import org.litepal.crud.DataSupport;

/**
 * 2017/10/18.
 */
public class LoginPresenter implements ILoginPresenter {

    private ILoginView view;

    public LoginPresenter(ILoginView loginView) {
        this.view = loginView;
    }

    @Override
    public String getLastMobile() {
        User user = DataSupport.order("updated_at desc").findFirst(User.class);
        return user != null ? user.mobile : "";
    }

    @Override
    public void signIn(String mobile, String password) {
        UserRequest.login(mobile, password)
                .compose(DoTransform.<UserEntity>applyScheduler(view.getActivity(), true))
                .subscribe(new HttpSubscriber<UserEntity>(view.getActivity()) {
                    @Override
                    protected void onSuccess(UserEntity userEntity) {
                        // 保存 TOKEN 到 SharePreference， AES 对称加密
                        TokenUtils.saveToken(userEntity.token);
                        // 保存 User 到数据库
                        saveUserToDB(userEntity);
                        view.onLoginSuccess(userEntity.message);
                    }

                    @Override
                    protected void onFail(String msg) {
                        view.onLoginFailed(msg);
                    }
                });
    }

    @Override
    public void validate(String mobile, String password) {

        if (TextUtils.isEmpty(mobile)) {
            view.onValidateFailed("Mobile cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            view.onValidateFailed("Password cannot be empty");
        }
    }

    private void saveUserToDB(final UserEntity userEntity) {
        // 保存登录用户到数据库
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
}