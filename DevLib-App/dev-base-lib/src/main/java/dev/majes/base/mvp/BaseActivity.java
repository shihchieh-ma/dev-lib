package dev.majes.base.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

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

public abstract class BaseActivity<P extends IPrensenter> extends RxAppCompatActivity
        implements IView<P> {

    private ICyc cyc;
    private P p;
    protected Activity context;
    private RxPermissions rxPermissions;
    private RxBus rxBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(getLayoutId());
        bindUI(null);
        initData(savedInstanceState);
    }

    @Override
    public void bindUI(View rootView) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onStart() {
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

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCyc().resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getCyc().pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getCorrespondingP() != null) {
            getCorrespondingP().unBindView();
        }
        getCyc().destory();
        p = null;
        cyc = null;
        if (null != rxBus) {
            rxBus.unSubscribe(this);
            rxBus = null;
        }
        if (null != rxPermissions) {
            rxPermissions = null;
        }
    }

    public RxPermissions getRxPermissions() {
        if (null == rxPermissions) {
            synchronized (BaseFragment.class) {
                if (null == rxPermissions) {
                    rxPermissions = new RxPermissions(this);
                }
            }
        }
        return rxPermissions;
    }

    protected ICyc getCyc() {
        if (null == cyc) {
            synchronized (BaseFragment.class) {
                if (null == cyc) {
                    cyc = CycImpl.create(this);
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
            }
        }
        p.bindView(this);
        return p;
    }

}
