package com.xyz.php.business.request;

import com.xyz.php.business.entity.BMIEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 2017/7/19.
 */

interface PhpService {

    @FormUrlEncoded
    @POST("bmi-api/calculate")
    Observable<BMIEntity> calculateBMI(
            @Field("name") String name,
            @Field("sex") String sex,
            @Field("age") String age,
            @Field("height") String height,
            @Field("weight") String weight
    );
}
