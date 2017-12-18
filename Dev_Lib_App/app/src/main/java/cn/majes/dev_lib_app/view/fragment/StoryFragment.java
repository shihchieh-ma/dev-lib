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
import cn.majes.dev_lib_app.adapter.StoryFragmentAdapter;
import cn.majes.dev_lib_app.entity.StoryEntity;
import cn.majes.dev_lib_app.presenter.StoryPresenter;
import dev.majes.base.log.Log;
import dev.majes.base.mvp.BaseFragment;
import dev.majes.utils.WaitShowUtils;

/**
 * @author majes
 * @date 12/13/17.
 */

public class StoryFragment extends BaseFragment<StoryPresenter> implements OnRefreshListener,
        OnRefreshLoadmoreListener {

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private LinearLayoutManager mLayoutManager;
    private int page = 1;
    private List<StoryEntity.DataBean> list;
    private StoryFragmentAdapter ideaFragmentAdapter;

    @Override
    public void bindUI(View rootView) {
        recyclerView = rootView.findViewById(R.id.recycleview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        refreshLayout = (RefreshLayout) rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        list = new ArrayList<>();
        ideaFragmentAdapter = new StoryFragmentAdapter(list);
        recyclerView.setAdapter(ideaFragmentAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    public void showError(String error) {
        Log.e(error);
    }

    public void showData(List<StoryEntity.DataBean> list) {
        WaitShowUtils.release();
        this.list.addAll(list);
        page++;
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefresh();
        ideaFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        WaitShowUtils.getInstance(getActivity()).show();
        getCorrespondingP().loadData();
    }


    @Override
    public StoryPresenter getP() {
        return new StoryPresenter();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getCorrespondingP().loadData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getCorrespondingP().loadData();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment;
    }


}
