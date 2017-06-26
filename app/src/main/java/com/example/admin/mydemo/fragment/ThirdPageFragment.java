package com.example.admin.mydemo.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.admin.mydemo.R;
import com.example.admin.mydemo.base.BaseFragment;
import com.example.admin.mydemo.design.PoolInstance;
import com.example.admin.mydemo.design.ShowProgressDialog;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class ThirdPageFragment extends BaseFragment {
    private ListView        firstPageLv;
    private ProgressDialog  progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int setLayout() {
        progressDialog = ShowProgressDialog.getInstance(context);
        return R.layout.third_page;
    }

    @Override
    protected void initViews() {
        firstPageLv = this.bindView(R.id.first_page_lv);
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
