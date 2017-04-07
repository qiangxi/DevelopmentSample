package com.qiangxi.developmentsample.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者 任强强 on 2017/4/6 13:51.
 * SharedPreferences帮助类,整个应用只维持这一个SharedPreferences
 * 所有需要SharedPreferences存储的数据全部存储在这个SharedPreferences名下
 */

public class SpHelper {
    private static final String SHARE_PREFERENCES_NAME = "AppData";//SharedPreferences名称
    private static final String FIELD_COOKIE = "cookie";//cookie字段
    private static final String FIELD_DEVICE_ID = "deviceId";//deviceId字段

    private SpHelper() {
    }

    /**
     * 获取cookie
     */
    public static String getCookie(Context context) {
        StringBuilder sb = new StringBuilder();
        SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String cookie = sp.getString(FIELD_COOKIE, null);
        String deviceId = getDeviceId(context);
        sb.append("cookieDeviceId").append("=").append(deviceId).append(";").append(cookie);
        return sb.toString();
    }

    /**
     * 保存cookie
     */
    public static void saveCookie(Context context, String cookie) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(FIELD_COOKIE, cookie).apply();
    }

    /**
     * 获取deviceId
     */
    private static String getDeviceId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getString(FIELD_DEVICE_ID, null);
    }

    /**
     * 保存deviceId
     */
    public static void saveDeviceId(Context context, String deviceId) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(FIELD_DEVICE_ID, deviceId).apply();
    }
}
