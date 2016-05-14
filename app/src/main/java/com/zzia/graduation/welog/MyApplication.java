package com.zzia.graduation.welog;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by yunyujing on 16/5/6.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance = null;


    public MyApplication() {
        mInstance = this;
    }

    /**
     * 获取Application单例
     */
    public static MyApplication getInstances() {
        if (null == mInstance) {
            mInstance = new MyApplication();
        }
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }


}
