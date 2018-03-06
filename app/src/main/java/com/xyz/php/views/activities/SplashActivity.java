package com.xyz.php.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.xyz.php.R;
import com.xyz.php.config.BaseEntity;
import com.xyz.php.config.DoTransform;
import com.xyz.php.config.HttpSubscriber;
import com.xyz.php.models.UserRequest;
import com.xyz.php.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
//        Flowable.timer(2, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
                        UserRequest.test()
                                .compose(DoTransform.<BaseEntity>applyScheduler(SplashActivity.this, true))
                                .subscribe(new HttpSubscriber<BaseEntity>(SplashActivity.this) {
                                    @Override
                                    protected void onSuccess(BaseEntity baseEntity) {
                                        ToastUtils.simple(baseEntity.message);
                                    }

                                    @Override
                                    protected void onFail(String msg) {
                                        ToastUtils.simple(msg);
                                    }
                                });
//                        if (TextUtils.isEmpty(TokenUtils.getToken())) {
//                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                        } else {
//                            startActivity(new Intent(SplashActivity.this, UserActivity.class));
//                        }
//                        finish();
//                    }
//                });
    }
}
