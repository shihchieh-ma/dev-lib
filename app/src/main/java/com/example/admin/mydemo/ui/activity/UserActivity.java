package com.example.admin.mydemo.ui.activity;

import android.widget.TextView;

import com.example.admin.mydemo.R;
import com.example.admin.mydemo.ui.base.BaseActivity;
import com.example.admin.mydemo.utils.SharedPreferencesUtils;


/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class UserActivity extends BaseActivity {
    private TextView textView;


    @Override
    protected void simpleInit() {

    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initViews() {
       textView = bindView(R.id.userwelcome);
    }

    @Override
    protected void setListeners() {

    }


    @Override
    protected void initData() {
        textView.setText("Welcome" + SharedPreferencesUtils.getParam(this
                ,"user",""));
    }

}


