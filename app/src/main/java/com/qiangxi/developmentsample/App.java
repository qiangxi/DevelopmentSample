package com.qiangxi.developmentsample;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * 作者 任强强 on 2017/4/6 11:39.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //release版使用LogLevel.NONE
        Logger.init("rqq").logLevel(LogLevel.FULL).methodCount(3).methodOffset(2);
    }
}
