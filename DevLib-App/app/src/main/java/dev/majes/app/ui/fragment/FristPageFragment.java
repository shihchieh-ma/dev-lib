package dev.majes.app.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import dev.majes.app.R;
import dev.majes.base.log.Log;
import dev.majes.base.mvp.BaseLazyFragment;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class FristPageFragment extends BaseLazyFragment {

    @Override
    public void bindUI(View rootView) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Log.e("-----------------");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment;
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
