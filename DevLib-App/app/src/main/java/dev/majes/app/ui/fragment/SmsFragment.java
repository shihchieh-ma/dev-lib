package dev.majes.app.ui.fragment;

import android.os.Bundle;
import android.view.View;
import dev.majes.app.R;
import dev.majes.base.mvp.BaseFragment;
import dev.majes.base.rxbus.IRxMsg;


/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class SmsFragment extends BaseFragment{


    @Override
    public void bindUI(View rootView) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment;
    }

    @Override
    public void onRxBusMsg(IRxMsg iRxMsg) {

    }

    @Override
    public Object getP() {
        return null;
    }
}


