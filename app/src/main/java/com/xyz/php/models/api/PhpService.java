package com.xyz.php.models.api;

import com.xyz.core.base.BaseEntity;
import com.xyz.php.models.entity.BMIEntity;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 2017/7/19.
 */

public interface PhpService {

    @GET("bmi/a")
    Flowable<BaseEntity> getBmi();

    @FormUrlEncoded
    @POST("bmi-api/calculate")
    Flowable<BMIEntity> calculateBMI(
            @Field("name") String name,
            @Field("sex") String sex,
            @Field("age") String age,
            @Field("height") String height,
            @Field("weight") String weight
    );
}
