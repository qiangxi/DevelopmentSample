package com.qiangxi.developmentsample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiangxi.developmentsample.base.BaseActivity;
import com.qiangxi.developmentsample.helper.CaseViewHelper;
import com.qiangxi.developmentsample.helper.SpHelper;
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
    RelativeLayout mActivityMain;

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
        RequestManager.login(SpHelper.getCookie(this), "18894001263", "1234", this);
    }


    @Override
    public void userInfoRequestSuccess(String state) {
        mTextView.setText("userInfoRequestSuccess");
        //数据成功返回
    }

    @Override
    public void userInfoRequestFailure(String errorCode) {
        //数据未能成功返回
        mTextView.setText("userInfoRequestFailure");
    }

    @Override
    public void onRequestStart() {
        mTextView.setText("onRequestStart");
        mDialog.show();
    }

    @Override
    public void onRequestFinish() {
        mTextView.setText("onRequestFinish");
        mDialog.dismiss();
    }

    @Override
    public void serverError(Response<?> response) {
        mTextView.setText("serverError");
    }

    @Override
    public void networkError(IOException e) {
        mTextView.setText("networkError");
    }

    @Override
    public void onReLogin() {
        //重新登录,跳转到登录界面
        mTextView.setText("onReLogin");
    }

    @OnClick(R.id.textView)
    public void onClick() {
        if (!isShow) {
            isShow = true;
            CaseViewHelper.showNoNetworkView(mActivityMain, new View.OnClickListener() {
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
