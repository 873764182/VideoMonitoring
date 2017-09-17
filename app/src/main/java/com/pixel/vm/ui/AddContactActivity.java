package com.pixel.vm.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.pixel.vm.R;

/**
 * 添加联系人
 */
public class AddContactActivity extends BaseActivity {

    private TextView mActionLeft;
    private TextView mActionCenter;
    private TextView mActionRight;
    private EditText mEtContact;
    private EditText mEtReason;
    private Button mBtnAdd;

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_contact;
    }

    @Override
    public void initialization(Bundle savedInstanceState) {

        mActionLeft = (TextView) findViewById(R.id.actionLeft);
        mActionCenter = (TextView) findViewById(R.id.actionCenter);
        mActionRight = (TextView) findViewById(R.id.actionRight);
        mEtContact = (EditText) findViewById(R.id.etContact);
        mEtReason = (EditText) findViewById(R.id.etReason);
        mBtnAdd = (Button) findViewById(R.id.btnAdd);

        mActionLeft.setVisibility(View.VISIBLE);
        mActionLeft.setText("返回");

        mActionCenter.setVisibility(View.VISIBLE);
        mActionCenter.setText("添加联系人");

        mActionLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String contact = mEtContact.getText().toString();
                final String reason = mEtReason.getText().toString();
                if (contact.length() <= 0) {
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().contactManager().addContact(contact, reason);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast("添加成功");
                                    finish();
                                }
                            });
                        } catch (HyphenateException e) {
                            printLog(e);
                        }
                    }
                }).start();
            }
        });
    }

}
