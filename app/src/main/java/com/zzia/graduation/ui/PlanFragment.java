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
 * Created by yunyujing on 16/5/21.
 */
public class PlanFragment extends FragmentActivity implements View.OnClickListener {

    private TextView allPlanText;
    private TextView minePlanText;
    private TextView allPlanTextLine;
    private TextView minePlanTextLine;
    private ViewPager pager;
    private PlanFragmentPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragmentArrayList;

    private CreatePlanFragment createPlanFragment;
    private CheckPlanFragment checkPlanFragment;
//    private Fragment[] fragments;
//    private String checkTag = "check";
//    private String createTag = "create";
    private MyActionBar myActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_layout);
        initView();
    }

    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.plan_layout,null);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        initView(view);
//    }
//

    private void initView() {
        myActionBar = (MyActionBar) findViewById(R.id.plan_list_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));

        allPlanText = (TextView) findViewById(R.id.plan_layout_allplan);
        allPlanText.setOnClickListener(this);
        minePlanText = (TextView) findViewById(R.id.plan_layout_mineplan);
        minePlanText.setOnClickListener(this);
        allPlanTextLine = (TextView) findViewById(R.id.plan_layout_allplan_line);
        minePlanTextLine = (TextView) findViewById(R.id.plan_layout_mineplan_line);
//        fragments = new Fragment[2];
//        fragments[0] = new CreatePlanFragment();
//        fragments[1] = new CheckPlanFragment();
//        setAllPlanTextSelected();

        fragmentArrayList=new ArrayList<>();
        createPlanFragment=new CreatePlanFragment();
        checkPlanFragment=new CheckPlanFragment();
        fragmentArrayList.add(createPlanFragment);
        fragmentArrayList.add(checkPlanFragment);
        pagerAdapter=new PlanFragmentPagerAdapter(getSupportFragmentManager(),fragmentArrayList);
        pager= (ViewPager)findViewById(R.id.plan_layout_viewpager);
        pager.setAdapter(pagerAdapter);
//        pager.setCurrentItem(0);
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
//        try {
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            if (!fragments[0].isAdded() && !createTag.equals(fragments[0].getTag())) {
//                fragmentTransaction.add(R.id.content_plan_frame, fragments[0], createTag);
//            } else if (!fragments[0].isVisible()) {
//                fragmentTransaction.show(fragments[0]);
//            }
//            if (fragments[1].isAdded() && fragments[1].isVisible()) {
//                fragmentTransaction.hide(fragments[1]);
//            }
//            fragmentTransaction.commitAllowingStateLoss();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }

    }

    private void setMinePlanTextSelected() {
        allPlanText.setTextColor(getResources().getColor(R.color.dark_text_gray));
        allPlanTextLine.setBackgroundResource(R.color.line_gray);
        minePlanText.setTextColor(getResources().getColor(R.color.colorPrimary));
        minePlanTextLine.setBackgroundResource(R.color.colorPrimary);

//        try {
//
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            if (!fragments[1].isAdded() && !checkTag.equals(fragments[1].getTag())) {
//                fragmentTransaction.add(R.id.content_plan_frame, fragments[1], checkTag);
//            } else if (!fragments[1].isVisible()) {
//                fragmentTransaction.show(fragments[1]);
//            }
//            if (fragments[0].isAdded() && fragments[0].isVisible()) {
//                fragmentTransaction.hide(fragments[0]);
//            }
//            fragmentTransaction.commitAllowingStateLoss();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plan_layout_allplan:
//                setAllPlanTextSelected();
                pager.setCurrentItem(0,true);
                break;
            case R.id.plan_layout_mineplan:
//                setMinePlanTextSelected();
                pager.setCurrentItem(1,true);
                break;
        }
    }

}
