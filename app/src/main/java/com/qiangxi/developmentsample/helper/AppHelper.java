package com.qiangxi.developmentsample.helper;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.List;

/**
 * 作者 任强强 on 2017/4/10 11:22.
 * App帮助类,获取/判断app各种信息
 */

public class AppHelper {
    private AppHelper() {
    }

    /**
     * 获取当前应用版本名
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取当前应用版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    /**
     * 给定service是否正在运行
     * @param context 上下文
     * @param cls service的class类型,如MyService.class
     * @return true:正在运行,false:没在运行
     */
    public static boolean isServiceRunning(Context context, Class<?> cls) {
        String serviceFullName = cls.getName();
        ActivityManager manager = (ActivityManager)
                context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices =
                manager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo runningService : runningServices) {
            if (TextUtils.equals(serviceFullName, runningService.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 指定activity是否正在前台显示
     * @param context 上下文
     * @param cls activity的class类型,如MainActivity.class
     * @return true:正在前台显示,false:没在前台显示
     */
    public static boolean isActivityForeground(Context context, Class<?> cls) {
        String activityFullName = cls.getName();
        ActivityManager manager = (ActivityManager)
                context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks =
                manager.getRunningTasks(Integer.MAX_VALUE);
        for (ActivityManager.RunningTaskInfo runningTask : runningTasks) {
            if (TextUtils.equals(activityFullName, runningTask.topActivity.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
