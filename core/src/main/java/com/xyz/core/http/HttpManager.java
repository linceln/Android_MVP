package com.xyz.core.http;

import com.xyz.core.BuildConfig;
import com.xyz.core.constant.UrlConst;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Retrofit
 */
public class HttpManager {

    private static Retrofit retrofit;
//    private static Retrofit downLoadRetrofit;

    /**
     * Singleton
     */
    public static Retrofit getRetrofit() {

        if (retrofit == null) {

            synchronized (HttpManager.class) {

                if (retrofit == null) {

                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(6, TimeUnit.SECONDS)
                            .readTimeout(6, TimeUnit.SECONDS)
                            .addInterceptor(new ParamInterceptor());

                    if (BuildConfig.DEBUG) {
                        builder.addInterceptor(new LogInterceptor());
                    }

                    retrofit = new Retrofit.Builder()
                            .baseUrl(UrlConst.baseUrl())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(builder.build())
                            .build();
                }
            }
        }
        return retrofit;
    }

//    /**
//     * 下载用，有实时进度
//     *
//     * @return
//     */
//    public static Retrofit getDownloadRetrofit() {
//        if (downLoadRetrofit == null) {
//            synchronized (HttpManager.class) {
//                if (downLoadRetrofit == null) {
//
//                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                            .connectTimeout(20, TimeUnit.SECONDS)
//                            .writeTimeout(6, TimeUnit.SECONDS)
//                            .readTimeout(6, TimeUnit.SECONDS)
//                            .addInterceptor(new ProgressInterceptor());
//
//                    downLoadRetrofit = new Retrofit.Builder()
//                            .baseUrl(UrlConst.baseUrl())
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                            .client(builder.build())
//                            .build();
//                }
//            }
//        }
//        return downLoadRetrofit;
//    }
//
//    private static class ProgressInterceptor implements Interceptor {
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Response originalResponse = chain.proceed(chain.request());
//            return originalResponse.newBuilder()
//                    .body(new FileResponseBody(originalResponse.body()))
//                    .build();
//        }
//    }
}
