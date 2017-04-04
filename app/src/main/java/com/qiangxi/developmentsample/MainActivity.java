package com.qiangxi.developmentsample;

import android.os.Bundle;

import com.qiangxi.developmentsample.base.BaseActivity;
import com.qiangxi.developmentsample.net.RequestManager;
import com.qiangxi.developmentsample.presenter.UserInfoPresenter;

public class MainActivity extends BaseActivity implements UserInfoPresenter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initComponent() {
//        dialog = new Dialog(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        RequestManager.login("18894001263", "1234", this);
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
        //dialog.show();
    }

    @Override
    public void onRequestFinish() {
        //dialog.dismiss();
    }

    @Override
    public void onRequestError() {
        //showToast("服务器连接异常");
    }

    @Override
    public void onReLogin() {
        //重新登录,跳转到登录界面
    }
}
