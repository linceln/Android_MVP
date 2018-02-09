package com.xyz.php.abs.views;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xyz.php.entities.UserEntity;

/**
 * 18/10/2017
 */

public interface IRegisterView {

    RxAppCompatActivity getActivity();

    void onValidateSuccess(String username, String mobile, String password, String repeatPassword);

    void onValidateFailed(String msg);

    void onRegisterSuccess(UserEntity userEntity);

    void onRegisterFailed(String msg);
}
