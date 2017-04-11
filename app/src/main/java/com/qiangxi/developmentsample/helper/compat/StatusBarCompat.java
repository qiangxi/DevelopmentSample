package com.qiangxi.developmentsample.helper.compat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 作者 任强强 on 2017/4/11 10:04.
 * 该帮助类根据https://github.com/imflyn/Eyes修改而来
 */

public final class StatusBarCompat {
    private static IStatusBar IMPL;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            IMPL = new StatusBarHelperLollipop();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            IMPL = new StatusBarHelperKitKat();
        } else {
            IMPL = new StatusBarHelperDefault();
        }
    }

    /**
     * 设置状态栏颜色
     */
    public static void setStatusBarColor(Activity activity, int statusColor) {
        IMPL.setStatusBarColor(activity, statusColor);
    }

    /**
     * 设置透明状态栏,内容延伸到状态栏
     */
    public static void translucentStatusBar(Activity activity) {
        IMPL.translucentStatusBar(activity, false);
    }

    /**
     * 设置透明状态栏,内容延伸到状态栏
     * @param hideStatusBarBackground 是否隐藏状态栏
     */
    public static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        IMPL.translucentStatusBar(activity, hideStatusBarBackground);
    }

    /**
     * 为 CollapsingToolbar设置状态栏颜色
     */
    public static void setStatusBarColorForCollapsingToolbar(@NonNull Activity activity,
                                                             AppBarLayout appBarLayout,
                                                             CollapsingToolbarLayout collapsingToolbarLayout,
                                                             Toolbar toolbar, @ColorInt int statusColor) {
        IMPL.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
    }

    /**
     * 设置亮色模式,亮色模式下,状态栏字体颜色会相应变色(注意:对于非小米,魅族手机,只对亮色模式有效,暗色模式无效)
     */
    public static void setStatusBarLightMode(Activity activity, int color) {
        //判断是否为小米或魅族手机，如果是则将状态栏文字改为黑色
        if (MIUISetStatusBarLightMode(activity, true) || FlymeSetStatusBarLightMode(activity, true)) {
            IMPL.setStatusBarColor(activity, color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setStatusBarLightForMarshmallow(activity, color);
        }
    }

    /**
     * 为6.0以上设备设置亮色状态栏
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static void setStatusBarLightForMarshmallow(Activity activity, int color) {
        //如果是6.0以上将状态栏文字改为黑色，并设置状态栏颜色
        activity.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        activity.getWindow().getDecorView().setSystemUiVisibility(option);
        activity.getWindow().setStatusBarColor(color);
        //fitsSystemWindow 为 false, 不预留系统栏位置.
        ViewGroup mContentView = (ViewGroup) activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, true);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    /**
     * 在亮色状态栏下为CollapsingToolbar设置状态栏颜色
     */
    public static void setStatusBarLightForCollapsingToolbar(Activity activity,
                                                             AppBarLayout appBarLayout,
                                                             CollapsingToolbarLayout collapsingToolbarLayout,
                                                             Toolbar toolbar, int statusBarColor) {
        IMPL.setStatusBarLightForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusBarColor);
    }

    /**
     * MIUI 6的沉浸支持透明白色字体和透明黑色字体
     */
    static boolean MIUISetStatusBarLightMode(Activity activity, boolean darkmode) {
        boolean result = false;
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     */
    static boolean FlymeSetStatusBarLightMode(Activity activity, boolean darkmode) {
        boolean result = false;
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkmode) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    static void setContentTopPadding(Activity activity, int padding) {
        ViewGroup mContentView = (ViewGroup) activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        mContentView.setPadding(0, padding, 0, 0);
    }

    static int getPxFromDp(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
