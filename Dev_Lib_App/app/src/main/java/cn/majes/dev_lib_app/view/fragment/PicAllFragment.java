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
import cn.majes.dev_lib_app.adapter.OnRecyclerViewItemClickListener;
import cn.majes.dev_lib_app.adapter.PicFragmentAdapter;
import cn.majes.dev_lib_app.entity.PicEntity;
import cn.majes.dev_lib_app.entity.TestRxBusMsg;
import cn.majes.dev_lib_app.presenter.PicPresenter;
import dev.majes.base.log.Log;
import dev.majes.base.mvp.BaseFragment;
import dev.majes.base.utils.WindowUtils;
import dev.majes.utils.WaitShowUtils;
import dev.majes.widget.windowmanager.WindowManagerCtroller;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author majes
 * @date 12/13/17.
 */

public class PicAllFragment extends BaseFragment<PicPresenter> implements OnRefreshListener,
        OnRefreshLoadmoreListener ,OnRecyclerViewItemClickListener{

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private LinearLayoutManager mLayoutManager;
    private int page = 1;
    private List<PicEntity.DataBean> list;
    private PicFragmentAdapter picFragmentAdapter;

    @Override
    public void bindUI(View rootView) {
        recyclerView = rootView.findViewById(R.id.recycleview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        refreshLayout = (RefreshLayout) rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        list = new ArrayList<>();
        picFragmentAdapter = new PicFragmentAdapter(list);
        picFragmentAdapter.setOnItemClick(this);
        recyclerView.setAdapter(picFragmentAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    public void showError(String error) {
        Log.e(error);
    }

    public void showData(List<PicEntity.DataBean> list) {
        WaitShowUtils.release();
        this.list.addAll(list);
        page++;
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefresh();
        picFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        WaitShowUtils.getInstance(getActivity()).show();
        getCorrespondingP().loadData();
        registerRxBus(TestRxBusMsg.class, new Consumer<TestRxBusMsg>() {
            @Override
            public void accept(@NonNull TestRxBusMsg iRxMsg) throws Exception {
                Log.e((iRxMsg).getString());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment;
    }

    @Override
    public PicPresenter getP() {
        return new PicPresenter();
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
    public void onItemClick(View view, Object data) {
        WaitShowUtils.release();
        if (!WindowUtils.checkFloatWindowPermission(getActivity())){
            WindowUtils.showDialogTipUserRequestPermission(getActivity());
        }else {
            WindowManagerCtroller.getWindowManagerCtroller(getActivity()).createWindowView();
        }
    }
}
