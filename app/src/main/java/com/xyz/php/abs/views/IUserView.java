package com.xyz.php.abs.views;

import android.support.v4.app.FragmentActivity;

/**
 * 2017/10/23.
 */

public interface IUserView {

    FragmentActivity getActivity();

    void notifyDataSetChanged();

    void onRequestSuccess();

    void onRequestFailed(String msg);

    void onItemClick();
}
