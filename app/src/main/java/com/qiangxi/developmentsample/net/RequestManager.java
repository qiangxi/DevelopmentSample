package com.qiangxi.developmentsample.net;


import com.qiangxi.developmentsample.presenter.UserInfoPresenter;

/**
 * Created by qiang_xi on 2017/4/4 16:46.
 * 策略模式,请求管理类
 */

public class RequestManager {
    private RequestManager() {
    }

    /**
     * 在退出应用时置空这些静态变量,方便JVM回收,避免内存泄漏
     */
    public static void shutdown() {
        RetrofitStrategy.shutdown();
    }


    public static void login(String phoneNumber, String password, UserInfoPresenter presenter) {
        RetrofitStrategy.login(phoneNumber, password, presenter);
    }
}
