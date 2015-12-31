package com.zzia.graduation.welog;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzia.graduation.ui.AddClaimActivity;
import com.zzia.graduation.ui.AddHolidayActivity;
import com.zzia.graduation.ui.AddOverTimeActivity;
import com.zzia.graduation.ui.AddProjectActivity;
import com.zzia.graduation.ui.AddTaskActivity;
import com.zzia.graduation.ui.ClockFragment;
import com.zzia.graduation.ui.EditProjectActivity;
import com.zzia.graduation.ui.SearchFragment;
import com.zzia.graduation.ui.ProjectFragment;
import com.zzia.graduation.ui.TaskFragment;
import com.zzia.graduation.utils.ActivityUtils;
import com.zzia.graduation.utils.Common;

import org.w3c.dom.Text;

public class MainActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    //导航抽屉
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    //标题
    private ImageView userImg;
    //悬浮按钮
    private FloatingActionButton fab;
    private View aboveView;
    private TextView projectView;
    private TextView taskView;
    private TextView clocklView;
    private TextView searchView;
    //内容的Fragment
    private String projectTag = "projectTag";
    private String taskTag = "taskTag";
    private String clockTag = "clockTag";
    private String searchTag="searchTag";
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        userImg = (ImageView) findViewById(R.id.activity_main_toolbar_user);
        userImg.setOnClickListener(this);
        projectView = (TextView) findViewById(R.id.activity_main_toolbar_project);
        projectView.setOnClickListener(this);
        taskView = (TextView) findViewById(R.id.activity_main_toolbar_task);
        taskView.setOnClickListener(this);
        clocklView = (TextView) findViewById(R.id.activity_main_toolbar_clock);
        clocklView.setOnClickListener(this);
        searchView= (TextView) findViewById(R.id.activity_main_toolbar_search);
        searchView.setOnClickListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);

        navigationView = (NavigationView) findViewById(R.id.activity_main_drawer_navigation);
        navigationView.setNavigationItemSelectedListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        aboveView = findViewById(R.id.activity_main_above_view);
        aboveView.setOnClickListener(this);
        findViewById(R.id.activity_main_above_view_add_task).setOnClickListener(this);
        findViewById(R.id.activity_main_above_view_add_project).setOnClickListener(this);
        findViewById(R.id.activity_main_above_view_add_overtime).setOnClickListener(this);
        findViewById(R.id.activity_main_above_view_add_holiday).setOnClickListener(this);

        fragments = new Fragment[4];
        fragments[0] = new ProjectFragment();
        fragments[1] = new TaskFragment();
        fragments[2] = new ClockFragment();
        fragments[3]=new SearchFragment();
        clickProject();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_toolbar_user:
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.activity_main_toolbar_project:
                clickProject();
                break;
            case R.id.activity_main_toolbar_task:
                clickTask();
                break;
            case R.id.activity_main_toolbar_clock:
                clickClock();
                break;
            case R.id.activity_main_toolbar_search:
                clickSearch();
                break;
            case R.id.fab:
                if (aboveView.getVisibility() == View.VISIBLE) {
                    aboveView.setVisibility(View.GONE);
                } else {
                    aboveView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.activity_main_above_view:
                aboveView.setVisibility(View.GONE);
                break;
            case R.id.activity_main_above_view_add_task:
                addTask();
                break;
            case R.id.activity_main_above_view_add_project:
                addProject();
                break;
            case R.id.activity_main_above_view_add_overtime:
                addOvertime();
                break;
            case R.id.activity_main_above_view_add_holiday:
                addHoliday();
                break;
            case R.id.activity_main_above_view_add_claim:
                addClaim();
                break;
        }
    }


    private void addClaim() {
        if (aboveView.getVisibility() == View.VISIBLE) {
            aboveView.setVisibility(View.GONE);
        } else {
            aboveView.setVisibility(View.VISIBLE);
        }
        AddClaimActivity.startAddClaimActivity(this, "main");
    }

    private void addHoliday() {
        if (aboveView.getVisibility() == View.VISIBLE) {
            aboveView.setVisibility(View.GONE);
        } else {
            aboveView.setVisibility(View.VISIBLE);
        }
        AddHolidayActivity.startAddHolidayActivity(this, "main");
    }

    private void addOvertime() {
        if (aboveView.getVisibility() == View.VISIBLE) {
            aboveView.setVisibility(View.GONE);
        } else {
            aboveView.setVisibility(View.VISIBLE);
        }
        AddOverTimeActivity.startAddOverTimeActivity(this, "main");
    }

    private void addProject() {
        if (aboveView.getVisibility() == View.VISIBLE) {
            aboveView.setVisibility(View.GONE);
        } else {
            aboveView.setVisibility(View.VISIBLE);
        }
        AddProjectActivity.startAddProjectActivity(this, "main");
    }

    private void addTask() {
        if (aboveView.getVisibility() == View.VISIBLE) {
            aboveView.setVisibility(View.GONE);
        } else {
            aboveView.setVisibility(View.VISIBLE);
        }
        AddTaskActivity.startAddTaskActivity(this, "main");
    }

    private void clickProject() {
        try {
            projectView.setBackgroundResource(R.drawable.homepage_rectangle_press);
            projectView.setTextColor(getResources().getColor(R.color.colorPrimary));
            taskView.setBackgroundResource(R.drawable.homepage_rectangle_normal);
            taskView.setTextColor(getResources().getColor(R.color.white));
            clocklView.setBackgroundResource(R.drawable.homepage_rectangle_normal);
            clocklView.setTextColor(getResources().getColor(R.color.white));
            searchView.setBackgroundResource(R.drawable.homepage_rectangle_normal);
            searchView.setTextColor(getResources().getColor(R.color.white));
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (!fragments[0].isAdded() && !projectTag.equals(fragments[0].getTag())) {
                fragmentTransaction.add(R.id.content_main_frame, fragments[0], projectTag);
            } else if (!fragments[0].isVisible()) {
                fragmentTransaction.show(fragments[0]);
            }
            if (fragments[1].isAdded() && fragments[1].isVisible()) {
                fragmentTransaction.hide(fragments[1]);
            }
            if (fragments[2].isAdded() && fragments[2].isVisible()) {
                fragmentTransaction.hide(fragments[2]);
            }
            if(fragments[3].isAdded()&&fragments[3].isVisible()){
                fragmentTransaction.hide(fragments[3]);
            }
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clickTask() {
        try {
            projectView.setBackgroundResource(R.drawable.homepage_rectangle_normal);
            projectView.setTextColor(getResources().getColor(R.color.white));
            taskView.setBackgroundResource(R.drawable.homepage_rectangle_press);
            taskView.setTextColor(getResources().getColor(R.color.colorPrimary));
            clocklView.setBackgroundResource(R.drawable.homepage_rectangle_normal);
            clocklView.setTextColor(getResources().getColor(R.color.white));
            searchView.setBackgroundResource(R.drawable.homepage_rectangle_normal);
            searchView.setTextColor(getResources().getColor(R.color.white));
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (!fragments[1].isAdded() && !taskTag.equals(fragments[1].getTag())) {
                fragmentTransaction.add(R.id.content_main_frame, fragments[1], taskTag);
            } else if (!fragments[1].isVisible()) {
                fragmentTransaction.show(fragments[1]);
            }
            if (fragments[0].isAdded() && fragments[0].isVisible()) {
                fragmentTransaction.hide(fragments[0]);
            }
            if (fragments[2].isAdded() && fragments[2].isVisible()) {
                fragmentTransaction.hide(fragments[2]);
            }
            if(fragments[3].isAdded()&&fragments[3].isVisible()){
                fragmentTransaction.hide(fragments[3]);
            }
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clickClock() {
        try {
            projectView.setBackgroundResource(R.drawable.homepage_rectangle_normal);
            projectView.setTextColor(getResources().getColor(R.color.white));
            taskView.setBackgroundResource(R.drawable.homepage_rectangle_normal);
            taskView.setTextColor(getResources().getColor(R.color.white));
            clocklView.setBackgroundResource(R.drawable.homepage_rectangle_press);
            clocklView.setTextColor(getResources().getColor(R.color.colorPrimary));
            searchView.setBackgroundResource(R.drawable.homepage_rectangle_normal);
            searchView.setTextColor(getResources().getColor(R.color.white));
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (!fragments[2].isAdded() && !clockTag.equals(fragments[2].getTag())) {
                fragmentTransaction.add(R.id.content_main_frame, fragments[2], clockTag);
            } else if (!fragments[2].isVisible()) {
                fragmentTransaction.show(fragments[2]);
            }
            if (fragments[0].isAdded() && fragments[0].isVisible()) {
                fragmentTransaction.hide(fragments[0]);
            }
            if (fragments[1].isAdded() && fragments[1].isVisible()) {
                fragmentTransaction.hide(fragments[1]);
            }
            if(fragments[3].isAdded()&&fragments[3].isVisible()){
                fragmentTransaction.hide(fragments[3]);
            }
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clickSearch() {
        try {
            projectView.setBackgroundResource(R.drawable.homepage_rectangle_normal);
            projectView.setTextColor(getResources().getColor(R.color.white));
            taskView.setBackgroundResource(R.drawable.homepage_rectangle_normal);
            taskView.setTextColor(getResources().getColor(R.color.white));
            clocklView.setBackgroundResource(R.drawable.homepage_rectangle_normal);
            clocklView.setTextColor(getResources().getColor(R.color.white));
            searchView.setBackgroundResource(R.drawable.homepage_rectangle_press);
            searchView.setTextColor(getResources().getColor(R.color.colorPrimary));
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (!fragments[3].isAdded() && !searchTag.equals(fragments[3].getTag())) {
                fragmentTransaction.add(R.id.content_main_frame, fragments[3], searchTag);
            } else if (!fragments[3].isVisible()) {
                fragmentTransaction.show(fragments[3]);
            }
            if (fragments[0].isAdded() && fragments[0].isVisible()) {
                fragmentTransaction.hide(fragments[0]);
            }
            if (fragments[1].isAdded() && fragments[1].isVisible()) {
                fragmentTransaction.hide(fragments[1]);
            }
            if(fragments[2].isAdded()&&fragments[2].isVisible()){
                fragmentTransaction.hide(fragments[2]);
            }
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_userinfo) {//账号信息

        } else if (id == R.id.nav_collect) {//收藏

        } else if (id == R.id.nav_overtime) {//加班

        } else if (id == R.id.nav_holiday) {//休假

        } else if (id == R.id.nav_claim) {//报销

        } else if (id == R.id.nav_manage) {//设置

        } else if (id == R.id.nav_help) {//帮助

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (aboveView.getVisibility() == View.VISIBLE) {
                aboveView.setVisibility(View.GONE);
                return true;
            }
            if (navigationView.isShown()) {
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                ActivityUtils.alertExitDialog(this);
                return true;
            }
        } else {
            return super.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }


}
