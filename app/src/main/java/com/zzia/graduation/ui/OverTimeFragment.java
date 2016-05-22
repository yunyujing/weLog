package com.zzia.graduation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.zzia.graduation.adapters.PlanFragmentPagerAdapter;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Created by yunyujing on 16/5/22.
 */
public class OverTimeFragment extends FragmentActivity implements View.OnClickListener{
    private TextView allPlanText;
    private TextView minePlanText;
    private TextView allPlanTextLine;
    private TextView minePlanTextLine;
    private ViewPager pager;
    private PlanFragmentPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragmentArrayList;

    private CreateOverFragment createPlanFragment;
    private CheckOverFragment checkPlanFragment;

    private MyActionBar myActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_layout);
        initView();
    }


    private void initView() {
        myActionBar = (MyActionBar) findViewById(R.id.plan_list_layout_actionbar);
        myActionBar.setTitle("请假列表");
        myActionBar.setBackAction(new MyActionBar.BackAction(this));

        allPlanText = (TextView) findViewById(R.id.plan_layout_allplan);
        allPlanText.setOnClickListener(this);
        minePlanText = (TextView) findViewById(R.id.plan_layout_mineplan);
        minePlanText.setOnClickListener(this);
        allPlanTextLine = (TextView) findViewById(R.id.plan_layout_allplan_line);
        minePlanTextLine = (TextView) findViewById(R.id.plan_layout_mineplan_line);


        fragmentArrayList=new ArrayList<>();
        createPlanFragment=new CreateOverFragment();
        checkPlanFragment=new CheckOverFragment();
        fragmentArrayList.add(createPlanFragment);
        fragmentArrayList.add(checkPlanFragment);
        pagerAdapter=new PlanFragmentPagerAdapter(getSupportFragmentManager(),fragmentArrayList);
        pager= (ViewPager)findViewById(R.id.plan_layout_viewpager);
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setAllPlanTextSelected();
                        break;
                    case 1:
                        setMinePlanTextSelected();
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setAllPlanTextSelected() {
        allPlanText.setTextColor(getResources().getColor(R.color.colorPrimary));
        allPlanTextLine.setBackgroundResource(R.color.colorPrimary);
        minePlanText.setTextColor(getResources().getColor(R.color.dark_text_gray));
        minePlanTextLine.setBackgroundResource(R.color.line_gray);

    }

    private void setMinePlanTextSelected() {
        allPlanText.setTextColor(getResources().getColor(R.color.dark_text_gray));
        allPlanTextLine.setBackgroundResource(R.color.line_gray);
        minePlanText.setTextColor(getResources().getColor(R.color.colorPrimary));
        minePlanTextLine.setBackgroundResource(R.color.colorPrimary);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plan_layout_allplan:
                pager.setCurrentItem(0,true);
                break;
            case R.id.plan_layout_mineplan:
                pager.setCurrentItem(1,true);
                break;
        }
    }
}
