package dev.majes.base.rxnet.func;


import dev.majes.base.rxnet.listener.AsyncCallBack;
import dev.majes.base.utils.RxUtil;
import io.reactivex.functions.Function;

/**
 * Map with AsyncCallBack
 */
public class MapFunc<T, R> implements Function<T, R> {
    private AsyncCallBack<T, R> callback;

    public MapFunc(AsyncCallBack<T, R> callback) {
        this.callback = callback;
    }

    @Override
    public R apply(T responseBody) throws Exception {
        RxUtil.printThread("RxNet_theard callback apply: ");
        return callback.apply(responseBody);
    }
}
