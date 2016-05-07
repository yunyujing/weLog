package com.zzia.graduation.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * 点击上传图片的适配器
 * Created by yunyujing on 16/5/4.
 */
public class CheckLargeImageGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BaseBean> list;

    public CheckLargeImageGridAdapter(Context context, ArrayList<BaseBean> list) {

        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public BaseBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CheckLargeImageGridViewHOlder checkLargeImageGridViewHOlder = null;
        if (convertView == null) {
            checkLargeImageGridViewHOlder = new CheckLargeImageGridViewHOlder();
            convertView = LayoutInflater.from(context).inflate(R.layout.upload_image_grid_item, null);
            checkLargeImageGridViewHOlder.image = (SimpleDraweeView) convertView.findViewById(R.id.upload_image_gird_item_simpleview);
            convertView.setTag(checkLargeImageGridViewHOlder);
        } else {
            convertView.getTag();
        }
        checkLargeImageGridViewHOlder.update(getItem(position),position);
        return convertView;
    }

    class CheckLargeImageGridViewHOlder implements View.OnClickListener {

        private SimpleDraweeView image;
        private BaseBean baseBean;
        private int position;

        public void update(BaseBean item,int position) {

            this.baseBean=  item;
            this.position=position;
            image.setOnClickListener(this);
            image.setImageURI(Uri.parse(baseBean.getStr("url")));

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
