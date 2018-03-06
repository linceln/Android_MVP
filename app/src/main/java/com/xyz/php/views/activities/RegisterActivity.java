package com.xyz.php.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xyz.php.R;
import com.xyz.php.abs.presenters.IRegisterPresenter;
import com.xyz.php.abs.views.IRegisterView;
import com.xyz.php.config.BaseActivity;
import com.xyz.php.constants.AppConst;
import com.xyz.php.constants.Extras;
import com.xyz.php.entities.UserEntity;
import com.xyz.php.presenters.RegisterPresenter;
import com.xyz.php.utils.MD5;
import com.xyz.php.utils.SnackbarUtils;
import com.xyz.php.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class RegisterActivity extends BaseActivity implements IRegisterView {

    private IRegisterPresenter presenter;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.etUsername)
    TextInputEditText etUsername;

    @BindView(R.id.etMobile)
    TextInputEditText etMobile;

    @BindView(R.id.etPassword)
    TextInputEditText etPassword;

    @BindView(R.id.etRepeatPassword)
    TextInputEditText etRepeatPassword;

    @BindView(R.id.tilRepeatPassword)
    TextInputLayout tilRepeatPassword;

    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initTitle("SIGN UP");
        ButterKnife.bind(this);

        presenter = new RegisterPresenter(this);
//        presenter = new RegisterPresenterTest(this);

        tilPassword.setPasswordVisibilityToggleEnabled(true);
        tilRepeatPassword.setPasswordVisibilityToggleEnabled(true);

        click2Register();
    }

    private void click2Register() {
        RxView.clicks(fab)
                .throttleFirst(AppConst.THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        String username = etUsername.getText().toString().trim();
                        String mobile = etMobile.getText().toString().trim();
                        String password = MD5.digest(etPassword.getText().toString().trim());
                        String repeatPassword = MD5.digest(etRepeatPassword.getText().toString().trim());

                        // Validate
                        presenter.validate(username, mobile, password, repeatPassword);
                        // Submit to register
                        presenter.register(username, mobile, password, repeatPassword);
                    }
                });
    }

    @Override
    public RxAppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void onValidateFailed(String msg) {
        SnackbarUtils.simple(fab, msg);
    }

    @Override
    public void onRegisterSuccess(UserEntity userEntity) {
        ToastUtils.simple(userEntity.message);
        Intent intent = new Intent();
        intent.putExtra(Extras.MOBILE, userEntity.mobile);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onRegisterFailed(String msg) {
        SnackbarUtils.simple(fab, msg);
    }
}