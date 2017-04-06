package com.qiangxi.developmentsample.net;

import android.content.Context;

import com.qiangxi.developmentsample.helper.SpHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者 任强强 on 2017/4/6 13:47.
 * 为每次请求添加cookie(通过拦截器添加cookie,不用再在每个请求上都添加cookie了,但是这个需要传入上下文,而Context又不太好传入,待定吧...)
 */

public class CookieRequestIntercept implements Interceptor {
    private String mCookie;

    public CookieRequestIntercept(Context context) {
        mCookie = SpHelper.getCookie(context.getApplicationContext());
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request newRequest = originalRequest.newBuilder().
                header("cookie", mCookie).build();
        return chain.proceed(newRequest);
    }
}
