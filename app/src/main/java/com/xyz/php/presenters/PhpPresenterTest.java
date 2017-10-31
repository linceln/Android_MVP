package com.xyz.php.presenters;

import com.xyz.php.abs.presenters.IPhpPresenter;
import com.xyz.php.abs.views.IPhpView;
import com.xyz.php.models.db.User;

/**
 * 2017/10/17.
 */
public class PhpPresenterTest implements IPhpPresenter {

    private IPhpView phpView;

    public PhpPresenterTest(IPhpView phpView) {
        this.phpView = phpView;
        this.phpView.initUI();
        getDBMobile();
    }

    @Override
    public void getRemoteData() {

    }

    @Override
    public String getDBMobile() {
        return null;
    }

    @Override
    public void submitBmi() {
        final User user = new User();
        user.mobile = "15859286737";
        user.username = "Lincoln";
    }
}