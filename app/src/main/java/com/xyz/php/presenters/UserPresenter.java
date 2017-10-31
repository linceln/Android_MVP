package com.xyz.php.presenters;

import com.xyz.core.http.DoTransform;
import com.xyz.core.http.HttpSubscriber;
import com.xyz.php.abs.presenters.IUserPresenter;
import com.xyz.php.abs.views.IUserView;
import com.xyz.php.entities.UserEntity;
import com.xyz.php.entities.UserListEntity;
import com.xyz.php.models.UserRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 2017/10/23.
 */
public class UserPresenter implements IUserPresenter {

    private List<UserEntity> users = new ArrayList<>();
    private IUserView userView;

    public UserPresenter(IUserView userView, int page) {
        this.userView = userView;
        getUser(page);
    }

    @Override
    public List<UserEntity> getUserList() {
        return users;
    }

    @Override
    public void clear() {
        users.clear();
    }

    @Override
    public void getUser(int page) {
        UserRequest.index(page)
                .compose(DoTransform.<UserListEntity>applyScheduler(true))
                .subscribe(new HttpSubscriber<UserListEntity>(userView.getActivity()) {
                    @Override
                    protected void onSuccess(UserListEntity userListEntity) {
                        users.addAll(userListEntity.user);
                        userView.onRequestSuccess();
                    }

                    @Override
                    protected void onFail(String msg) {
                        userView.onRequestFailed(msg);
                    }
                });
    }
}