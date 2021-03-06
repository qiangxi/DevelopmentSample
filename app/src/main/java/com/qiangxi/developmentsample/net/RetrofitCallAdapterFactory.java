package com.qiangxi.developmentsample.net;

import android.os.Handler;
import android.os.Looper;

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
        private Handler handler = new Handler(Looper.getMainLooper());
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
                public void onResponse(Call<T> call, final Response<T> response) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            int code = response.code();
                            if (code >= 200 && code < 300) {
                                callback.success(response);
                            } else if (code == 401) {
                                callback.unauthenticated(response);
                            } else if (code >= 400 && code < 500) {
                                callback.clientError(response);
                            } else if (code >= 500 && code < 600) {
                                callback.serverError(response);
                            } else {
                                callback.unexpectedError(new RuntimeException("其他异常: " + response));
                            }
                            callback.finish();
                        }
                    });
                }

                @Override
                public void onFailure(Call<T> call, final Throwable t) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (t instanceof IOException) {
                                callback.networkError((IOException) t);
                            } else {
                                callback.unexpectedError(t);
                            }
                            callback.finish();
                        }
                    });
                }
            });
        }

        @Override
        public RetrofitCall<T> clone() {
            return new MyCallAdapter<>(call.clone(), callbackExecutor);
        }
    }
}
