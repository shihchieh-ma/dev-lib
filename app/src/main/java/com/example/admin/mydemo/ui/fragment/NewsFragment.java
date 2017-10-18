package com.example.admin.mydemo.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.admin.mydemo.R;
import com.example.admin.mydemo.ui.base.BaseFragment;
import com.example.admin.mydemo.commen.Commen;
import com.example.admin.mydemo.commen.ShowProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class NewsFragment extends BaseFragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment firstPageFragment,secondPageFragment,
            thirdPageFragment,fourPageFragment,fivePageFragment
            ,sixPageFragment,sevenPageFragment,eightPageFragment;
    private List<Fragment> fragments;
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        progressDialog = ShowProgressDialog.getInstance(context);
        return R.layout.home_fragment;
    }

    @Override
    protected void initViews() {

        tabLayout = bindView(R.id.tablayout);
        viewPager = bindView(R.id.viewpage);
    }


    @Override
    protected void initDatas() {
        progressDialog.show();
        fragments = new ArrayList<>();
        addFragment();

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

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        progressDialog.dismiss();
    }

    private void addFragment() {
        firstPageFragment = new FristPageFragment();
        secondPageFragment = new SecondPageFragment();
        thirdPageFragment = new ThirdPageFragment();
        fourPageFragment = new FourPageFragment();
        fivePageFragment = new FivePageFragment();
        sixPageFragment = new SixPageFragment();
        sevenPageFragment = new SevenPageFragment();
        eightPageFragment = new EightPageFragment();
        fragments.add(firstPageFragment);
        fragments.add(secondPageFragment);
        fragments.add(thirdPageFragment);
        fragments.add(fourPageFragment);
        fragments.add(fivePageFragment);
        fragments.add(sixPageFragment);
        fragments.add(sevenPageFragment);
        fragments.add(eightPageFragment);
    }
}
