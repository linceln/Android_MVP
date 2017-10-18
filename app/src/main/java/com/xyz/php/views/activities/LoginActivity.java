package com.xyz.php.views.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.xyz.php.R;
import com.xyz.php.abs.presenters.ILoginPresenter;
import com.xyz.php.abs.views.ILoginView;
import com.xyz.php.config.BaseActivity;
import com.xyz.php.presenters.LoginPresenter;

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
        etPassword = findViewById(R.id.etPassword);
        findViewById(R.id.btnSignIn).setOnClickListener(this);

        presenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View view) {
        presenter.signIn();
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    public void onLoginSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}