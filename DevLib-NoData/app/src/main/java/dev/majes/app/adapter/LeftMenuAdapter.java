package dev.majes.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import dev.majes.app.R;
import dev.majes.app.ui.base.BaseHolder;
import dev.majes.app.ui.base.DefaultAdapter;
import dev.majes.app.bean.LeftBean;

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
