package dev.majes.base.mvp;

import dev.majes.base.rxbus.IRxMsg;
import io.reactivex.functions.Consumer;

/**
 * @author majes
 * @date 12/11/17.
 */

public interface IPrensenter<V> {
    void bindView(V view);

    void unBindView();

}
