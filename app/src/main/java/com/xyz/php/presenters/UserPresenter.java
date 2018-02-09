package com.xyz.php.presenters;

import android.support.v7.widget.RecyclerView;

import com.xyz.php.abs.presenters.IUserPresenter;
import com.xyz.php.abs.views.IUserView;
import com.xyz.php.config.DoTransform;
import com.xyz.php.config.HttpSubscriber;
import com.xyz.php.constants.AppConst;
import com.xyz.php.constants.SPConst;
import com.xyz.php.entities.UserEntity;
import com.xyz.php.entities.UserListEntity;
import com.xyz.php.models.UserRequest;
import com.xyz.php.models.db.User;
import com.xyz.php.utils.SPUtils;
import com.xyz.php.views.adapters.UserAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 2017/10/23.
 */
public class UserPresenter implements IUserPresenter {

    private List<UserEntity> users = new ArrayList<>();

    private IUserView view;

    public UserPresenter(IUserView userView) {
        this.view = userView;
    }

    @Override
    public String getTitle() {
        User user = DataSupport.where("token == ?", SPUtils.getString(SPConst.TOKEN)).findFirst(User.class);
        return user != null ? user.username : "";
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new UserAdapter(view.getActivity(), users);
    }

    @Override
    public void request(final int page, final boolean refresh, final boolean load) {
        UserRequest.index(page)
                .compose(DoTransform.<UserListEntity>applyScheduler(view.getActivity(), !(refresh || load)))
                .subscribe(new HttpSubscriber<UserListEntity>(view.getActivity()) {
                    @Override
                    protected void onSuccess(UserListEntity userListEntity) {

                        // TODO HttpListSubscriber
                        if(refresh){
                            users.clear();
                        }
                        users.addAll(userListEntity.users);

                        int pages = userListEntity.pages;

                        if (refresh) {
                            view.onPageChange(AppConst.DEFAULT_PAGE);
                        } else if (load) {
                            view.onPageChange(page + 1);
                        } else {
                            view.onPageChange(page + 1);
                        }

                        view.onWhetherFinishPagination(page < pages - 1);

                        if (users.isEmpty()) view.onEmptyData();

                        view.onRequestSuccess();
                    }

                    @Override
                    protected void onFail(String msg) {
                        view.onRequestFailed(msg);
                    }

                    @Override
                    protected void onNetworkError(String msg) {
                        view.onNetworkError();
                    }
                });
    }
}