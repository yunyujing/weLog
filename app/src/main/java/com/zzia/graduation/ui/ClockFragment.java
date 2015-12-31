package com.zzia.graduation.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Author: yunyujing
 * Date: 2015/12/31
 */
public class ClockFragment extends Fragment implements View.OnClickListener {

    private LayoutInflater inflater;
    private ViewPager pager;
    private ArrayList<View> viewArrayList;
    private View first;
    private View second;
    private LinearLayout index;
    private ImageView[] imageViews;
    private TextView notice;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        return inflater.inflate(R.layout.clock_layout, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        first = inflater.inflate(R.layout.clock_pager_first, null);
        second = inflater.inflate(R.layout.clock_pager_second, null);
        viewArrayList = new ArrayList<>();
        viewArrayList.add(first);
        viewArrayList.add(second);
        pager = (ViewPager) view.findViewById(R.id.clock_layout_pager);
        pager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewArrayList.get(position));
                return viewArrayList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewArrayList.get(position));
            }

            @Override
            public int getCount() {
                return viewArrayList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //初始化底部的按钮的布局
        index = (LinearLayout) view.findViewById(R.id.clock_layout_index);
        if (index != null) {//保证当前布局为空
            index.removeAllViews();
        }
        imageViews = new ImageView[viewArrayList.size()];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 20);
        for (int i = 0; i < viewArrayList.size(); i++) {
            imageViews[i] = new ImageView(getContext());
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.mipmap.clock_index_green);
            } else {
                imageViews[i].setBackgroundResource(R.mipmap.clock_index_gray);
            }
            imageViews[i].setLayoutParams(params);
            index.addView(imageViews[i]);
        }
        notice = (TextView) view.findViewById(R.id.clock_layout_range_notice);
        view.findViewById(R.id.clock_layout_click).setOnClickListener(this);

    }

    //页面滑动伴随的底部按钮的变化
    private void setIndex(int position) {
        for (int i = 0; i < viewArrayList.size(); i++) {
            if (position == i) {
                imageViews[i].setBackgroundResource(R.mipmap.clock_index_green);
            } else {
                imageViews[i].setBackgroundResource(R.mipmap.clock_index_gray);
            }
        }
    }


    @Override
    public void onClick(View v) {
        //点击打卡
    }
}
