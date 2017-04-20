package com.qiangxi.developmentsample.net;

import com.qiangxi.developmentsample.presenter.BasePresenter;

import java.io.IOException;

import retrofit2.Response;

/**
 * 作者 任强强 on 2017/4/20 14:51.
 */

class SimpleRetrofitCallback<T> implements RetrofitCallback<T> {
    private BasePresenter mPresenter;

    SimpleRetrofitCallback(BasePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void start() {
        mPresenter.onRequestStart();
    }

    @Override
    public void success(Response<T> response) {
    }

    @Override
    public void unauthenticated(Response<?> response) {
    }

    @Override
    public void clientError(Response<?> response) {
    }

    @Override
    public void serverError(Response<?> response) {
        mPresenter.serverError(response);
    }

    @Override
    public void networkError(IOException e) {
        mPresenter.networkError(e);
    }

    @Override
    public void unexpectedError(Throwable t) {
    }

    @Override
    public void finish() {
        mPresenter.onRequestFinish();
    }
}