package com.zzia.graduation.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzia.graduation.welog.R;

/**
 * Author: yunyujing
 * Date: 2015/12/25
 */
public class MyActionBar extends RelativeLayout implements View.OnClickListener {

    private LayoutInflater inflater;
    private ImageView back;
    private TextView title;
    private View right;

    public MyActionBar(Context context) {
        this(context, null);
    }

    public MyActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyActionBar, defStyleAttr, 0);
        String name = a.getString(R.styleable.MyActionBar_titleName);
        int color = a.getColor(R.styleable.MyActionBar_titleColor, getResources().getColor(R.color.text_fff));
        int gravity = a.getInteger(R.styleable.MyActionBar_titleGravity, 19);
        if (title != null) {
            title.setTextColor(color);
            title.setGravity(gravity);
        }
        if (name != null) {
            setTitle(name);
        }
        a.recycle();
    }

    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.myactionbar_layout, null);
        back = (ImageView) view.findViewById(R.id.myactionbar_back_img);
        title = (TextView) view.findViewById(R.id.myactionbar_title);
        right = view.findViewById(R.id.myactionbar_right_ll);
        addView(view);
    }

    public void setTitle(CharSequence text) {
        title.setText(text == null ? " " : text);
    }

    public void setTitle(int textId) {
        title.setText(textId < 0 ? " " : getResources().getString(textId));
    }

    public String getTitle() {
        if (title != null) {
            return title.getText() == null ? " " : title.getText().toString();
        }
        return " ";
    }

    @Override
    public void onClick(View v) {
        final Object tag = v.getTag();
        if (tag instanceof Action) {
            final Action action = (Action) tag;
            action.performAction(v);
        }
    }

    //点击返回键返回到上个页面
    public void setBackAction(BackAction action) {
        if (back != null) {
            back.setTag(action);
            back.setOnClickListener(this);
        }
    }

    /**
     * 实现返回键的动作类，是一个特殊而广泛应用的实现AbsAction的类，不需要每一个子类分别实现
     */
    public static class BackAction extends AbsAction {
        Activity mActivity;

        public BackAction(Activity activity) {
            super(R.mipmap.actionbar_back_arrow);
            this.mActivity = activity;
        }

        @Override
        public void performAction(View view) {
            if (mActivity != null) {
                mActivity.onBackPressed();
            }
        }
    }

    //点击右侧的按键跳转到相应的页面或显示相应的布局
    public void setRightAction(AbsAction action) {

    }

    /**
     * 对于右侧的布局中控件进行填充，子类实现该动作
     */
    public static abstract class AbsAction implements Action {

        final private int mDrawable;
        final private int mText;

        public AbsAction(int drawable) {
            mDrawable = drawable;
            mText = -1;
        }

        public AbsAction(int drawable, int text) {
            mDrawable = drawable;
            mText = text;
        }

        @Override
        public int getDrawable() {
            return mDrawable;
        }

        public int getText() {
            return mText;
        }
    }

    /**
     * 对于TitleBar的控件和动作的定义
     */
    public interface Action {

        public int getDrawable();// 图片或背景

        public int getText();// 文字

        public void performAction(View view);//动作

    }
}
