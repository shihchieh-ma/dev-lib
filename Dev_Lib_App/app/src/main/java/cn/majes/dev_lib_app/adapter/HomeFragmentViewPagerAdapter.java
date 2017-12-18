package cn.majes.dev_lib_app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * @author majes
 * @date 12/13/17.
 */

public class HomeFragmentViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles;

    public HomeFragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fragments
            , String[] titles) {
        super(fm);
        fragmentList = fragments;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > position) {
            return titles[position];
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
