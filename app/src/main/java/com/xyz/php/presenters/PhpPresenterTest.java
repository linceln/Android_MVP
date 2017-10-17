package com.xyz.php.presenters;

import android.support.annotation.NonNull;

import com.xyz.php.abs.presenters.IPhpPresenter;
import com.xyz.php.abs.views.IPhpView;
import com.xyz.php.entity.BMIEntity;
import com.xyz.php.models.local.User;

import io.realm.Realm;

/**
 * 2017/10/17.
 */
public class PhpPresenterTest implements IPhpPresenter {

    private IPhpView phpView;

    public PhpPresenterTest(IPhpView phpView) {
        this.phpView = phpView;
        this.phpView.initUI();
        getDBData();
    }

    @Override
    public void getRemoteData() {

    }

    @Override
    public void getDBData() {
    }

    @Override
    public void submitBmi() {
        final User user = new User();
        user.mobile = "15859286737";
        user.nickname = "Lincoln";
        user.token = "23idsfjkafldashflsafdjlfasdjfjlasldfsalj";

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.copyToRealmOrUpdate(user);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                BMIEntity entity = new BMIEntity();
                entity.message = "插入数据库成功";
                phpView.onSubmitSuccess(entity);
            }
        });
    }
}
