package com.zzia.graduation.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yunyujing on 16/5/15.
 */
public class MySpinnerAdapter extends BaseAdapter{
    private Context context;
    private ArrayList list;
    public MySpinnerAdapter(Context context, ArrayList<String> depts) {

        this.context=context;
        this.list=depts;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MySpinnerAdapterViewHolder holder=null;
        if(convertView==null){
//            convertView= LayoutInflater.from(context).inflate()
//            holder=new MySpinnerAdapterViewHolder();
        }else {

        }
        return convertView;
    }

    class MySpinnerAdapterViewHolder{
        private TextView textView;

        public MySpinnerAdapterViewHolder(View itemView) {
        }
    }
}
