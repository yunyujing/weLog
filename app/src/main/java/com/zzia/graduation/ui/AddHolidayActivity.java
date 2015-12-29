package com.zzia.graduation.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

/**
 * Author: yunyujing
 * Date: 2015/12/29
 */
public class AddHolidayActivity extends Activity {
    private MyActionBar actionBar;

    public static void startAddHolidayActivity(Context context, String from) {
        Intent intent = new Intent(context, AddHolidayActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_holiday_layout);
        initView();
    }

    private void initView() {
        actionBar = (MyActionBar) findViewById(R.id.edit_add_holiday_layout_actionbar);
        actionBar.setBackAction(new MyActionBar.BackAction(this));
        actionBar.setRight2Action(new MyActionBar.Action() {
            @Override
            public int getDrawable() {
                return R.mipmap.actionbar_edit_complete;
            }

            @Override
            public int getText() {
                return 0;
            }

            @Override
            public void performAction(View view) {

            }
        });
    }

}
