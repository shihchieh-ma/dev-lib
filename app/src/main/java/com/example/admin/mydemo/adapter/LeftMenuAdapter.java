package com.example.admin.mydemo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.admin.mydemo.R;
import com.example.admin.mydemo.base.BaseHolder;
import com.example.admin.mydemo.base.DefaultAdapter;
import com.example.admin.mydemo.bean.LeftBean;

import java.util.List;

/**
 * Created by Marl_Jar on 2017/6/6.
 */

public class LeftMenuAdapter extends DefaultAdapter {

    private Context context;

    public LeftMenuAdapter(Context context,List datas) {
        super(datas);
        this.context = context;
    }

    @Override
    protected BaseHolder getHolder() {
        return new LeftHolder();
    }

    class LeftHolder extends BaseHolder<LeftBean> {
        private TextView str;
        @Override
        protected void refreshView(LeftBean data) {
           this.str.setText(data.getStr());
        }

        @Override
        protected View initView() {
            View view = View.inflate(context, R.layout.listview_item, null);
            str = (TextView) view.findViewById(R.id.left_tv);
            return view;
        }
    }
}
