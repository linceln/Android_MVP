package com.xyz.core.http;

import com.xyz.core.BuildConfig;
import com.xyz.core.constant.UrlConst;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit
 */
public class HttpManager {

    private static Retrofit retrofit;

    /**
     * Singleton
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (HttpManager.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(UrlConst.baseUrl())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getOkHttpClient())
                            .build();
                }
            }
        }
        return retrofit;
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.SECONDS)
                .addInterceptor(new ParamInterceptor());

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new LogInterceptor());
        }

        return builder.build();
    }
}
