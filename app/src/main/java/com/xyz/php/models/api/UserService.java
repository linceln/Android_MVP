package com.xyz.php.models.api;

import com.xyz.php.entities.UserEntity;
import com.xyz.php.entities.UserListEntity;

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
    @POST("v1/user/login")
    Flowable<UserEntity> login(
            @Field("mobile") String username,
            @Field("password") String password,
            @Field("device") int device
    );

    @FormUrlEncoded
    @POST("v1/user/signup")
    Flowable<UserEntity> register(
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("password") String password,
            @Field("passwordRepeat") String passwordRepeat
    );

    @GET("v1/user")
    Flowable<UserListEntity> index(
            @Query("page") int page
    );
}
