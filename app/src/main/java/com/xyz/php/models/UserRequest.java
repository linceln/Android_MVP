package com.xyz.php.models;

import com.xyz.php.config.HttpManager;
import com.xyz.php.entity.UserEntity;
import com.xyz.php.models.api.UserService;

import io.reactivex.Flowable;

/**
 * 2017/10/18.
 */
public class UserRequest {

    private static UserService service = HttpManager.getRetrofit().create(UserService.class);

    public static Flowable<UserEntity> login(String account, String password) {
        return service.login(account, password, 1);
    }
}
