package com.qiangxi.developmentsample.net;

import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qiang_xi on 2017/4/9 0:25.
 * Retrofit配置类
 */

final class RetrofitConfig {
    private RetrofitConfig() {
    }

    static Retrofit generateRetrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.e(message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .addInterceptor(loggingInterceptor)
                .build();
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new RetrofitCallAdapterFactory())
                .baseUrl(Api.BASE_URL)
                .client(client)
                .build();
    }
}
