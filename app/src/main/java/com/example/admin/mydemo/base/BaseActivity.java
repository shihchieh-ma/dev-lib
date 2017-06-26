package com.example.admin.mydemo.base;


import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在activity没有执行关联之前，UILooper是空闲的
        //利用looper来进行一系列的初始化操作
        //优化activity的启动速度
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                simpleInit();
                return false;
            }
        });
        setContentView(setLayout());
        //定制流程
        //绑定布局
        //初始化布局
        //使用数据，设置监听
        initViews();
        setListeners();
        initData();
    }

    protected abstract void simpleInit();

    /**
     *
     * 设置布局
     */
    protected abstract int setLayout();
    /**
     * 初始化组件
     */
    protected abstract void initViews();

    /**
     * 设置监听
     */
    protected abstract void setListeners();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    protected <T extends View> T bindView(int resId){
        return (T) findViewById(resId);
    }

    @Override
    public void finish() {
        super.finish();
        //加入activity的进入和退出动画
        //统一设置
//        overridePendingTransition();
        //在oncreate中结束，push进去
        //在finish或者onDestory中pop弹出
//        Stack<Activity> stack=new Stack<>();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    protected void goTo(Class<? extends BaseActivity> to){
        Intent intent=new Intent();
        intent.setClass(this,to);
        startActivity(intent);
    }

    protected void goTo(Class<? extends BaseActivity> to,Bundle bundle){
        Intent intent=new Intent(this,to);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    protected void goTo(Class<? extends BaseActivity> to,String title){
        Intent intent=new Intent(this,to);
        intent.putExtra("title", title);
        startActivity(intent);
    }
}
