package cn.majes.dev_lib_app.presenter;

import cn.majes.dev_lib_app.entity.PicEntity;
import cn.majes.dev_lib_app.net.Api;
import cn.majes.dev_lib_app.view.fragment.PicAllFragment;
import dev.majes.base.mvp.BasePresenter;
import dev.majes.base.net.ApiSubscriber;
import dev.majes.base.net.NetError;
import dev.majes.base.net.XApi;

/**
 * @author majes
 * @date 12/17/17.
 */

public class PicPresenter extends BasePresenter<PicAllFragment> {

    public void loadData() {

        Api.getOtherService().getPhotoArticle("组图", String.valueOf(System.currentTimeMillis() / 1000 - 10000))
                .compose(XApi.<PicEntity>getApiTransformer())
                .compose(XApi.<PicEntity>getScheduler())
                .compose(getV().<PicEntity>bindToLifecycle())
                .subscribe(new ApiSubscriber<PicEntity>() {
                    @Override
                    protected void onFail(NetError error) {

                        getV().showError(error.getMessage());
                    }

                    @Override
                    public void onNext(PicEntity entity) {

                        getV().showData(entity.getData());
                    }
                });
    }
}
