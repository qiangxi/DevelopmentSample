package com.qiangxi.developmentsample.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qiangxi.developmentsample.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setImmersiveStatusBar(true);
    }

    protected void setImmersiveStatusBar(boolean setImmersiveStatusBar) {
        if (setImmersiveStatusBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }


}
