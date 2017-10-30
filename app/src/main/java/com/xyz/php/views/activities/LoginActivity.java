package com.xyz.php.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.xyz.php.utils.MD5;
import com.xyz.php.utils.SnackbarUtils;
import com.xyz.php.utils.ToastUtils;

public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener {

    private View container;
    private TextInputEditText etMobile;
    private TextInputEditText etPassword;
    private ILoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolbar("SIGN IN");
        container = findViewById(R.id.linear);
        etMobile = findViewById(R.id.etMobile);
        TextInputLayout tilPassword = findViewById(R.id.tilPassword);
        tilPassword.setPasswordVisibilityToggleEnabled(true);
        etPassword = findViewById(R.id.etPassword);
        findViewById(R.id.btnSignIn).setOnClickListener(this);
        findViewById(R.id.tvRegister).setOnClickListener(this);
        presenter = new LoginPresenter(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.REGISTER && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String mobile = data.getStringExtra(Extras.MOBILE);
                etMobile.setText(TextUtils.isEmpty(mobile) ? "" : mobile);
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
            case R.id.tvRegister:
                presenter.startRegisterActivity();
                break;
        }
    }

    @Override
    public String getMobile() {
        return etMobile.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return MD5.digest(etPassword.getText().toString().trim());
    }

    @Override
    public void validate(String msg) {
        SnackbarUtils.simple(container, msg);
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    public void onLastUserMobile(String mobile) {
        etMobile.setText(mobile);
    }

    @Override
    public void onLoginSuccess(String msg) {
        ToastUtils.simple(msg);
        startActivity(new Intent(this, UserActivity.class));
        finish();
    }

    @Override
    public void onLoginFailed(String msg) {
        SnackbarUtils.simple(container, msg);
    }
}