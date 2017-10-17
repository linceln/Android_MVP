package com.xyz.php.abs;

import com.xyz.core.base.IView;
import com.xyz.php.models.entity.BMIEntity;

/**
 * 2017/10/17.
 */

public interface IPhpView extends IView<BMIEntity> {

    BMIEntity getUserInput();
}
