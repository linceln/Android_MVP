package com.xyz.php.abs.views;

import android.support.v4.app.FragmentActivity;

/**
 * 2017/10/18.
 */

public interface ILoginView {

    String getAccount();

    String getPassword();

    void validate(String msg);

    FragmentActivity getActivity();

    void onLoginSuccess(String msg);

    void onLoginFailed(String msg);
}
