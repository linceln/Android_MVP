package com.xyz.php.abs.views;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xyz.php.entities.UserEntity;

/**
 * 18/10/2017
 */

public interface IRegisterView {

    String getUsername();

    String getMobile();

    String getPassword();

    String getRepeatPassword();

    RxAppCompatActivity getActivity();

    void onRegisterSuccess(UserEntity userEntity);

    void onRegisterFailed(String msg);

    void onInvalidate(String msg);
}
