package dev.majes.app.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxFragment;

import dev.majes.app.R;
import dev.majes.app.commen.Commen;
import dev.majes.base.mvp.BaseFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class NewsFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RxFragment firstPageFragment,secondPageFragment;
    private List<RxFragment> fragments;

    @Override
    public void bindUI(View rootView) {
        tabLayout = rootView.findViewById(R.id.tablayout);
        viewPager = rootView.findViewById(R.id.viewpage);
        fragments = new ArrayList<>();
        firstPageFragment = new FristPageFragment();
        secondPageFragment = new SecondPageFragment();
        fragments.add(firstPageFragment);
        fragments.add(secondPageFragment);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return Commen.TITLELIST[position];
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_fragment;
    }

}
