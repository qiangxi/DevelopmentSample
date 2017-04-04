package com.qiangxi.developmentsample.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qiangxi.developmentsample.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initComponent();
        initEvent();
        initData();
    }

    /**
     * 初始化组件,如Dialog,adapter,list,bean,handler等其他对象
     */
    protected abstract void initComponent();

    /**
     * 初始化各种监听事件,如ButterKnife中没有声明的监听事件
     */
    protected abstract void initEvent();

    /**
     * 初始化数据,如intent传递过来的数据,本地数据库获取的数据等
     */
    protected abstract void initData();
}
