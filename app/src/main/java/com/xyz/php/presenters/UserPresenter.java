package com.xyz.php.presenters;

import com.xyz.core.http.HttpSubscriber;
import com.xyz.php.abs.presenters.IUserPresenter;
import com.xyz.php.abs.views.IUserView;
import com.xyz.php.entities.UserEntity;
import com.xyz.php.entities.UserListEntity;
import com.xyz.php.models.UserRequest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 2017/10/23.
 */
public class UserPresenter implements IUserPresenter {

    private List<UserEntity> users = new ArrayList<>();
    private IUserView userView;

    public UserPresenter(IUserView userView) {

        this.userView = userView;
        getRemoteData();
    }

    @Override
    public List<UserEntity> getUsers() {
        return users;
    }

    private void getRemoteData() {
        UserRequest.index()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscriber<UserListEntity>(userView.getActivity()) {
                    @Override
                    protected void onSuccess(UserListEntity userListEntity) {
                        users.addAll(userListEntity.users);
                        userView.notifyDataSetChanged();
                        userView.onRequestSuccess();
                    }

                    @Override
                    protected void onFail(String msg) {
                        userView.onRequestFailed(msg);
                    }
                });
    }
}