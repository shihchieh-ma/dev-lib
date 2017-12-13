package dev.majes.app.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import dev.majes.app.R;
import dev.majes.base.mvp.BaseLazyLoadFragment;
import dev.majes.base.rxbus.IRxMsg;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class FristPageFragment extends BaseLazyLoadFragment {

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

    @Override
    protected View getPreviewLayout(LayoutInflater mInflater, FrameLayout layout) {
        return null;
    }

    @Override
    protected void onStartLazy() {

    }

    @Override
    protected void onStopLazy() {

    }

    @Override
    protected void onResumeLazy() {

    }

    @Override
    protected void onPauseLazy() {

    }
}
