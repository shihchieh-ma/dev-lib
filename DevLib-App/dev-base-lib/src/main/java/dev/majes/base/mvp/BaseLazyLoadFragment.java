package dev.majes.base.mvp;


import android.os.Bundle;

import com.tbruyelle.rxpermissions2.RxPermissions;

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

public abstract class BaseLazyLoadFragment<P extends IPrensenter> extends ALazyFragment
        implements IView<P> {

    private ICyc cyc;
    private P p;
    private RxPermissions rxPermissions;
    private RxBus rxBus;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        setContentView(getRootView());
        initData(savedInstanceState);
    }

    public ICyc getCyc() {
        if (null == cyc) {
            synchronized (BaseFragment.class) {
                if (null == cyc) {
                    cyc = CycImpl.create(context);
                }
            }
        }
        return cyc;
    }

    @Override
    public void onStart() {
        super.onStart();
            if (useRxBus()) {
                rxBus = RxBus.getIntanceBus();
                initRxBus();
        }
    }

    private void initRxBus() {
        rxBus = RxBus.getIntanceBus();
        registerRxBus(IRxMsg.class, new Consumer<IRxMsg>() {
            @Override
            public void accept(@NonNull IRxMsg iRxMsg) throws Exception {
                p.registerRxBus(iRxMsg);
            }
        });
    }

    private <T> void registerRxBus(Class<T> eventType, Consumer<T> action) {
        Disposable disposable = rxBus.doSubscribe(eventType, action, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e("NewsMainPresenter" + throwable.toString());
            }
        });
        rxBus.addSubscription(this, disposable);
    }

    @Override
    public boolean useRxBus() {
        return false;
    }

    public RxPermissions getRxPermissions() {
        if (null == rxPermissions) {
            synchronized (BaseFragment.class) {
                if (null == rxPermissions) {
                    rxPermissions = new RxPermissions(getActivity());
                }
            }
        }
        return rxPermissions;
    }

    protected P getCorrespondingP() {
        if (p == null) {
            synchronized (BaseFragment.class) {
                if (null == p) {
                    p = getP();
                }
                if (null != p){
                    p.bindView(this);
                }
            }
        }
        return p;
    }

    @Override
    protected void onDestoryLazy() {
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

}
