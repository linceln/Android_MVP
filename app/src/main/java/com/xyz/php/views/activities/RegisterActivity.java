package com.xyz.php.views.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xyz.php.R;
import com.xyz.php.abs.presenters.IRegisterPresenter;
import com.xyz.php.abs.views.IRegisterView;
import com.xyz.php.config.BaseActivity;
import com.xyz.php.presenters.RegisterPresenter;
import com.xyz.php.utils.SnackbarUtils;

public class RegisterActivity extends BaseActivity implements IRegisterView, View.OnClickListener {

    private IRegisterPresenter presenter;
    private FloatingActionButton fab;
    private TextInputEditText etUsername;
    private TextInputEditText etMobile;
    private TextInputEditText etPassword;
    private TextInputEditText etRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initToolbar("SIGN UP");
        etUsername = findViewById(R.id.etUsername);
        etMobile = findViewById(R.id.etMobile);
        TextInputLayout tilPassword = findViewById(R.id.tilPassword);
        tilPassword.setPasswordVisibilityToggleEnabled(true);
        etPassword = findViewById(R.id.etPassword);
        TextInputLayout tilRepeatPassword = findViewById(R.id.tilRepeatPassword);
        tilRepeatPassword.setPasswordVisibilityToggleEnabled(true);
        etRepeatPassword = findViewById(R.id.etRepeatPassword);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        presenter = new RegisterPresenter(this);
    }

    @Override
    public String getUsername() {
        return etUsername.getText().toString().trim();
    }

    @Override
    public String getMobile() {
        return etMobile.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }

    @Override
    public String getRepeatPassword() {
        return etRepeatPassword.getText().toString().trim();
    }

    @Override
    public RxAppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void onRegisterSuccess(String msg) {
        SnackbarUtils.simple(fab, msg);
        finish();
    }

    @Override
    public void onRegisterFailed(String msg) {
        SnackbarUtils.simple(fab, msg);
    }

    @Override
    public void onInvalidate(String msg) {
        SnackbarUtils.simple(fab, msg);
    }

    @Override
    public void onClick(View view) {
        presenter.register();
    }
}