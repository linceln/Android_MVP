package com.xyz.php.models.api;

import com.xyz.php.entity.UserEntity;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 2017/10/18.
 */

public interface UserService {

    @FormUrlEncoded
    @POST("v1/users/login")
    Flowable<UserEntity> login(
            @Field("username") String mobile,
            @Field("password") String password,
            @Field("device") int device
    );
}
