package com.xyz.php.presenters;

import android.text.TextUtils;

import com.xyz.php.abs.presenters.ILoginPresenter;
import com.xyz.php.abs.views.ILoginView;
import com.xyz.php.config.LoadingFragment;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * 2017/10/18.
 */
public class LoginPresenterTest implements ILoginPresenter {

    private ILoginView view;

    public LoginPresenterTest(ILoginView loginView) {
        this.view = loginView;
    }

    @Override
    public String getLastMobile() {
        return null;
    }

    @Override
    public void signIn(String mobile, String password) {
        final LoadingFragment loadingFragment = new LoadingFragment();
        Flowable.timer(3, TimeUnit.SECONDS)
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        loadingFragment.show(view.getActivity().getSupportFragmentManager(), "");
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        loadingFragment.dismiss();
                        view.onLoginSuccess("Login success");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loadingFragment.dismiss();
                        view.onLoginFailed("Login success");
                    }
                });
    }

    @Override
    public void validate(String mobile, String password) {

        if (TextUtils.isEmpty(mobile)) {
            view.onValidateFailed("Mobile cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            view.onValidateFailed("Password cannot be empty");
            return;
        }
    }
}