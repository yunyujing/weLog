package com.zzia.graduation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zzia.graduation.welog.R;

/**
 * Author: yunyujing
 * Date: 2015/12/1
 */
public class SearchFragment extends Fragment implements View.OnClickListener{
    private SearchView searchText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }

    private void initView(View view) {
        searchText= (SearchView) view.findViewById(R.id.search_layout_edit);
        searchText.onActionViewExpanded();//设置搜索框为展开状态
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//搜索内容提交
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//搜索内容改变
                return false;
            }
        });
//        searchText.setSuggestionsAdapter();
//        searchText.setAppSearchData();//搜索匹配数据
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
