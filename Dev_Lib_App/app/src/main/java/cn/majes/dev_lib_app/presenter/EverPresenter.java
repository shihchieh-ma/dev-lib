package cn.majes.dev_lib_app.presenter;

import cn.majes.dev_lib_app.entity.EverEntity;
import cn.majes.dev_lib_app.net.Api;
import cn.majes.dev_lib_app.view.fragment.EverFragment;
import dev.majes.base.mvp.BasePresenter;
import dev.majes.base.net.ApiSubscriber;
import dev.majes.base.net.NetError;
import dev.majes.base.net.XApi;

/**
 * @author majes
 * @date 12/15/17.
 */

public class EverPresenter extends BasePresenter<EverFragment> {

    public void loadData() {
        Api.getOtherService().getNotifyArticle("gallery_story", String.valueOf(System.currentTimeMillis() / 1000 - 10000))
                .compose(XApi.<EverEntity>getScheduler())
                .compose(getV().<EverEntity>bindToLifecycle())
                .subscribe(new ApiSubscriber<EverEntity>() {
                    @Override
                    protected void onFail(NetError error) {

                        getV().showError(error.getMessage());
                    }

                    @Override
                    public void onNext(EverEntity entity) {

                        getV().showData(entity.getData());
                    }
                });
    }
}
