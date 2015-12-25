package com.zzia.graduation.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzia.graduation.welog.R;

/**
 * Author: yunyujing
 * Date: 2015/12/18
 */
public class TipsLayout extends RelativeLayout {
    LayoutInflater inflater;

    View tipslayout_error_layout;
    TextView tipslayout_error_text;
    ImageView tipslayout_error_img;

    View tipslayout_loading_layout;
    TextView tipslayout_loading_text;

    public TipsLayout(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        setGravity(Gravity.CENTER);
    }

    public TipsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        setGravity(Gravity.CENTER);
    }

    public View getLoadingLayout() {
        if (tipslayout_loading_layout == null) {
            tipslayout_loading_layout = inflater.inflate(R.layout.tipslayout_loading_layout, null);
            tipslayout_loading_text = (TextView) tipslayout_loading_layout.findViewById(R.id.tipslayout_loading_text);
        }
        return tipslayout_loading_layout;
    }

    public View getErrorLayout() {
        if (tipslayout_error_layout == null) {
            tipslayout_error_layout = inflater.inflate(R.layout.tipslayout_error_layout, null);
            tipslayout_error_text = (TextView) tipslayout_error_layout.findViewById(R.id.tipslayout_error_text);
            tipslayout_error_img = (ImageView) tipslayout_error_layout.findViewById(R.id.tipslayout_error_img);
        }
        tipslayout_error_layout.setOnClickListener(null);
        return tipslayout_error_layout;
    }

    public View getErrorLayout(OnClickListener onClickListener) {
        getErrorLayout().setOnClickListener(onClickListener);
        return tipslayout_error_layout;
    }


    /**
     * 显示错误提示
     *
     * @param text
     * @param drawableId
     */
    public void showErrorTips(int text, int drawableId) {
        showErrorTips(text, drawableId, null);
    }

    /**
     * 显示错误提示
     *
     * @param text
     * @param drawableId
     */
    public void showErrorTips(CharSequence text, int drawableId) {
        showErrorTips(text, drawableId, null);
    }

    /**
     * 显示错误提示
     *
     * @param text
     * @param drawableId
     * @param clickListener 可为空
     */
    public void showErrorTips(int text, int drawableId, OnClickListener clickListener) {
        this.setVisibility(VISIBLE);
        showErrorTips(text <= 0 ? "" : getResources().getString(text), drawableId, clickListener);
    }



    /**
     *
     * 显示错误提示
     *
     * @param text
     * @param drawableId
     * @param clickListener 可为空
     */
    public void showErrorTips(CharSequence text, int drawableId, OnClickListener clickListener) {
        setVisibility(View.VISIBLE);
        removeAllViews();
        addView(getErrorLayout(clickListener), RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        tipslayout_error_text.setText(TextUtils.isEmpty(text) ? "" : text);
        tipslayout_error_img.setImageResource(drawableId <= 0 ? R.mipmap.tips_error : drawableId);
    }


    /**
     * 显示加载提示 默认显示，正在加载中
     */
    public void showLoadingTips() {
        showLoadingTips("");
    }

    /**
     * 显示加载提示
     *
     * @param textId
     */
    public void showLoadingTips(int textId) {
        showLoadingTips(textId > 0 ? getResources().getString(textId) : "");
    }

    /**
     * 显示加载提示
     *
     * @param text
     */
    public void showLoadingTips(CharSequence text) {
        setVisibility(View.VISIBLE);
        removeAllViews();
        addView(getLoadingLayout());
        setClickable(true);
        tipslayout_loading_text.setText(TextUtils.isEmpty(text) ? getResources().getString(R.string.tips_loading) : text);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (getChildCount() > 0) {
            getChildAt(0).setOnClickListener(l);
        }
    }


}
