package cn.majes.dev_lib_app.presenter;

import cn.majes.dev_lib_app.entity.HotEntity;
import cn.majes.dev_lib_app.net.Api;
import cn.majes.dev_lib_app.view.fragment.HotFragment;
import dev.majes.base.mvp.BasePresenter;
import dev.majes.base.net.ApiSubscriber;
import dev.majes.base.net.NetError;
import dev.majes.base.net.XApi;

/**
 * @author majes
 * @date 12/15/17.
 */

public class HotPresenter extends BasePresenter<HotFragment> {

    private static final int PAGE_SIZE = 8;


    public void loadData(String type, int page) {
        Api.getApiService().getGankHot(type, PAGE_SIZE, page)
                .compose(XApi.<HotEntity>getScheduler())
                .compose(getV().<HotEntity>bindToLifecycle())
                .subscribe(new ApiSubscriber<HotEntity>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error.getMessage());
                    }

                    @Override
                    public void onNext(HotEntity entity) {
                        getV().showData(entity.getResults());
                    }
                });
    }
}
