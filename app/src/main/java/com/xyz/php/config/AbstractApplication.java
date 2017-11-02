package com.xyz.php.config;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.Stetho;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.xyz.php.R;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * 17/10/2017
 */
public abstract class AbstractApplication extends Application {

    //static 代码段可以防止内存泄露
    static {
        //设置全局的下拉刷新Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.white);//全局设置主题颜色
                return new DeliveryHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
//                return new ClassicsFooter(context).setDrawableSize(20);
                return new BallPulseFooter(context)
                        .setIndicatorColor(0xfff5a343)
                        .setNormalColor(0xfff5a343)
                        .setAnimatingColor(0xfff5a343);
            }
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        Stetho.initializeWithDefaults(this);
    }
}
