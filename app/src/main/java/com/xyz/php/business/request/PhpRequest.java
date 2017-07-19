package com.xyz.php.business.request;

import com.xyz.core.http.HttpManager;
import com.xyz.php.business.entity.BMIEntity;

import rx.Observable;

/**
 * 2017/7/19.
 */

public class PhpRequest {

    private static PhpService service = HttpManager.getRetrofit().create(PhpService.class);

    public static Observable<BMIEntity> calculateBMI(BMIEntity e) {
        return service.calculateBMI(e.name, e.sex, e.age, e.height, e.weight);
    }
}
