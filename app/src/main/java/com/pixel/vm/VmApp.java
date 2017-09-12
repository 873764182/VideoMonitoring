package com.pixel.vm;

import android.app.Application;

import com.pixel.cs.CsUtil;

/**
 * Created by pixel on 2017/9/12.
 * <p>
 * 应用代理
 */

public class VmApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化环信SDK
        CsUtil.initEmSdk(this);
    }


}
