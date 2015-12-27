package com.zzia.graduation.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zzia.graduation.utils.Common;
import com.zzia.graduation.utils.StringUtils;
import com.zzia.graduation.welog.R;


/**
 * Author: yunyujing
 * Date: 2015/12/27
 */
public class EditActivity extends Activity {
    public static void startEditActivity(Context context, String from) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        if (StringUtils.isEmpty(from)) {
            if (from.equals(Common.DETAIL_PROJECT)) {
                setContentView(R.layout.edit_project_layout);
            } else if (from.equals(Common.DETAIL_TASK)) {
                setContentView(R.layout.edit_task_layout);
            }
        }
    }
}
