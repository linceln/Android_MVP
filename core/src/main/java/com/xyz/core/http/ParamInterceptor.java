package com.xyz.core.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

class ParamInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(generateSignRequest(chain.request()));
    }

    private Request generateSignRequest(Request request) {

        Request newRequest;
        switch (request.method()) {
            case "GET":
                newRequest = request.newBuilder().url(newUrl(request)).build();
                break;
            case "POST":
                newRequest = request.newBuilder().url(newUrl(request)).post(newRequestBody(request)).build();
                break;
            default:
                newRequest = request.newBuilder().build();
                break;
        }
        return newRequest;
    }


    /**
     * GET URL
     * 如果某个参数key对应的value为空，则忽略此参数
     *
     * @param request
     * @return
     */
    private String newUrl(Request request) {

        // "?"分隔出路径和参数
        String[] split = request.url().toString().split("\\?");
        if (split.length > 1 && !TextUtils.isEmpty(split[1])) {
            HashMap<String, String> paramPairs = splitParamsFilterNull(split[1]);

            String paramStr = "";
            for (Map.Entry<String, String> entry : paramPairs.entrySet()) {
                paramStr += entry.getKey() + "=" + entry.getValue() + "&";
            }
            String s = split[0] + "?" + paramStr;
            return s.substring(0, s.length() - 1);
        }
        return request.url().toString();
    }

    /**
     * POST BODY
     * 如果某个参数key对应的value为空，则忽略此参数
     *
     * @param request
     * @return
     */
    @NonNull
    private RequestBody newRequestBody(Request request) {
        RequestBody requestBody = request.body();

        // 上传文件略过
        if ("multipart".equals(requestBody.contentType().type())) {
            return requestBody;
        }

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

        HashMap<String, String> map = splitParamsFilterNull(paramsStr);

        String actualParamStr = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            actualParamStr += entry.getKey() + "=" + entry.getValue() + "&";
        }
        if (!TextUtils.isEmpty(actualParamStr)) {
            return RequestBody.create(contentType, actualParamStr.substring(0, actualParamStr.length() - 1));
        } else {
            return RequestBody.create(contentType, actualParamStr);
        }
    }

    private HashMap<String, String> splitParamsFilterNull(String paramsString) {
        HashMap<String, String> hashMap = new HashMap<>();
        // 用"&"分隔出键值对"key=value&key=value&key=value"
        String[] paramPairs = paramsString.split("&");
        for (String paramPair : paramPairs) {
            // 用"="分隔每个"key=value"
            String[] keyValue = paramPair.split("=");
            if (keyValue.length > 1) {
                hashMap.put(keyValue[0], keyValue[1]);
            }
        }
        return hashMap;
    }
}