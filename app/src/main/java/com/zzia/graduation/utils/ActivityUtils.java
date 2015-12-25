package com.zzia.graduation.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Author: yunyujing
 * Date: 2015/12/18
 */
public class ActivityUtils {
    public static void alertExitDialog(final Activity activity){
        final AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.create();
        builder.setTitle("确定要退出吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
