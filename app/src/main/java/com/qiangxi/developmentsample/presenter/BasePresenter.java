package com.qiangxi.developmentsample.presenter;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by qiang_xi on 2017/4/4 16:50.
 * 公共Presenter接口
 */

public interface BasePresenter {
    /**
     * 请求开始
     */
    void onRequestStart();

    /**
     * 请求结束
     */
    void onRequestFinish();

    /**
     * 服务器异常,错误码 [500, 600),
     */
    void serverError(Response<?> response);

    /**
     * 网络异常
     */
    void networkError(IOException e);

    /**
     * 重新登陆
     */
    void onReLogin();
}
