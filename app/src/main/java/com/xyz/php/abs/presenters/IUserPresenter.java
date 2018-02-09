package com.xyz.php.abs.presenters;

import android.support.v7.widget.RecyclerView;

/**
 * 2017/10/23.
 */
public interface IUserPresenter {

    String getTitle();

    RecyclerView.Adapter getAdapter();

    void request(int page, boolean refresh, boolean load);
}
