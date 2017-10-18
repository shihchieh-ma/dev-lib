package com.example.admin.mydemo.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public abstract class BaseFragment extends Fragment{
    public static String ONE,TWO,THREE,FOUR;
    protected Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //为fragment设置布局
        return inflater.inflate(setLayout(),container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化组件
        initViews();
    }

    //Activity的onCreate执行完毕
    //需要getActivity,最好在onActivityCreated方法中进行
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //使用数据
        initDatas();
    }

    //定制fragment流程
    protected abstract int setLayout();
    protected abstract void initViews();
    protected abstract void initDatas();

    //精简findviewbyid
    protected <T extends View> T bindView(int resId){
        return (T) getView().findViewById(resId);
    }
}
