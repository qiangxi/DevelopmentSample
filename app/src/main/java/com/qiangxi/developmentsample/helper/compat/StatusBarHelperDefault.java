package com.qiangxi.developmentsample.helper.compat;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;

/**
 * 作者 任强强 on 2017/4/11 12:05.
 * 4.4以下版本,默认空实现
 */

final class StatusBarHelperDefault implements IStatusBar {
    @Override
    public void setStatusBarColor(Activity activity, int statusColor) {
    }

    @Override
    public void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
    }

    @Override
    public void setStatusBarColorForCollapsingToolbar(Activity activity,
                                                      AppBarLayout appBarLayout,
                                                      CollapsingToolbarLayout collapsingToolbarLayout,
                                                      Toolbar toolbar,
                                                      int statusColor) {
    }

    @Override
    public void setStatusBarLightForCollapsingToolbar(Activity activity,
                                                      AppBarLayout appBarLayout,
                                                      CollapsingToolbarLayout collapsingToolbarLayout,
                                                      Toolbar toolbar,
                                                      int statusBarColor) {
    }
}
