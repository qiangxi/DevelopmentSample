package com.qiangxi.developmentsample.helper;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qiangxi.developmentsample.R;

/**
 * 作者 任强强 on 2017/4/7 13:46.
 * Toast帮助类,所有需要展示Toast的地方,全部使用这个帮助类
 */

public class ToastHelper {
    private static Toast mToast;

    private ToastHelper() {
    }

    /**
     * 展示一条短的toast
     *
     * @param context 上下文
     * @param message 要显示的消息
     */
    public static void show(Context context, CharSequence message) {
        show(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 展示一条短的toast
     *
     * @param context 上下文
     * @param resId   要显示的消息的资源id
     */
    public static void show(Context context, int resId) {
        if (context == null) {
            return;
        }
        show(context, context.getApplicationContext().getResources().getText(resId),
                Toast.LENGTH_SHORT);
    }

    /**
     * 展示一条长的toast
     *
     * @param context 上下文
     * @param message 要显示的消息
     */
    public static void showLong(Context context, CharSequence message) {
        show(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 展示一条长的toast
     *
     * @param context 上下文
     * @param resId   要显示的消息的资源id
     */
    public static void showLong(Context context, int resId) {
        if (context == null) {
            return;
        }
        show(context, context.getApplicationContext().getResources().getText(resId),
                Toast.LENGTH_LONG);
    }

    /**
     * 具体的实现方法
     *
     * @param context  上下文
     * @param message  要显示的消息
     * @param duration 要显示的时长
     */
    private static void show(Context context, CharSequence message, int duration) {
        if (context == null) {
            return;
        }
        float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        if (mToast == null) {
            mToast = new Toast(context.getApplicationContext());
            LayoutInflater inflate =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View toastLayout = inflate.inflate(R.layout.toast_layout, null);
            TextView tv = (TextView) toastLayout.findViewById(R.id.toastLayoutMessage);
            tv.setText(message);
            mToast.setView(toastLayout);
        } else {
            TextView tv = (TextView) mToast.getView().findViewById(R.id.toastLayoutMessage);
            tv.setText(message);
        }
        //底部居中,距离屏幕底部60dp
        mToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
                (int) ((60 * scale) + 0.5f));
        mToast.setDuration(duration);
        mToast.show();
    }
}
