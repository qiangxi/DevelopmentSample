package com.qiangxi.developmentsample.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.qiangxi.developmentsample.R;

/**
 * 作者 任强强 on 2017/4/24 11:43.
 * 中间悬浮dialog
 */

public class CenterFloatDialog extends DialogFragment {
    private DialogConfiguration mConfiguration;
    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;
    private boolean mCancelableOnTouchOutside;

    public void setConfig(DialogConfiguration configuration) {
        mConfiguration = configuration;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        setCancelable(false);//点击dialog外部区域或者点击返回键,dialog都不可dismiss
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(mCancelableOnTouchOutside);//点击dialog外部区域,不可dismiss,但点击返回键可以
            Window window = dialog.getWindow();
            if (window != null) {
                window.requestFeature(Window.FEATURE_NO_TITLE);
//                window .setWindowAnimations(R.);
            }
        }
        return inflater.inflate(R.layout.center_float_dialog_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mConfiguration == null) {
            mConfiguration = new DialogConfiguration.Builder().build();
        }
        TextView dialogContent = (TextView) view.findViewById(R.id.contentTwoButtonContent);
        dialogContent.setText(mConfiguration.getDialogContent());
        Button cancel = (Button) view.findViewById(R.id.contentTwoButtonCancel);
        cancel.setText(mConfiguration.getDialogLeftText());
        Button confirm = (Button) view.findViewById(R.id.contentTwoButtonConfirm);
        confirm.setText(mConfiguration.getDialogRightText());
        cancel.setOnClickListener(mLeftClickListener);
        confirm.setOnClickListener(mRightClickListener);
    }

    public void setOnLeftClickListener(View.OnClickListener listener) {
        mLeftClickListener = listener;
    }

    public void setOnRightClickListener(View.OnClickListener listener) {
        mRightClickListener = listener;
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        mCancelableOnTouchOutside = cancel;
    }
}
