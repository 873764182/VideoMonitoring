package com.pixel.vm.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.pixel.vm.R;

/**
 * 注册／登录
 */
public class LoginActivity extends BaseActivity {

    private EditText mEtUsername;
    private EditText mEdPassword;
    private Button mBtnLogin;
    private Button mBtnRegistration;
    private TextView mTvLog;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initialization(Bundle savedInstanceState) {
        mEtUsername = (EditText) findViewById(R.id.etUsername);
        mEdPassword = (EditText) findViewById(R.id.edPassword);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mBtnRegistration = (Button) findViewById(R.id.btnRegistration);
        mTvLog = (TextView) findViewById(R.id.tvLog);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        mBtnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regis();
            }
        });
    }

    private void login() {
        String username = mEtUsername.getText().toString();
        String password = mEdPassword.getText().toString();

        if (username.length() <= 0 || password.length() <= 0) {
            return;
        }

        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                updateLog("登录成功！");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(activity).setItems(new String[]{"作为摄像头", "作为控制端"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                updateLog("登录错误： " + i + "\t" + s);
            }

            @Override
            public void onProgress(int i, String s) {
                updateLog("登录进度： " + i + "\t" + s);
            }
        });
    }

    private void regis() {
        final String username = mEtUsername.getText().toString();
        final String password = mEdPassword.getText().toString();

        if (username.length() <= 0 || password.length() <= 0) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = "注册状态";
                try {
                    EMClient.getInstance().createAccount(username, password);
                    msg = "注册成功！";
                } catch (HyphenateException e) {
                    msg = "注册失败：" + e.getMessage();
                } finally {
                    updateLog(msg);
                }
            }
        }).start();
    }

    private void updateLog(final String log) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvLog.setText(mTvLog.getText().toString() + "\n" + log);
            }
        });
    }

}
