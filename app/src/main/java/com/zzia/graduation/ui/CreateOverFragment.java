package com.zzia.graduation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzia.graduation.adapters.CreateOverFragmentAdapter;
import com.zzia.graduation.bean.CheckWork;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Created by yunyujing on 16/5/22.
 */
public class CreateOverFragment extends Fragment{
    private RecyclerView recyclerView;
    private ArrayList<BaseBean> plans;
    private CreateOverFragmentAdapter createPlanFragmentAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.plan_list_layout1, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initData();
        initView(view);
    }

    private void initData() {
        plans=new ArrayList<>();
        plans = CheckWork.getCreateOverTimeList(getActivity());
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.plan_list_layout1_recycle);
        recyclerView.setHasFixedSize(true);//有固定大小的recycleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DetailProjectActivity.MyListItemDecoration(30));
        createPlanFragmentAdapter =new CreateOverFragmentAdapter(getContext(),plans);
        recyclerView.setAdapter(createPlanFragmentAdapter);

    }
}
