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
 * 上传图片适配器
 * Created by yunyujing on 16/5/6.
 */
public class UploadImageGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Uri> list;

    public UploadImageGridAdapter(Context context, ArrayList list) {

        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Uri getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UploadImageGridViewHOlder uploadImageGridViewHOlder = null;
        if (convertView == null) {
            uploadImageGridViewHOlder = new UploadImageGridViewHOlder();
            convertView = LayoutInflater.from(context).inflate(R.layout.upload_image_grid_item, null);
            uploadImageGridViewHOlder.simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.upload_image_gird_item_simpleview);
            convertView.setTag(uploadImageGridViewHOlder);
        } else {
            uploadImageGridViewHOlder= (UploadImageGridViewHOlder) convertView.getTag();
        }

        uploadImageGridViewHOlder.update(getItem(position), position);

        return convertView;
    }

    public class UploadImageGridViewHOlder {

        private SimpleDraweeView simpleDraweeView;
        private Uri uri;
        private int position;

        public void update(Uri item, int position) {

            this.uri = item;
            this.position = position;
            simpleDraweeView.setImageURI(uri);

        }
    }


}
