package com.qiangxi.developmentsample.net;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by qiang_xi on 2017/4/4 17:45.
 * okHttp日志拦截器,用于在debug调试时做断点调试
 */

public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        StringBuilder sbRequest = new StringBuilder();
        //请求
        Request request = chain.request();
        //请求头
        Headers requestHeaders = request.headers();
        //请求体
        RequestBody requestBody = request.body();
        //请求方式
        String requestMethod = request.method();
        //请求url
        HttpUrl requestUrl = request.url();

        //响应
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (Exception ignored) {
        }
        if (response != null) {
            ResponseBody responseBody = response.body();
            Headers responseHeaders = response.headers();
            int responseCode = response.code();
        }
        return response;
    }
}
