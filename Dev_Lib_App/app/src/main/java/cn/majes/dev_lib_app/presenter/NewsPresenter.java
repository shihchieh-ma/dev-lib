package cn.majes.dev_lib_app.presenter;

import cn.majes.dev_lib_app.entity.NewsEntity;
import cn.majes.dev_lib_app.net.Api;
import cn.majes.dev_lib_app.view.fragment.NewsFragment;
import dev.majes.base.mvp.BasePresenter;
import dev.majes.base.net.ApiSubscriber;
import dev.majes.base.net.NetError;
import dev.majes.base.net.XApi;

/**
 * @author majes
 * @date 12/15/17.
 */

public class NewsPresenter extends BasePresenter<NewsFragment> {

    private static final int PAGE_SIZE = 10;

    public void loadData(String type, int page) {
        Api.getApiService().getGankNews(type, PAGE_SIZE, page)
                .compose(XApi.<NewsEntity>getScheduler())
                .compose(getV().<NewsEntity>bindToLifecycle())
                .subscribe(new ApiSubscriber<NewsEntity>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error.getMessage());
                    }

                    @Override
                    public void onNext(NewsEntity entity) {
                        getV().showData(entity.getResults());
                    }
                });
    }
}
