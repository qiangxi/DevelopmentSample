package com.qiangxi.developmentsample.net;

import com.qiangxi.developmentsample.presenter.BasePresenter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by qiang_xi on 2017/4/4 21:00.
 * 自定义的CallAdapter.Factory
 */

final class RetrofitCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != RetrofitCall.class) {
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException(
                    "RetrofitCall 至少必须是一个通用类型 (如 RetrofitCall<ResponseBody>)");
        }
        Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Executor callbackExecutor = retrofit.callbackExecutor();
        return new RetrofitCallAdapter<>(responseType, callbackExecutor);
    }

    private static final class RetrofitCallAdapter<R> implements CallAdapter<R, RetrofitCall<R>> {
        private final Type responseType;
        private final Executor callbackExecutor;

        RetrofitCallAdapter(Type responseType, Executor callbackExecutor) {
            this.responseType = responseType;
            this.callbackExecutor = callbackExecutor;
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public RetrofitCall<R> adapt(Call<R> call) {
            return new MyCallAdapter<>(call, callbackExecutor);
        }
    }

    private static class MyCallAdapter<T> implements RetrofitCall<T> {
        private final Call<T> call;
        private final Executor callbackExecutor;

        MyCallAdapter(Call<T> call, Executor callbackExecutor) {
            this.call = call;
            this.callbackExecutor = callbackExecutor;
        }

        @Override
        public void cancel() {
            call.cancel();
        }

        @Override
        public void enqueue(final RetrofitCallback<T> callback) {
            callback.start();
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    int code = response.code();
                    if (code >= 200 && code < 300) {
                        callback.success(response);
                    } else {
                        if (code == 401) {
                            callback.unauthenticated(response);
                        } else if (code >= 400 && code < 500) {
                            callback.clientError(response);
                        } else if (code >= 500 && code < 600) {
                            callback.serverError(response);
                        } else {
                            callback.unexpectedError(new RuntimeException("其他异常: " + response));
                        }
                    }
                    callback.finish();
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    if (t instanceof IOException) {
                        callback.networkError((IOException) t);
                    } else {
                        callback.unexpectedError(t);
                    }
                    callback.finish();
                }
            });
        }

        @Override
        public RetrofitCall<T> clone() {
            return new MyCallAdapter<>(call.clone(), callbackExecutor);
        }
    }

    static class SimpleRetrofitCallback<T> implements RetrofitCallback<T> {
        BasePresenter mPresenter;

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
}
