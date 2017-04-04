package com.qiangxi.developmentsample.presenter;

/**
 * Created by qiang_xi on 2017/4/4 16:50.
 */

public interface BasePresenter {
    void onRequestStart();

    void onRequestFinish();

    void onRequestError();

    void onReLogin();
}
