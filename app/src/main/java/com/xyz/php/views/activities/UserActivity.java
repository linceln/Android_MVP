package com.xyz.php.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xyz.php.R;
import com.xyz.php.abs.presenters.IUserPresenter;
import com.xyz.php.abs.views.IUserView;
import com.xyz.php.config.BaseActivity;
import com.xyz.php.constants.AppConst;
import com.xyz.php.presenters.UserPresenter;
import com.xyz.php.utils.SnackbarUtils;
import com.xyz.php.views.adapters.UserAdapter;

public class UserActivity extends BaseActivity implements IUserView {

    private CoordinatorLayout coordinator;
    private UserAdapter adapter;
    private IUserPresenter presenter;

    private int page = AppConst.DEFAUL_PAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        presenter = new UserPresenter(this, page);
        initToolbar("USERS");
        coordinator = findViewById(R.id.coordinator);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this, presenter.getUserList());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    public void onRequestSuccess() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestFailed(String msg) {
        SnackbarUtils.simple(coordinator, msg);
    }

    @Override
    public void onItemClick() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onRefresh() {
        page = AppConst.DEFAUL_PAGE;
        presenter.clear();
        presenter.getUser(page);
    }

    @Override
    public void onPaging() {
        page++;
        presenter.getUser(page);
    }
}
