package cn.majes.dev_lib_app.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.majes.dev_lib_app.R;
import cn.majes.dev_lib_app.adapter.HomeFragmentViewPagerAdapter;
import dev.majes.base.mvp.BaseFragment;

/**
 * @author majes
 * @date 12/13/17.
 */

public class GankIOFragment extends BaseFragment {

    private FragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> fragmentList;
    private static final String[] titles = {"博客", "美女"};
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void bindUI(View rootView) {
        tabLayout = rootView.findViewById(R.id.tabLayout);
        viewPager = rootView.findViewById(R.id.viewpager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (null == fragmentList) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.clear();
        fragmentList.add(new NewsFragment());
        fragmentList.add(new HotFragment());
        if (null == fragmentPagerAdapter) {
            fragmentPagerAdapter = new HomeFragmentViewPagerAdapter(
                    getChildFragmentManager(),
                    fragmentList, titles);
        }
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(titles.length);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


}
