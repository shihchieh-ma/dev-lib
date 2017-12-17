package dev.majes.base.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxFragment;

import dev.majes.base.log.Log;
import dev.majes.base.rxbus.IRxMsg;
import dev.majes.base.rxbus.RxBus;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * @author majes
 * @date 12/11/17.
 */

public abstract class BaseFragment<P extends IPrensenter> extends RxFragment implements IView<P> {
    private ICyc cyc;
    private P p;
    protected Activity activity;
    private View view;
    protected LayoutInflater layoutInflater;
    private RxPermissions rxPermissions;
    private RxBus rxBus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (view == null) {
            view = inflater.inflate(getLayoutId(), null);
            bindUI(view);
        } else {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    protected  <T> void registerRxBus(Class<T> eventType, Consumer<T> action) {
        rxBus = RxBus.getIntanceBus();
        Disposable disposable = rxBus.doSubscribe(eventType, action, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e("NewsMainPresenter" + throwable.toString());
            }
        });
        rxBus.addSubscription(this, disposable);
    }

    protected RxPermissions getRxPermissions() {
        if (null == rxPermissions) {
            synchronized (BaseFragment.class) {
                if (null == rxPermissions) {
                    rxPermissions = new RxPermissions(getActivity());
                }
            }
        }
        return rxPermissions;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    protected ICyc getCyc() {
        if (null == cyc) {
            synchronized (BaseFragment.class) {
                if (null == cyc) {
                    cyc = CycImpl.create(activity);
                }
            }
        }
        return cyc;
    }

    protected P getCorrespondingP() {
        if (p == null) {
            synchronized (BaseFragment.class) {
                if (null == p) {
                    p = getP();
                }
                if (null != p) {
                    p.bindView(this);
                }
            }
        }
        return p;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (null != activity) {
            activity = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getCorrespondingP() != null) {
            getCorrespondingP().unBindView();
        }
        if (null != rxPermissions) {
            rxPermissions = null;
        }
        getCyc().destory();
        p = null;
        cyc = null;
        if (null != rxBus) {
            rxBus.unSubscribe(this);
            rxBus = null;
        }
    }

    @Override
    public P getP() {
        return null;
    }


}
