package com.xyz.php.abs.views;

import android.support.v4.app.FragmentActivity;

/**
 * 2017/10/18.
 */

public interface ILoginView {

    FragmentActivity getActivity();

    void onValidateSuccess(String mobile, String password);

    void onLoginFailed(String msg);

    void onValidateFailed(String msg);

    void onLoginSuccess(String msg);
}
