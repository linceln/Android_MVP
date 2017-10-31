package com.xyz.php.models;

import com.xyz.php.config.BaseEntity;
import com.xyz.php.config.HttpManager;
import com.xyz.php.models.api.PhpService;
import com.xyz.php.entities.BMIEntity;

import io.reactivex.Flowable;

/**
 * 2017/7/19.
 */
public class PhpRequest {

    private static PhpService service = HttpManager.getRetrofit().create(PhpService.class);

    public static Flowable<BMIEntity> calculateBMI(BMIEntity e) {
        return service.calculateBMI(e.name, e.sex, e.age, e.height, e.weight);
    }

    public static Flowable<BaseEntity> getBmi(){
        return service.getBmi();
    }
}
