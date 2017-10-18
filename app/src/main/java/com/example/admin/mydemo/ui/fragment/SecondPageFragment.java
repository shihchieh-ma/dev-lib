package com.example.admin.mydemo.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.admin.mydemo.R;
import com.example.admin.mydemo.ui.base.BaseFragment;
import com.example.admin.mydemo.commen.PoolInstance;
import com.example.admin.mydemo.commen.ShowProgressDialog;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class SecondPageFragment extends BaseFragment {
    private ListView         secondPageLv;
    private ProgressDialog   progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int setLayout() {
        progressDialog = ShowProgressDialog.getInstance(context);
        return R.layout.second_page;
    }

    @Override
    protected void initViews() {
        secondPageLv = this.bindView(R.id.second_page_lv);
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
