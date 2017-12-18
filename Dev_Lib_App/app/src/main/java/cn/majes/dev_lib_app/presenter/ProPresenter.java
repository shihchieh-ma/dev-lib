package cn.majes.dev_lib_app.presenter;

import cn.majes.dev_lib_app.entity.ProEntity;
import cn.majes.dev_lib_app.net.Api;
import cn.majes.dev_lib_app.view.fragment.ProFragment;
import dev.majes.base.mvp.BasePresenter;
import dev.majes.base.net.ApiSubscriber;
import dev.majes.base.net.NetError;
import dev.majes.base.net.XApi;

/**
 * @author majes
 * @date 12/17/17.
 */

public class ProPresenter extends BasePresenter<ProFragment> {

    public void loadData() {

        Api.getOtherService().getOldArticle("gallery_old_picture", String.valueOf(System.currentTimeMillis() / 1000 - 10000))
                .compose(XApi.<ProEntity>getApiTransformer())
                .compose(XApi.<ProEntity>getScheduler())
                .compose(getV().<ProEntity>bindToLifecycle())
                .subscribe(new ApiSubscriber<ProEntity>() {
                    @Override
                    protected void onFail(NetError error) {

                        getV().showError(error.getMessage());
                    }

                    @Override
                    public void onNext(ProEntity entity) {

                        getV().showData(entity.getData());
                    }
                });
    }
}
