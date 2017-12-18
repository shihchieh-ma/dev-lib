package cn.majes.dev_lib_app.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import cn.majes.dev_lib_app.R;
import cn.majes.dev_lib_app.entity.TestRxBusMsg2;
import cn.majes.dev_lib_app.view.activity.WebViewActivity;
import cn.majes.dev_lib_app.adapter.OnRecyclerViewItemClickListener;
import cn.majes.dev_lib_app.adapter.NewsFragmentAdapter;
import cn.majes.dev_lib_app.entity.NewsEntity;
import cn.majes.dev_lib_app.presenter.NewsPresenter;
import dev.majes.base.log.Log;
import dev.majes.base.mvp.BaseLazyFragment;
import dev.majes.base.router.Router;
import dev.majes.utils.WaitShowUtils;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author majes
 * @date 12/15/17.
 */

public class NewsFragment extends BaseLazyFragment<NewsPresenter> implements OnRefreshListener,
        OnRefreshLoadmoreListener, OnRecyclerViewItemClickListener {

    private RecyclerView recycleview;
    private LinearLayoutManager mLayoutManager;
    private NewsFragmentAdapter newsFragmentAdapter;
    private RefreshLayout refreshLayout;
    private int page = 1;
    private List<NewsEntity.ResultsBean> list;

    @Override
    public void bindUI(View rootView) {
        Log.e(rootView);
        recycleview = rootView.findViewById(R.id.recycleview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        refreshLayout = (RefreshLayout) rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        list = new ArrayList<>();
        //创建并设置Adapter
        newsFragmentAdapter = new NewsFragmentAdapter(list);
        newsFragmentAdapter.setOnItemClick(this);
        recycleview.setAdapter(newsFragmentAdapter);
        recycleview.setLayoutManager(mLayoutManager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        WaitShowUtils.getInstance(getActivity()).show();
        getCorrespondingP().loadData("all", page);
        registerRxBus(TestRxBusMsg2.class, new Consumer<TestRxBusMsg2>() {
            @Override
            public void accept(@NonNull TestRxBusMsg2 iRxMsg) throws Exception {
                Log.e(iRxMsg.getString());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment;
    }

    @Override
    public NewsPresenter getP() {
        return new NewsPresenter();
    }


    public void showError(String error) {
        Log.e(error);
    }

    public void showData(List<NewsEntity.ResultsBean> list) {
        WaitShowUtils.release();
        this.list.addAll(list);
        page++;
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefresh();
        newsFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        //refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        getCorrespondingP().loadData("all", page);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        // refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
        getCorrespondingP().loadData("all", page);
    }

    @Override
    public void onItemClick(View view, Object data) {
        Router.newIntent(getActivity())
                .to(WebViewActivity.class)
                .putString(WebViewActivity.KEY_URL, (String) data)
                .launch();
    }
}
