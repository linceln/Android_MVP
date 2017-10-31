package com.xyz.php.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.xyz.php.R;
import com.xyz.php.abs.presenters.ILoginPresenter;
import com.xyz.php.abs.views.ILoginView;
import com.xyz.php.config.BaseActivity;
import com.xyz.php.constants.AppConst;
import com.xyz.php.constants.Extras;
import com.xyz.php.constants.RequestCode;
import com.xyz.php.presenters.LoginPresenter;
import com.xyz.php.utils.MD5;
import com.xyz.php.utils.SnackbarUtils;
import com.xyz.php.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity implements ILoginView {

    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.etMobile)
    TextInputEditText etMobile;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.tvRegister)
    TextView tvRegister;

    private ILoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initToolbar("SIGN IN");
        presenter = new LoginPresenter(this);

        tilPassword.setPasswordVisibilityToggleEnabled(true);
        RxView.clicks(btnSignIn)
                .throttleFirst(AppConst.THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        presenter.signIn();
                    }
                });
        RxView.clicks(tvRegister)
                .throttleFirst(AppConst.THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        presenter.startRegisterActivity();
                    }
                });
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
    public String getMobile() {
        return etMobile.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return MD5.digest(etPassword.getText().toString().trim());
    }

    @Override
    public void onValidate(String msg) {
        SnackbarUtils.simple(container, msg);
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
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

    @Override
    public void onShowHistoryMobile(String mobile) {
        if (!TextUtils.isEmpty(mobile)) {
            etMobile.setText(mobile);
            etPassword.requestFocus();
        }
    }
}