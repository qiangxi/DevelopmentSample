package com.qiangxi.developmentsample.net;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by qiang_xi on 2017/4/4 20:30.
 * Retrofit请求回调
 */

public interface RetrofitCallback<T> {
    /**
     * 请求开始
     */
    void start();

    /**
     * 请求成功,错误码 [200, 300)
     */
    void success(Response<T> response);

    /**
     * 未认证异常, 错误码 401,
     */
    void unauthenticated(Response<?> response);

    /**
     * 客户端异常, 错误码 [400, 500) ,除了 401.
     */
    void clientError(Response<?> response);

    /**
     * 服务器异常,错误码 [500, 600),
     */
    void serverError(Response<?> response);

    /**
     * 网络异常
     */
    void networkError(IOException e);

    /**
     * 其他异常
     */
    void unexpectedError(Throwable t);

    void finish();
}
