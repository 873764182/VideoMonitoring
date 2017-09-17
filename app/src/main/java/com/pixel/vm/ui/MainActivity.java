package com.pixel.vm.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.pixel.vm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {

    private TextView mActionLeft;
    private TextView mActionCenter;
    private TextView mActionRight;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private final List<String> contactList = new ArrayList<>();
    private ListAdapter listAdapter = null;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initialization(Bundle savedInstanceState) {

        mActionLeft = (TextView) findViewById(R.id.actionLeft);
        mActionCenter = (TextView) findViewById(R.id.actionCenter);
        mActionRight = (TextView) findViewById(R.id.actionRight);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mActionLeft.setVisibility(View.VISIBLE);
        mActionLeft.setText("退出");

        mActionCenter.setVisibility(View.VISIBLE);
        mActionCenter.setText("主页列表");

        mActionRight.setVisibility(View.VISIBLE);
        mActionRight.setText("菜单");

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mActionLeft) {
                    logout();
                }
                if (v == mActionRight) {
                    showMenu();
                }
            }
        };
        mActionLeft.setOnClickListener(clickListener);
        mActionRight.setOnClickListener(clickListener);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initList();
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                initList();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(listAdapter = new ListAdapter());
    }

    public void logout() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().logout(true);
            }
        }).start();
        finish();
    }

    public void showMenu() {
        new AlertDialog.Builder(activity).setItems(new String[]{"添加好友"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    startActivity(new Intent(activity, AddContactActivity.class));
                }
            }
        }).show();
    }

    public void initList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<String> list = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Log.e("", "好友列表数量" + list.size());
                    if (list != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                contactList.clear();
                                contactList.addAll(list);
                                listAdapter.notifyDataSetChanged();
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                } catch (HyphenateException e) {
                    printLog(e);
                }
            }
        }).start();
    }

    private class ListHolder extends RecyclerView.ViewHolder {
        TextView tvContact;

        public ListHolder(View itemView) {
            super(itemView);
            tvContact = (TextView) itemView.findViewById(R.id.tvContact);
        }
    }

    private class ListAdapter extends RecyclerView.Adapter<ListHolder> {

        @Override
        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ListHolder(getLayoutInflater().inflate(R.layout.view_contact_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ListHolder holder, int position) {
            holder.tvContact.setText(contactList.get(position));
        }

        @Override
        public int getItemCount() {
            return contactList.size();
        }
    }

}
