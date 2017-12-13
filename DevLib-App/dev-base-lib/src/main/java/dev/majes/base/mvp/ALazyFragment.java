package dev.majes.base.mvp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.Field;


/**
 * @author majes
 * @date 12/11/17.
 */

public abstract class ALazyFragment extends RxFragment {
    //用户可见
    private static final int STATE_VISIBLE = 1;
    //未设置值
    private static final int STATE_NOT_SET = -1;
    //用户不可见
    private static final int STATE_NOT_VISIBLE = 0;

    private static final String ROOT_FRAGMENT = "lazy_loading_fragment";

    protected LayoutInflater layoutInflater;
    protected Activity context;
    private View rootView;
    private ViewGroup container;
    private boolean isInitReady = false;
    private int isVisibleToUserState = STATE_NOT_SET;
    private Bundle saveInstanceState;
    private boolean isLazyEnable = true;
    private boolean isStart = false;
    private FrameLayout layout;


    protected abstract void onCreateViewLazy(Bundle savedInstanceState);

    protected abstract View getPreviewLayout(LayoutInflater mInflater, FrameLayout layout);

    protected abstract void onStartLazy();

    protected abstract void onStopLazy();

    protected abstract void onResumeLazy();

    protected abstract void onPauseLazy();

    protected abstract void onDestoryLazy();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.layoutInflater = inflater;
        this.container = container;
        this.saveInstanceState = savedInstanceState;
        boolean isVisible;
        if (isVisibleToUserState == STATE_NOT_SET) {
            isVisible = getUserVisibleHint();
        } else {
            isVisible = isVisibleToUserState == STATE_VISIBLE;
        }
        if (isLazyEnable) {
            if (isVisible && !isInitReady) {
                onCreateViewLazy(savedInstanceState);
                isInitReady = true;
            } else {
                LayoutInflater mInflater = layoutInflater;
                if (mInflater == null && context != null) {
                    mInflater = LayoutInflater.from(context);
                }
                layout = new FrameLayout(context);
                layout.setTag(ROOT_FRAGMENT);

                View view = getPreviewLayout(mInflater, layout);
                if (view != null) {
                    layout.addView(view);
                }
                layout.setLayoutParams(
                        new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                setContentView(layout);
            }
        } else {
            onCreateViewLazy(savedInstanceState);
            isInitReady = true;
        }
        if (rootView == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisibleToUserState = isVisibleToUser ? STATE_VISIBLE : STATE_NOT_VISIBLE;
        if (isVisibleToUser
                && !isInitReady
                && rootView != null) {
            isInitReady = true;
            onCreateViewLazy(saveInstanceState);
            onResumeLazy();
        }
        if (isInitReady && rootView != null) {
            if (isVisibleToUser) {
                isStart = true;
                onStartLazy();
            } else {
                isStart = false;
                onStopLazy();
            }
        }
    }

    protected View getRootView() {
        if (rootView != null) {
            if (rootView instanceof FrameLayout
                    && ROOT_FRAGMENT.equals(rootView.getTag())) {
                return ((FrameLayout) rootView).getChildAt(0);
            }
        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected void setContentView(int layoutResID) {
        if (isLazyEnable && rootView != null && rootView.getParent() != null) {
            layout.removeAllViews();
            View view = layoutInflater.inflate(layoutResID, layout, false);
            layout.addView(view);
        } else {
            rootView = layoutInflater.inflate(layoutResID, container, false);
        }
    }

    protected void setContentView(View view) {
        if (isLazyEnable && rootView != null && rootView.getParent() != null) {
            layout.removeAllViews();
            layout.addView(view);
        } else {
            rootView = view;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isInitReady) {
            onResumeLazy();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isInitReady) {
            onPauseLazy();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isInitReady
                && !isStart
                && getUserVisibleHint()) {
            isStart = true;
            onStartLazy();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isInitReady
                && isStart
                && getUserVisibleHint()) {
            isStart = false;
            onStopLazy();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.context = (Activity) context;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView = null;
        container = null;
        layoutInflater = null;
        if (isInitReady) {
            onDestoryLazy();
        }
        isInitReady = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
