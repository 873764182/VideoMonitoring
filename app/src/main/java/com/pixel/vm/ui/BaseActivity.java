package com.pixel.vm.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by pixel on 2017/9/12.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public volatile BaseActivity activity;

    public abstract int getContentViewId();

    public abstract void initialization(Bundle savedInstanceState);

    public final void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public final void printLog(Exception e) {
        showToast(e.getMessage());
        Log.e(getClass().getSimpleName(), "ACTIVITY", e);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.activity = this;

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE); // 沉浸式状态栏
        getWindow().setStatusBarColor(Color.TRANSPARENT);  // 状态栏颜色透明

        setContentView(getContentViewId());

        initialization(savedInstanceState);
    }
}
