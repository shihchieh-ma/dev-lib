package cn.majes.dev_lib_app.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import cn.majes.dev_lib_app.R;
import cn.majes.dev_lib_app.adapter.ProFragmentAdapter;
import cn.majes.dev_lib_app.entity.ProEntity;
import cn.majes.dev_lib_app.entity.TestRxBusMsg;
import cn.majes.dev_lib_app.presenter.ProPresenter;
import dev.majes.base.log.Log;
import dev.majes.base.mvp.BaseFragment;
import dev.majes.base.rxbus.RxBus;
import dev.majes.utils.WaitShowUtils;

/**
 * @author majes
 * @date 12/13/17.
 */

public class ProFragment extends BaseFragment<ProPresenter> implements OnRefreshListener,
        OnRefreshLoadmoreListener {

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private LinearLayoutManager mLayoutManager;
    private int page = 1;
    private List<ProEntity.DataBean> list;
    private ProFragmentAdapter ideaFragmentAdapter;

    @Override
    public void bindUI(View rootView) {
        recyclerView = rootView.findViewById(R.id.recycleview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        refreshLayout = (RefreshLayout) rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        list = new ArrayList<>();
        ideaFragmentAdapter = new ProFragmentAdapter(list);
        recyclerView.setAdapter(ideaFragmentAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    public void showError(String error) {
        Log.e(error);
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    public void showData(List<ProEntity.DataBean> list) {
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
        RxBus.getIntanceBus().post(new TestRxBusMsg("this is test msg form market fragment."));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment;
    }

    @Override
    public ProPresenter getP() {
        return new ProPresenter();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getCorrespondingP().loadData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getCorrespondingP().loadData();
    }
}
