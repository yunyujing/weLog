package com.zzia.graduation.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by yunyujing on 16/5/21.
 */
public class PlanFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentArrayList;
    private FragmentManager fragmentManager;

    public PlanFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        this.fragmentManager = fm;
        this.fragmentArrayList = fragmentArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }


    //    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return "由我提交";
//            case 1:
//                return "由我审核";
//            default:
//                return null;
//        }
//    }
}