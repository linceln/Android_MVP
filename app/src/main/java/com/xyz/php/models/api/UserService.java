package com.xyz.php.models.api;

import com.xyz.php.config.BaseEntity;
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

    @GET("v1/test")
    Flowable<BaseEntity> test();

    @FormUrlEncoded
    @POST("v1/login")
    Flowable<UserEntity> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device") int device
    );

    @FormUrlEncoded
    @POST("v1/register")
    Flowable<UserEntity> register(
            @Field("name") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );

    @GET("v1/user")
    Flowable<UserListEntity> index(
            @Query("page") int page
    );
}
