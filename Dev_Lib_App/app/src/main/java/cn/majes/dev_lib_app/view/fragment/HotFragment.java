package cn.majes.dev_lib_app.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import cn.majes.dev_lib_app.R;
import cn.majes.dev_lib_app.adapter.HotFragmentAdapter;
import cn.majes.dev_lib_app.adapter.OnRecyclerViewItemClickListener;
import cn.majes.dev_lib_app.entity.HotEntity;
import cn.majes.dev_lib_app.entity.TestRxBusMsg2;
import cn.majes.dev_lib_app.presenter.HotPresenter;
import cn.majes.dev_lib_app.view.activity.BigPicActivity;
import dev.majes.base.log.Log;
import dev.majes.base.mvp.BaseLazyFragment;
import dev.majes.base.router.Router;
import dev.majes.base.rxbus.RxBus;
import dev.majes.utils.WaitShowUtils;

/**
 * @author majes
 * @date 12/15/17.
 */

public class HotFragment extends BaseLazyFragment<HotPresenter> implements OnRefreshListener,
        OnRefreshLoadmoreListener, OnRecyclerViewItemClickListener {

    private RecyclerView hot_recycleview;
    private StaggeredGridLayoutManager mLayoutManager;
    private HotFragmentAdapter hotFragmentAdapter;
    private RefreshLayout refreshLayout;
    private int page = 1;
    private List<HotEntity.ResultsBean> list;

    @Override
    public void bindUI(View rootView) {
        Log.e(rootView);
        hot_recycleview = rootView.findViewById(R.id.recycleview);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        refreshLayout = (RefreshLayout) rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        list = new ArrayList();
        hotFragmentAdapter = new HotFragmentAdapter(list);
        hotFragmentAdapter.setOnItemClick(this);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        hot_recycleview.setAdapter(hotFragmentAdapter);
        hot_recycleview.setLayoutManager(mLayoutManager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        WaitShowUtils.getInstance(getActivity()).show();
        RxBus.getIntanceBus().post(new TestRxBusMsg2("this is test msg form hot fragment."));
        getCorrespondingP().loadData("福利", page);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment;
    }


    public void showError(String error) {
        Log.e(error);
    }

    public void showData(List<HotEntity.ResultsBean> list) {
        WaitShowUtils.release();
        this.list.addAll(list);
        page++;
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefresh();
        hotFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public HotPresenter getP() {
        return new HotPresenter();
    }

    @Override
    public void onItemClick(View view, Object data) {
        Router.newIntent(getActivity())
                .to(BigPicActivity.class)
//                .putByteArray(BigPicActivity.BIGPIC_KEY, (byte[]) data)
                .putString(BigPicActivity.BIGPIC_KEY, (String) data)
                .launch();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getCorrespondingP().loadData("福利", page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        getCorrespondingP().loadData("福利", page);
    }
}
