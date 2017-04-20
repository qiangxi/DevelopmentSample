package com.qiangxi.developmentsample.net;

import com.qiangxi.developmentsample.entity.QueryResult;
import com.qiangxi.developmentsample.presenter.UserInfoPresenter;

import retrofit2.Response;

/**
 * Created by qiang_xi on 2017/4/4 16:45.
 * 策略模式,使用Retrofit进行http/https请求,
 */

final class RetrofitStrategy {
    private static volatile ApiService mApiService;

    private static ApiService http() {
        if (mApiService == null) {
            synchronized (ApiService.class) {
                if (mApiService == null) {
                    mApiService = RetrofitConfig.generateRetrofit().create(ApiService.class);
                }
            }
        }
        return mApiService;
    }

    private RetrofitStrategy() {
    }

    /**
     * 在退出应用时置空这些静态变量,方便JVM回收,避免内存泄漏
     */
    static void shutdown() {
        if (mApiService != null) {
            mApiService = null;
        }
    }

    static void login(String cookie, String phoneNumber, String password, final UserInfoPresenter presenter) {
        RetrofitCall<QueryResult<String>> login = http().getSmsValidateCode(cookie, phoneNumber);
        login.enqueue(new SimpleRetrofitCallback<QueryResult<String>>(presenter) {
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
