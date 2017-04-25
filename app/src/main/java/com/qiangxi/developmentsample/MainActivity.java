package com.qiangxi.developmentsample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiangxi.developmentsample.base.BaseActivity;
import com.qiangxi.developmentsample.dialog.CenterFloatDialog;
import com.qiangxi.developmentsample.dialog.DialogConfiguration;
import com.qiangxi.developmentsample.helper.CaseViewHelper;
import com.qiangxi.developmentsample.helper.ToastHelper;
import com.qiangxi.developmentsample.net.RequestManager;
import com.qiangxi.developmentsample.presenter.UserInfoPresenter;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements UserInfoPresenter {

    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;

    private ProgressDialog mDialog;
    private boolean isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initComponent();
        initEvent();
        initData();
    }

    protected void initComponent() {
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("请求开始");
    }

    protected void initEvent() {

    }

    protected void initData() {
//        RequestManager.login(SpHelper.getCookie(this), "18894001263", "1234", this);
    }


    @Override
    public void userInfoRequestSuccess(String state) {
        //数据成功返回
    }

    @Override
    public void userInfoRequestFailure(String errorCode) {
        //数据未能成功返回
    }

    @Override
    public void onRequestStart() {
        //请求开始
        mDialog.show();
    }

    @Override
    public void onRequestFinish() {
        //请求结束
        mDialog.dismiss();
    }

    @Override
    public void serverError(Response<?> response) {
        //服务器异常
    }

    @Override
    public void networkError(IOException e) {
        //网络异常
    }

    @Override
    public void onReLogin() {
        //重新登录,跳转到登录界面
    }

    @OnClick(R.id.textView)
    public void onClick() {
        DialogConfiguration configuration = new DialogConfiguration.Builder()
                .setDialogContent("您已断开网络连接")
                .setDialogLeftText("不连接")
                .setDialogRightText("重新连接")
                .build();
        final CenterFloatDialog dialog = new CenterFloatDialog();
        dialog.setConfig(configuration);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismissAllowingStateLoss();
                ToastHelper.show(MainActivity.this, "点击左边按钮");
            }
        });

        dialog.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismissAllowingStateLoss();
                ToastHelper.show(MainActivity.this, "点击右边按钮");
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        dialog.show(transaction, "CenterFloatDialog");
        if (!isShow) {
            isShow = true;
            CaseViewHelper.showServerErrorView(mActivityMain, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CaseViewHelper.hideCaseView(mActivityMain);
                    isShow = false;
                    ToastHelper.show(MainActivity.this, "大神大神大所大所大所大所多所大所大所大所大所大所大所大所大所大所大所大所大所大所大所大");
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.shutdown();
    }
}
