package com.zzia.graduation.welog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.zzia.graduation.db.MySQLiteOpenHelper;


/**
 * 启动页
 * Created by yunyujing on 4/25/16.
 */
public class WelcomeActivity extends AppCompatActivity {

    private ImageView welcomeView;
    private Handler handler=new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //无title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //fullScreen
        setContentView(R.layout.activity_welcome);
        initAnimation();
        MySQLiteOpenHelper mySQLiteOpenHelper=new MySQLiteOpenHelper(this);
        mySQLiteOpenHelper.getWritableDatabase();//在手机/data/data/包名/下创建db文件


    }

    /**
     * 延迟跳转
     */
    private void initAnimation() {
        welcomeView= (ImageView) findViewById(R.id.welcome_image);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Animation animation= AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.welcome_alpha);
                welcomeView.setAnimation(animation);
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                finish();

            }
        }, 2000L);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
    }

}
