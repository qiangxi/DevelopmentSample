package com.qiangxi.developmentsample.helper.compat;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;

/**
 * 作者 任强强 on 2017/4/11 11:49.
 * IStatusBar
 */

interface IStatusBar {
    void setStatusBarColor(Activity activity, int statusColor);

    void translucentStatusBar(Activity activity, boolean hideStatusBarBackground);

    void setStatusBarColorForCollapsingToolbar(final Activity activity,
                                               final AppBarLayout appBarLayout,
                                               final CollapsingToolbarLayout collapsingToolbarLayout,
                                               Toolbar toolbar,
                                               final int statusColor);

    void setStatusBarLightForCollapsingToolbar(final Activity activity,
                                               final AppBarLayout appBarLayout,
                                               final CollapsingToolbarLayout collapsingToolbarLayout,
                                               final Toolbar toolbar,
                                               final int statusBarColor);
}

