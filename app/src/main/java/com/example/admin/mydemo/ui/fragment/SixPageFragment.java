package com.example.admin.mydemo.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.admin.mydemo.R;
import com.example.admin.mydemo.ui.base.BaseFragment;
import com.example.admin.mydemo.commen.PoolInstance;
import com.example.admin.mydemo.commen.ShowProgressDialog;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class SixPageFragment extends BaseFragment {
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int setLayout() {
        progressDialog = ShowProgressDialog.getInstance(context);
        return R.layout.six_page;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initDatas() {
        //progressDialog.show();
        PoolInstance.getPoolInstance().startThread(new Runnable() {
            @Override
            public void run() {
            }
        });

    }


}
