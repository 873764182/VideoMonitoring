package com.pixel.cs;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import java.util.Iterator;
import java.util.List;

/**
 * Created by pixel on 2017/9/12.
 */

public abstract class CsUtil {

    /**
     * 初始化环信SDK
     */
    public static void initEmSdk(Context context) {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(context, pid);
        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            return; // 则此application::onCreate 是被service 调用的，直接返回
        }
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        // options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(context, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    // 获取应用程序名称
    private static String getAppName(Context context, int pID) {
        String processName = "";
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        // PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                Log.d("Process", "Error>> :" + e.toString());
            }
        }
        return processName;
    }
}
