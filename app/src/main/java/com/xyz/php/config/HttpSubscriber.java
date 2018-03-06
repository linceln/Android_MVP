package com.xyz.php.config;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.xyz.php.utils.LogUtil;
import com.xyz.php.views.activities.LoginActivity;

import retrofit2.HttpException;

/**
 * 2017/10/17.
 */
public abstract class HttpSubscriber<T extends BaseEntity> extends HttpAbstractSubscriber<T> {

    private FragmentActivity activity;

    public HttpSubscriber(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (e instanceof HttpException) {

            HttpException httpException = (HttpException) e;
            int code = httpException.response().code();

            LogUtil.e("HTTP CODE: " + code);

            if (code == 401) {
                // 未认证
                activity.startActivity(new Intent(activity, LoginActivity.class));
            } else if (code == 422) {
                // 参数校验不通过
                onFail(httpException.response().message());
            } else if (code == 403) {
                // 没有权限执行此操作
                onFail("您没有权限执行此操作");
            } else {
                onFail("网络请求错误，请稍后重试");
            }

        }
    }
}
