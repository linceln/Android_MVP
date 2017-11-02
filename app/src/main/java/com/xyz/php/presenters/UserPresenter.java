package com.xyz.php.presenters;

import com.xyz.php.abs.presenters.IUserPresenter;
import com.xyz.php.abs.views.IUserView;
import com.xyz.php.config.DoTransform;
import com.xyz.php.config.HttpSubscriber;
import com.xyz.php.constants.SPConst;
import com.xyz.php.entities.UserEntity;
import com.xyz.php.entities.UserListEntity;
import com.xyz.php.models.UserRequest;
import com.xyz.php.models.db.User;
import com.xyz.php.utils.SPUtils;

import org.litepal.crud.DataSupport;

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
    public String getSignedInUsername() {
        User user = DataSupport.where("token == ?", SPUtils.getString(SPConst.TOKEN)).findFirst(User.class);
        return user != null ? user.username : "";
    }

    @Override
    public void clear() {
        users.clear();
    }

    @Override
    public void getUser(final int page) {
        UserRequest.index(page)
                .compose(DoTransform.<UserListEntity>applyScheduler(true))
                .subscribe(new HttpSubscriber<UserListEntity>(userView.getActivity()) {
                    @Override
                    protected void onSuccess(UserListEntity userListEntity) {
                        users.addAll(userListEntity.users);
                        int pages = userListEntity.pages;
                        if (page >= pages - 1) {
                            userView.onFinishPagination(false);
                        } else {
                            userView.onFinishPagination(true);
                        }
                        userView.onRequestSuccess();
                    }

                    @Override
                    protected void onFail(String msg) {
                        userView.onRequestFailed(msg);
                    }
                });
    }
}