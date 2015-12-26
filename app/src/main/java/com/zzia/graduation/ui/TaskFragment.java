package com.zzia.graduation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzia.graduation.adapters.TaskFragmentPagerAdapter;
import com.zzia.graduation.welog.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.zzia.graduation.welog.R.id.task_layout_minetask_line;

/**
 * Author: yunyujing
 * Date: 2015/12/1
 */
public class TaskFragment extends Fragment implements View.OnClickListener{

    private TextView allTaskText;
    private TextView mineTaskText;
    private TextView allTaskTextLine;
    private TextView mineTaskTextLine;
    private TaskFragmentPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragmentArrayList;
    private AllTaskFragment allTaskFragment;
    private MineTaskFragment mineTaskFragment;
    private ViewPager pager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }


    private void initView(View view) {
        allTaskText= (TextView) view.findViewById(R.id.task_layout_alltask);
        allTaskText.setOnClickListener(this);
        mineTaskText= (TextView) view.findViewById(R.id.task_layout_minetask);
        mineTaskText.setOnClickListener(this);
        allTaskTextLine= (TextView) view.findViewById(R.id.task_layout_alltask_line);
        mineTaskTextLine = (TextView) view.findViewById(R.id.task_layout_minetask_line);
        allTaskFragment=new AllTaskFragment();
        mineTaskFragment=new MineTaskFragment();
        fragmentArrayList=new ArrayList<>();
        fragmentArrayList.add(allTaskFragment);
        fragmentArrayList.add(mineTaskFragment);
        pagerAdapter=new TaskFragmentPagerAdapter(getChildFragmentManager(),fragmentArrayList);
        pager= (ViewPager) view.findViewById(R.id.task_layout_viewpager);
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setAllTaskTextSelected();
                        break;
                    case 1:
                        setMineTaskTextSelected();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setAllTaskTextSelected() {
        allTaskText.setTextColor(getResources().getColor(R.color.colorPrimary));
        allTaskTextLine.setBackgroundResource(R.color.colorPrimary);
        mineTaskText.setTextColor(getResources().getColor(R.color.dark_text_gray));
        mineTaskTextLine.setBackgroundResource(R.color.line_gray);
    }

    private void setMineTaskTextSelected(){
        allTaskText.setTextColor(getResources().getColor(R.color.dark_text_gray));
        allTaskTextLine.setBackgroundResource(R.color.line_gray);
        mineTaskText.setTextColor(getResources().getColor(R.color.colorPrimary));
        mineTaskTextLine.setBackgroundResource(R.color.colorPrimary);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.task_layout_alltask:
                pager.setCurrentItem(0,true);
                break;
            case R.id.task_layout_minetask:
                pager.setCurrentItem(1,true);
                break;
        }
    }
}
