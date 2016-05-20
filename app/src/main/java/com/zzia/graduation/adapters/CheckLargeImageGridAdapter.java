package com.zzia.graduation.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zzia.graduation.utils.Common;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * 点击查看大图的适配器
 * Created by yunyujing on 16/5/4.
 */
public class CheckLargeImageGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;

    public CheckLargeImageGridAdapter(Context context, ArrayList<String> list) {

        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CheckLargeImageGridViewHolder checkLargeImageGridViewHolder = null;
        if (convertView == null) {
            checkLargeImageGridViewHolder = new CheckLargeImageGridViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.upload_image_grid_item, null);
            checkLargeImageGridViewHolder.image = (SimpleDraweeView) convertView.findViewById(R.id.upload_image_gird_item_simpleview);
            convertView.setTag(checkLargeImageGridViewHolder);
        } else {
            checkLargeImageGridViewHolder= (CheckLargeImageGridViewHolder) convertView.getTag();
        }
        checkLargeImageGridViewHolder.update(getItem(position),position);
        return convertView;
    }

    class CheckLargeImageGridViewHolder implements View.OnClickListener {

        private SimpleDraweeView image;
        private String uri;
        private int position;

        public void update(String uri,int position) {

            this.uri= uri;
            this.position=position;
//            image.setOnClickListener(this);
            image.setImageURI(Uri.parse(uri));
            Log.e(Common.LOG_APP,uri);

        }

        @Override
        public void onClick(View v) {
            checkLargeImage();
        }

        /**
         * 预览大图
         */
        private void checkLargeImage() {

        }


    }
}
