package cn.majes.dev_lib_app.view.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.Permission;

import java.util.ArrayList;
import java.util.List;

import cn.majes.dev_lib_app.R;
import cn.majes.dev_lib_app.entity.Tab;
import cn.majes.dev_lib_app.entity.TestRxBusMsg;
import cn.majes.dev_lib_app.view.fragment.GankIOFragment;
import cn.majes.dev_lib_app.view.fragment.HotFragment;
import cn.majes.dev_lib_app.view.fragment.NewsFragment;
import cn.majes.dev_lib_app.view.fragment.EverFragment;
import cn.majes.dev_lib_app.view.fragment.StoryFragment;
import dev.majes.base.log.Log;
import dev.majes.base.mvp.BaseActivity;
import dev.majes.base.rxbus.RxBus;
import dev.majes.base.utils.DPXUtils;
import io.reactivex.functions.Consumer;

/**
 * @author majes
 * @date 12/13/17.
 */

public class MainActivity extends BaseActivity {

    private LayoutInflater mInflater;
    private List<Tab> mTabs;
    private FragmentTabHost mTableHost;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTabs = new ArrayList<>(5);

        Tab tableHome = new Tab(R.string.table_gank, R.drawable.select_icon_home, GankIOFragment.class);
        Tab tableIdea = new Tab(R.string.table_all, R.drawable.select_pic, HotFragment.class);
        Tab tableSuperMarket = new Tab(R.string.table_know, R.drawable.select_icon_knowt, NewsFragment.class);
        Tab tableNotify = new Tab(R.string.table_story, R.drawable.select_icon_story, EverFragment.class);
        Tab tableUser = new Tab(R.string.table_camera, R.drawable.select_icon_camera, StoryFragment.class);
        mTabs.add(tableHome);
        mTabs.add(tableIdea);
        mTabs.add(tableSuperMarket);
        mTabs.add(tableNotify);
        mTabs.add(tableUser);
        mInflater = LayoutInflater.from(this);
        mTableHost = (FragmentTabHost) this.findViewById(R.id.tablehost_);
        mTableHost.setup(this, getSupportFragmentManager(), R.id.relatecontent);
        mTableHost.getTabWidget().setDividerDrawable(null);
        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTableHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTableHost.addTab(tabSpec, tab.getFragment(), null);
        }
        //        这个方法来知悉哪个权限被拒
        getRxPermissions().requestEach(Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        Log.e(permission.granted + "---" + permission.name);
                    }
                });
        getRxPermissions().request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        Log.e("检测到被授予权限，功能将可用。");
                    } else {
                        Log.e("检测到未被授予权限，功能将不可使用。");
                    }
                });

        RxBus.getIntanceBus().post(new TestRxBusMsg("this is test msg form main activity."));
    }

    private View buildIndicator(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                DPXUtils.dip2px(this, 22f),
                DPXUtils.dip2px(this, 22f)
        );
        img.setLayoutParams(ll);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);
        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());
        return view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
