package com.example.admin.mydemo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.admin.mydemo.R;
import com.example.admin.mydemo.base.BaseHolder;
import com.example.admin.mydemo.base.DefaultAdapter;
import com.example.admin.mydemo.bean.SmsBean;

import java.util.List;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class SmsAdapter extends DefaultAdapter {
    private Context context;

    public SmsAdapter(Context context, List datas) {
        super(datas);
        this.context = context;
    }

    @Override
    protected BaseHolder getHolder() {
        return new SmsHolder();
    }

    class SmsHolder extends BaseHolder<SmsBean> {
        private TextView num, sms, type, data;

        @Override
        protected void refreshView(SmsBean data) {

            this.data.setText(data.getDate());
            if (data.getType().equals( "接收于")) {
                this.type.setText("接收于");
            } else if (data.getType().equals("发送至")) {
                this.type.setText("发送至");
            } else {
                this.type.setText("插入");
            }
            this.num.setText(data.getPhoneNumber());
            this.sms.setText(data.getSmsbody());
        }

        @Override
        protected View initView() {
            View view = View.inflate(context, R.layout.item_sms, null);
            num = (TextView) view.findViewById(R.id.num);
            sms = (TextView) view.findViewById(R.id.sms);
            type = (TextView) view.findViewById(R.id.type);
            data = (TextView) view.findViewById(R.id.data);

            return view;
        }
    }

}
