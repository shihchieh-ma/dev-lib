package dev.majes.base.mvp;

import java.lang.ref.WeakReference;

import dev.majes.base.log.Log;
import dev.majes.base.rxbus.IRxMsg;

/**
 * @author majes
 * @date 12/11/17.
 */

public class BasePresenter<V extends IView> implements IPrensenter<V> {
    private V v;

    @Override
    public void bindView(V view) {
        v = new WeakReference<V>(view).get();
    }

    @Override
    public void unBindView() {
        if (v != null) {
            v = null;
        }
    }


    protected V getV() {
        if (v == null) {
            throw new IllegalStateException("v is null");
        }
        return v;
    }
}
