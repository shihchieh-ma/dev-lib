package dev.majes.base.rxnet.observer;


import dev.majes.base.rxnet.listener.DownloadCallBack;
import dev.majes.base.rxnet.request.DownloadRequest;
import dev.majes.base.utils.RxUtil;

/**
 * Observer with Download CallBack
 * Created by D on 2017/10/26.
 */
public class DownloadObserver extends AbsObserver<DownloadRequest.DownloadModel> {
    private DownloadCallBack callback;

    public DownloadObserver(DownloadCallBack callback) {
        this.callback = callback;
    }

    @Override
    public void onNext(DownloadRequest.DownloadModel m) {
        RxUtil.printThread("RxNet_theard downloadOnNext: ");
        callback.onProgress(m.downloadSize, m.totalSize);
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        callback.onError(e);
    }

    @Override
    public void onComplete() {
        callback.onComplete();
    }
}
