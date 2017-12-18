package dev.majes.base.mvp;


/**
 * @author majes
 * @date 12/11/17.
 */

public interface IPrensenter<V> {
    void bindView(V view);

    void unBindView();

}
