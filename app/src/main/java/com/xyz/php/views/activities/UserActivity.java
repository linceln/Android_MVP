package com.xyz.php.views.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends BaseActivity implements IUserView, OnLoadmoreListener {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.iv)
    ImageView iv;

    private RecyclerView.Adapter adapter;

    private IUserPresenter presenter;

    private int page = AppConst.DEFAULT_PAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        StatusBarUtil.setStatusBarTransparentAndLight(this);
        ButterKnife.bind(this);

        presenter = new UserPresenter(this);

        presenter.request(page, false, false);

        initToolbar(presenter.getTitle());

        Glide.with(this).load(R.mipmap.ic_gif_tank).into(iv);

        smartRefreshLayout.setOnLoadmoreListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = presenter.getAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    public void onPageChange(int page) {
        this.page = page;
    }

    @Override
    public void onRequestSuccess() {
        smartRefreshLayout.finishLoadmore();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestFailed(String msg) {
        smartRefreshLayout.finishLoadmore();
        SnackbarUtils.simple(coordinator, msg);
    }

    @Override
    public void onWhetherFinishPagination(boolean isPaginateEnabled) {
        smartRefreshLayout.setEnableLoadmore(isPaginateEnabled);
    }

    @Override
    public void onEmptyData() {
    }

    @Override
    public void onNetworkError() {
        smartRefreshLayout.finishLoadmore();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        presenter.request(page, false, true);
    }
}