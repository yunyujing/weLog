package com.zzia.graduation.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Author: yunyujing
 * Date: 2015/12/17
 */
public class TaskFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentArrayList;
    private FragmentManager fragmentManager;
    public TaskFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        this.fragmentManager=fm;
        this.fragmentArrayList=fragmentArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        switch (position){
            case 0:
                return "全部任务";
            case 1:
                return "我的任务";
            default:
                return null;
        }
    }

}
