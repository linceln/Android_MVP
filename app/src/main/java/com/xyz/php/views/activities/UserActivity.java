package com.xyz.php.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.xyz.php.R;
import com.xyz.php.abs.presenters.IUserPresenter;
import com.xyz.php.abs.views.IUserView;
import com.xyz.php.config.BaseActivity;
import com.xyz.php.constants.AppConst;
import com.xyz.php.presenters.UserPresenter;
import com.xyz.php.utils.SnackbarUtils;
import com.xyz.php.utils.StatusBarUtil;
import com.xyz.php.views.adapters.UserAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends BaseActivity implements IUserView, OnLoadmoreListener {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private UserAdapter adapter;
    private IUserPresenter presenter;

    private int page = AppConst.DEFAULT_PAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        StatusBarUtil.setStatusBarTransparentAndLight(this);
        ButterKnife.bind(this);
        presenter = new UserPresenter(this, page);
        initToolbar(presenter.getSignedInUsername());
        refreshLayout.setOnLoadmoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this, presenter.getUserList());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    public void onRequestSuccess(int pages) {
        refreshLayout.finishLoadmore();
        adapter.notifyDataSetChanged();
        if (page >= pages - 1) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            refreshLayout.setEnableLoadmore(true);
        }
    }

    @Override
    public void onRequestFailed(String msg) {
        SnackbarUtils.simple(coordinator, msg);
    }

    @Override
    public void onItemClick() {
        startActivity(new Intent(this, UserDetailActivity.class));
    }

//    @Override
//    public void onRefresh() {
//        page = AppConst.DEFAULT_PAGE;
//        presenter.clear();
//        presenter.getUser(page);
//    }
//
//    @Override
//    public void onPaging() {
//
//    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        presenter.getUser(page);
    }
}