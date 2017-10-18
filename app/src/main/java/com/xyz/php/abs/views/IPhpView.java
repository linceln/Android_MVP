package com.xyz.php.abs.views;

import android.support.v4.app.FragmentActivity;

import com.xyz.core.base.BaseEntity;
import com.xyz.php.entities.BMIEntity;

/**
 * 2017/10/17.
 */
public interface IPhpView {

    void initUI();

    BMIEntity getUserInput();

    void onSubmitSuccess(BMIEntity entity);

    void onRequestSuccess(BaseEntity entity);

    void toastNegative(String msg);

    FragmentActivity getActivity();
}
