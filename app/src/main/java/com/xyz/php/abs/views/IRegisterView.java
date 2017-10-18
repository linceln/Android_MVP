package com.xyz.php.abs.views;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * 18/10/2017
 */

public interface IRegisterView {

    String getUsername();

    String getMobile();

    String getPassword();

    String getRepeatPassword();

    RxAppCompatActivity getActivity();

    void onRegisterSuccess(String msg);

    void onRegisterFailed(String msg);

    void onInvalidate(String msg);
}
