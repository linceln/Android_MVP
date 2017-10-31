package com.xyz.php.config;

import android.support.annotation.NonNull;

import com.xyz.php.utils.CodingUtil;
import com.xyz.php.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * 打印http请求日志
 */
public class LogInterceptor implements Interceptor {

    private static final String TAG = "HTTP";

    /**
     * 判断该String类型是否是Json数据
     */
    private static boolean isJson(String str) {
        boolean isJson;
        try {
            new JSONObject(str);
            isJson = true;
        } catch (Exception e) {
            isJson = false;
            LogUtil.e(TAG, str);
            e.printStackTrace();
        }
        return isJson;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        LogUtil.e(TAG, "---------------------------------Start-------------------------------------");
        LogUtil.e(TAG, request.method());
        LogUtil.e(TAG, request.headers().toString());
        if ("GET".equals(request.method())) {
            String str = "";
            String url = request.url().toString();
            String[] split = url.split("\\?");
            if (split.length >= 2) {
                String[] keyValues = split[1].split("&");
                for (String keyValue : keyValues) {
                    str += keyValue + "\n";
                }
            }
            LogUtil.e(TAG, request.url().toString());
            LogUtil.e(TAG, CodingUtil.urlDecode(str));
        } else if ("POST".equals(request.method())) {

            if ("multipart".equals(request.body().contentType().type())) {
                // multipart/form-data
                String paramsStr = getRequestParams(request);
                LogUtil.e(TAG, request.url().toString() + "\n" + paramsStr);
            } else {
                // application/x-www-form-urlencoded
                String paramsStr = getRequestParams(request);
                LogUtil.e(TAG, request.url().toString() + "\n" + CodingUtil.urlDecode(paramsStr));
            }
        }

        okhttp3.Response response = chain.proceed(request);
        LogUtil.e(TAG, response.headers().toString());
        String content = response.body().string();

        if (content != null && isJson(content)) {
            try {
                LogUtil.e(TAG, new JSONObject(content).toString(4));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            LogUtil.e(TAG, content);
        }

        LogUtil.e(TAG, "-----------------------------------End-------------------------------------");

        MediaType mediaType = response.body().contentType();
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();


    }

    @NonNull
    private String getRequestParams(Request request) {
        RequestBody requestBody = request.body();
        Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = requestBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"));
        }
        String paramsStr = buffer.readString(charset);
        String[] split = paramsStr.split("&");
        String str = "";
        for (String s : split) {
            str += s + "\n";
        }
        return str;
    }
}
