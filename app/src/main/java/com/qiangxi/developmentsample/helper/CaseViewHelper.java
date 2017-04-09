package com.qiangxi.developmentsample.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiangxi.developmentsample.R;

/**
 * Created by qiang_xi on 2017/4/8 16:58.
 * 为 无网络/服务器异常/暂无数据 情况下设置提示布局的帮助类
 */

public class CaseViewHelper {
    private CaseViewHelper() {
    }

    /**
     * 展示无网络提示布局
     *
     * @param viewGroup 要在哪个根布局下展示
     * @param listener  点击刷新按钮的监听
     */
    public static void showNoNetworkView(ViewGroup viewGroup, View.OnClickListener listener) {
        showCaseView(viewGroup, R.drawable.icon_no_network, "网络异常", listener);
    }

    /**
     * 展示暂无数据提示布局
     *
     * @param viewGroup 要在哪个根布局下展示
     */
    public static void showNoResultView(ViewGroup viewGroup) {
        showCaseView(viewGroup, R.drawable.icon_no_result, "暂无数据", null);
    }

    /**
     * 展示服务器异常提示布局
     *
     * @param viewGroup 要在哪个根布局下展示
     * @param listener  点击刷新按钮的监听
     */
    public static void showServerErrorView(ViewGroup viewGroup, View.OnClickListener listener) {
        showCaseView(viewGroup, R.drawable.icon_server_error, "服务器异常", listener);
    }

    /**
     * 展示提示布局
     *
     * @param viewGroup 要在哪个根布局下展示
     * @param resId     要展示的图片id
     * @param message   要展示的信息
     * @param listener  点击刷新按钮的监听
     */
    private static void showCaseView(ViewGroup viewGroup, int resId, CharSequence message,
                                     View.OnClickListener listener) {
        if (viewGroup == null) {
            return;
        }
        //让viewGroup先隐藏,避免当子view过多时,依次隐藏造成的卡顿感觉
        viewGroup.setVisibility(View.GONE);
        //隐藏所有子view
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView != null) {
                childView.setVisibility(View.GONE);
            }
        }
        //显示caseView
        Context context = viewGroup.getContext();
        LayoutInflater inflate =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View caseView = inflate.inflate(R.layout.case_view_layout, null);
        //提示图片
        ImageView caseViewImage = (ImageView) caseView.findViewById(R.id.caseViewImage);
        caseViewImage.setImageResource(resId);
        //提示信息
        TextView caseViewMessage = (TextView) caseView.findViewById(R.id.caseViewMessage);
        caseViewMessage.setText(message);
        //刷新按钮
        TextView caseViewRefresh = (TextView) caseView.findViewById(R.id.caseViewRefresh);
        if (listener == null) {
            caseViewRefresh.setVisibility(View.GONE);
        } else {
            caseViewRefresh.setOnClickListener(listener);
        }
        //为caseView设置布局参数
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        caseView.setLayoutParams(layoutParams);
        viewGroup.addView(caseView);
        //一切准备就绪后,再显示viewGroup
        viewGroup.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏提示布局
     *
     * @param viewGroup 在哪个根布局下展示的
     */
    public static void hideCaseView(ViewGroup viewGroup) {
        if (viewGroup == null) {
            return;
        }
        viewGroup.setVisibility(View.GONE);
        int childCount = viewGroup.getChildCount();
        //移除caseView
        viewGroup.removeViewAt(childCount - 1);
        //显示所有子view
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView != null) {
                childView.setVisibility(View.VISIBLE);
            }
        }
        viewGroup.setVisibility(View.VISIBLE);
    }
}
