package com.qiangxi.developmentsample.net;

import com.orhanobut.logger.Logger;
import com.qiangxi.developmentsample.entity.QueryResult;
import com.qiangxi.developmentsample.presenter.UserInfoPresenter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by qiang_xi on 2017/4/4 16:45.
 * 单例模式+策略模式,使用Retrofit进行http/https请求,
 */

class RetrofitStrategy {
    private static Retrofit mRetrofit;
    private static ApiService mApiService;
    private static OkHttpClient mClient;

    static {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.e(message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mClient == null) {
            mClient = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .addInterceptor(loggingInterceptor)
                    .build();
        }
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new RetrofitCallAdapterFactory())
                    .baseUrl(Api.BASE_URL)
                    .client(mClient)
                    .build();
        }
        if (mApiService == null) {
            mApiService = mRetrofit.create(ApiService.class);
        }
    }

    private RetrofitStrategy() {
    }

    /**
     * 在退出应用时置空这些静态变量,方便JVM回收,避免内存泄漏
     */
    static void shutdown() {
        if (mClient != null) {
            mClient = null;
        }
        if (mRetrofit != null) {
            mRetrofit = null;
        }
        if (mApiService != null) {
            mApiService = null;
        }
    }

    static void login(String phoneNumber, String password, final UserInfoPresenter presenter) {
        RetrofitCall<QueryResult<String>> login = mApiService.getSmsValidateCode(phoneNumber);
        login.enqueue(new RetrofitCallAdapterFactory.SimpleRetrofitCallback<QueryResult<String>>(presenter) {
            @Override
            public void success(Response<QueryResult<String>> response) {
                QueryResult<String> body = response.body();
                if (body.isSuccess()) {
                    presenter.userInfoRequestSuccess(body.getResult());
                } else {
                    presenter.userInfoRequestFailure(body.getExceptionMessage());
                    if (Api.RELOGIN_ERROR_CODE_01.equals(body.getExceptionMessage())) {
                        presenter.onReLogin();
                    }
                }
            }
        });
    }
}
