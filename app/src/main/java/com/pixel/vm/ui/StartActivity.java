package com.pixel.vm.ui;

import android.content.Intent;
import android.os.Bundle;

import com.pixel.vm.R;

/**
 * 启动页面 避免启动白屏
 */
public class StartActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_start;
    }

    @Override
    public void initialization(Bundle savedInstanceState) {
        startActivity(new Intent(activity, LoginActivity.class));
        finish();
    }

}
