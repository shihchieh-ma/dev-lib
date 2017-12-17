package dev.majes.base.mvp;

import android.os.Bundle;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;

import dev.majes.base.log.Log;
import dev.majes.base.rxbus.IRxMsg;
import dev.majes.base.rxbus.RxBus;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * @author majes
 * @date 12/17/17.
 */

public abstract class BaseLazyFragment<P extends IPrensenter>
        extends LazyFragment implements IView<P> {

    private ICyc iCyc;
    private P p;
    private RxBus rxBus;

    private RxPermissions rxPermissions;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindUI(getRealRootView());
        }
        initData(savedInstanceState);
    }

    protected <T> void registerRxBus(Class<T> eventType, Consumer<T> action) {
        rxBus = RxBus.getIntanceBus();
        Disposable disposable = rxBus.doSubscribe(eventType, action, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(throwable);
            }
        });
        rxBus.addSubscription(this, disposable);
    }



    protected ICyc getCyc() {
        if (null == iCyc) {
            synchronized (BaseFragment.class) {
                if (null == iCyc) {
                    iCyc = CycImpl.create(getActivity());
                }
            }
        }
        return iCyc;
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
    protected void onDestoryLazy() {
        super.onDestoryLazy();
        if (null != rxBus) {
            rxBus.unSubscribe(this);
            rxBus = null;
        }
        if (getCorrespondingP() != null) {
            getCorrespondingP().unBindView();
        }
        getCyc().destory();

        p = null;
        iCyc = null;
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
    public P getP() {
        return p;
    }
}
