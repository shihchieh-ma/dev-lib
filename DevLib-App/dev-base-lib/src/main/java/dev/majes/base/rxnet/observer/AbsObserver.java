package dev.majes.base.rxnet.observer;


import dev.majes.base.log.Log;
import dev.majes.base.rxnet.exception.ApiException;
import io.reactivex.observers.DisposableObserver;

/**
 * Abs Observer
 */
abstract class AbsObserver<T> extends DisposableObserver<T> {

    AbsObserver() {
    }

    @Override
    public void onError(Throwable e) {
        //print error log
        if (e instanceof ApiException) {
            e.printStackTrace();
        }
        Log.e(e.getMessage());
    }
}
