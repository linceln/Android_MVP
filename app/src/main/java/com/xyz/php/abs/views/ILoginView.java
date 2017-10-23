package com.xyz.php.abs.views;

import android.support.v4.app.FragmentActivity;

/**
 * 2017/10/18.
 */

public interface ILoginView {

    String getMobile();

    String getPassword();

    void validate(String msg);

    FragmentActivity getActivity();

    void onLastUserMobile(String mobile);

    void onLoginSuccess(String msg);

    void onLoginFailed(String msg);
}
