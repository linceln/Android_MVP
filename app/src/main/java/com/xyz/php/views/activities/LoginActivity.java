package com.xyz.php.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.xyz.php.R;
import com.xyz.php.abs.presenters.ILoginPresenter;
import com.xyz.php.abs.views.ILoginView;
import com.xyz.php.config.BaseActivity;
import com.xyz.php.constants.Extras;
import com.xyz.php.constants.RequestCode;
import com.xyz.php.presenters.LoginPresenter;
import com.xyz.php.utils.SnackbarUtils;

public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener {

    private TextInputEditText etAccount;
    private TextInputEditText etPassword;
    private ILoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolbar("SIGN IN");
        etAccount = findViewById(R.id.etAccount);
        TextInputLayout tilPassword = findViewById(R.id.tilPassword);
        tilPassword.setPasswordVisibilityToggleEnabled(true);
        etPassword = findViewById(R.id.etPassword);
        findViewById(R.id.btnSignIn).setOnClickListener(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        presenter = new LoginPresenter(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.REGISTER && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String mobile = data.getStringExtra(Extras.MOBILE);
                etAccount.setText(TextUtils.isEmpty(mobile) ? "" : mobile);
                etPassword.requestFocus();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                presenter.signIn();
                break;
            case R.id.fab:
                presenter.startRegisterActivity();
                break;
        }
    }

    @Override
    public String getAccount() {
        return etAccount.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }

    @Override
    public void validate(String msg) {
        SnackbarUtils.simple(etAccount, msg);
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    public void onLoginSuccess(String msg) {
        SnackbarUtils.simple(etAccount, msg);
    }

    @Override
    public void onLoginFailed(String msg) {
        SnackbarUtils.simple(etAccount, msg);
    }
}