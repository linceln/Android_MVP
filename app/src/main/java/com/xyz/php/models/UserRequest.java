package com.xyz.php.models;

import com.xyz.php.config.HttpManager;
import com.xyz.php.constants.AppConst;
import com.xyz.php.entities.UserEntity;
import com.xyz.php.entities.UserListEntity;
import com.xyz.php.models.api.UserService;

import io.reactivex.Flowable;

/**
 * 2017/10/18.
 */
public class UserRequest {

    private static UserService service = HttpManager.getRetrofit().create(UserService.class);

    public static Flowable<UserEntity> login(String account, String password) {
        return service.login(account, password, AppConst.DEVICE_ID);
    }

    public static Flowable<UserEntity> register(String username, String mobile, String password, String passwordRepeat) {
        return service.register(username, mobile, password, passwordRepeat);
    }

    public static Flowable<UserListEntity> index(int page) {
        return service.index(page);
    }
}
