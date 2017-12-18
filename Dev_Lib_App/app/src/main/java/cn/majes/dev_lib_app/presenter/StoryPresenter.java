package cn.majes.dev_lib_app.presenter;

import cn.majes.dev_lib_app.entity.StoryEntity;
import cn.majes.dev_lib_app.net.Api;
import cn.majes.dev_lib_app.view.fragment.StoryFragment;
import dev.majes.base.mvp.BasePresenter;
import dev.majes.base.net.ApiSubscriber;
import dev.majes.base.net.NetError;
import dev.majes.base.net.XApi;

/**
 * @author majes
 * @date 12/15/17.
 */

public class StoryPresenter extends BasePresenter<StoryFragment> {

    public void loadData() {
        Api.getOtherService().getUserArticle("gallery_photograthy", String.valueOf(System.currentTimeMillis() / 1000 - 10000))
                .compose(XApi.<StoryEntity>getApiTransformer())
                .compose(XApi.<StoryEntity>getScheduler())
                .compose(getV().<StoryEntity>bindToLifecycle())
                .subscribe(new ApiSubscriber<StoryEntity>() {
                    @Override
                    protected void onFail(NetError error) {

                        getV().showError(error.getMessage());
                    }

                    @Override
                    public void onNext(StoryEntity entity) {

                        getV().showData(entity.getData());
                    }
                });
    }
}
