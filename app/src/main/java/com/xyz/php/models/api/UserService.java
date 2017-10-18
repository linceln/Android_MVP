package com.xyz.php.models.api;

import com.xyz.php.entities.UserEntity;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 2017/10/18.
 */

public interface UserService {

    @FormUrlEncoded
    @POST("v1/users/login")
    Flowable<UserEntity> login(
            @Field("username") String username,
            @Field("password") String password,
            @Field("device") int device
    );

    @FormUrlEncoded
    @POST("v1/users/signup")
    Flowable<UserEntity> register(
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("password") String password,
            @Field("passwordRepeat") String passwordRepeat
    );
}
