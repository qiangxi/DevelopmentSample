package com.qiangxi.developmentsample.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者 任强强 on 2017/4/6 13:51.
 * SharedPreferences帮助类,整个应用只维持这一个SharedPreferences
 * 所有需要SharedPreferences存储的数据全部存储在这个SharedPreferences名下
 */

public class SpHelper {
    private static final String mSpName = "AppData";//SharedPreferences名称

    private SpHelper() {
    }

    public static String getCookie(Context context) {
        SharedPreferences sp = context.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
        return sp.getString("cookie", null);
    }
}
