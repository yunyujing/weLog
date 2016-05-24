package com.zzia.graduation.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzia.graduation.adapters.DetailTaskAdapter;
import com.zzia.graduation.adapters.UploadImageGridAdapter;
import com.zzia.graduation.bean.Task;
import com.zzia.graduation.bean.User;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.common.util.ToastUtils;
import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.utils.ActivityUtils;
import com.zzia.graduation.utils.Common;
import com.zzia.graduation.utils.SharedPreferenceUtils;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 任务详情页
 * Author: yunyujing
 * Date: 2015/12/21
 */
public class DetailTaskActivity extends AppCompatActivity implements View.OnClickListener {


    private MyActionBar actionBar;
    private int taskId;
    private TextView nameText, createrText, createrTimeText, excuterText, endTimeText, stateText;
    private String name, creater, createrTime, excuter, endTime, state;

    private RecyclerView recyclerView;
    private DetailTaskAdapter detailTaskAdapter;
    private ArrayList<BaseBean> list;

    private GridView gridView;
    private UploadImageGridAdapter imageGridAdapter;
    private ArrayList<String> imageList = new ArrayList<>();

    private ImageView addImage;
    private EditText input;
    private Button send;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_ALBUM = 102;
    private String imageUrl;


    public static void startDetailTaskActivity(Context context, BaseBean baseBean) {
        Intent intent = new Intent(context, DetailTaskActivity.class);
        intent.putExtra("task_id", baseBean.getInt("task_id"));
        intent.putExtra("task_name", baseBean.getStr("task_name"));
        intent.putExtra("task_creater", baseBean.getStr("task_creater"));
        intent.putExtra("task_createtime", baseBean.getStr("task_createtime"));
        intent.putExtra("task_excuter", baseBean.getStr("task_excuter"));
        intent.putExtra("task_endtime", baseBean.getStr("task_endtime"));
        intent.putExtra("task_state", baseBean.getStr("task_state"));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_task_layout);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            taskId = intent.getIntExtra("task_id", 0);
            name = intent.getStringExtra("task_name");
            creater = intent.getStringExtra("task_creater");
            createrTime = intent.getStringExtra("task_createtime");
            excuter = intent.getStringExtra("task_excuter");
            endTime = intent.getStringExtra("task_endtime");
            state = intent.getStringExtra("task_state");
        }

        list = new ArrayList<>();
        list = Task.getComments(getApplicationContext(), taskId);
//        BaseBean bean = new BaseBean();
//        bean.set("icon", "http://login.jpg");
//        bean.set("name", "依依");
//        bean.set("comment", "该任务需求已经商量Ok");
//        bean.set("time", "2016-05-08");
//        list.add(bean);

    }

    private void initView() {
        actionBar = (MyActionBar) findViewById(R.id.detail_task_layout_actionbar);
        actionBar.setBackAction(new MyActionBar.BackAction(this));
//        actionBar.setRight2Action(new MyActionBar.Action() {
//            @Override
//            public int getDrawable() {
//                return R.mipmap.actionbar_edit;
//            }
//
//            @Override
//            public int getText() {
//                return 0;
//            }
//
//            @Override
//            public void performAction(View view) {
//                //编辑任务
//                EditTaskActivity.startEditTaskActivity(DetailTaskActivity.this, Common.DETAIL_TASK);
//            }
//        });

        nameText = (TextView) findViewById(R.id.detail_task_layout_task_name);
        nameText.setText(name);
        createrText = (TextView) findViewById(R.id.detail_task_layout_creater_name);
        createrText.setText(creater);
        createrTimeText = (TextView) findViewById(R.id.detail_task_layout_createtime);
        createrTimeText.setText(createrTime);
        excuterText = (TextView) findViewById(R.id.detail_task_layout_excuter_name);
        excuterText.setText(excuter);
        endTimeText = (TextView) findViewById(R.id.detail_task_layout_endtime);
        endTimeText.setText(endTime);
        stateText = (TextView) findViewById(R.id.detail_task_layout_stage_name);
        stateText.setText(state);


        addImage = (ImageView) findViewById(R.id.detail_task_layout_input_add_image);
        addImage.setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.detail_task_layout_input_add_image_grid);
        imageGridAdapter = new UploadImageGridAdapter(this, imageList);
        gridView.setAdapter(imageGridAdapter);
        input = (EditText) findViewById(R.id.detail_task_layout_input_edit);
        send = (Button) findViewById(R.id.detail_task_layout_input_send);
        send.setOnClickListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.detail_task_layout_comment_recycle);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DetailProjectActivity.MyListItemDecoration(10));
        detailTaskAdapter = new DetailTaskAdapter(this, list);
        recyclerView.setAdapter(detailTaskAdapter);
        if (list != null && list.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.detaile_task_layout_no_comment).setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            findViewById(R.id.detaile_task_layout_no_comment).setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_task_layout_input_add_image:
                if (imageList.size() < 3) {
                    switchImage();

                } else {
                    ToastUtils.show(getApplicationContext(), "一次最多上传3张图片");
                }

                break;
            case R.id.detail_task_layout_input_send:
                BaseBean baseBean = new BaseBean();
                String comment = input.getText().toString().trim();
                String time = Common.getNowTimeNoSecond();
                if (comment.isEmpty() && imageList.size() <= 0) {
                    ToastUtils.show(this, "发送内容不能为空");
                } else {
                    if (!comment.isEmpty()) {
                        baseBean.set("comment_creater", String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.name, "")));
                        baseBean.set("comment_createtime", time);
                        baseBean.set("comment_content", comment);

                    }
                    if (imageList != null && imageList.size() > 0) {
//
                        baseBean.set("comment_image", imageList);

                    }
                    list.add(baseBean);
                    detailTaskAdapter.notifyDataSetChanged();
                    //在数据清除之前添加到数据库
                    addDataToSql(time, comment, imageList);
                    hideSoftInput();
                    //清空上次的编辑信息
                    input.setText("");
                    imageList.clear();
                    gridView.setVisibility(View.GONE);


                }
        }

    }

    /**
     * 隐藏键盘
     */
    private void hideSoftInput() {
        InputMethodManager inputMethodManager;
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null) {
            View v = DetailTaskActivity.this.getCurrentFocus();
            if (v == null) {
                return;
            }

            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 将评论内容添加到数据库
     *
     * @param time
     * @param comment
     * @param imageList
     */
    private void addDataToSql(String time, String comment, ArrayList<String> imageList) {

        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(getApplicationContext()).getWritableDatabase();
        //插入到comment表
        sqLiteDatabase.execSQL("insert into comment (comment_creater,comment_createtime,comment_content,task_id) values (?,?,?,?) ",
                new String[]{String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.id, 0)), time, comment, String.valueOf(taskId)});

        //查询刚才插入的那条数据的ID
        Cursor cursor = sqLiteDatabase.rawQuery("select * from comment where comment_creater=? and comment_createtime=? and comment_content=? and task_id=?",
                new String[]{String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.id, 0)), time, comment, String.valueOf(taskId)});
        int commentId = 0;
        if (cursor.moveToFirst()) {
            commentId = cursor.getInt(cursor.getColumnIndex("comment_id"));
        }
        cursor.close();

        //将评论的图片插入到image表中
        if (imageList != null && imageList.size() > 0) {
            for (int i = 0; i < imageList.size(); i++) {

                sqLiteDatabase.execSQL("insert into image where img_url=? and comment_id=?",
                        new String[]{imageList.get(i), String.valueOf(commentId)});
            }
        }
    }


    /**
     * 选择图片的对话框
     */
    private void switchImage() {
        //拍照或相册选择图片
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择图片").setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    switchByCamera();
                } else {
                    switchByAlbum();
                }
            }
        });
        builder.show();


    }

    /**
     * 拍照
     */
    private void switchByCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imageUrl)));
        if (ActivityUtils.isIntentAvailable(this, intent)) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            ToastUtils.show(getApplicationContext(), "您本地没有安装照相机");
        }
    }

    /**
     * 相册
     */
    private void switchByAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if (ActivityUtils.isIntentAvailable(this, intent)) {
            startActivityForResult(intent, REQUEST_IMAGE_ALBUM);
        } else {
            ToastUtils.show(getApplicationContext(), "您本地没有安装相册");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null) {
                saveAndUpload(data, REQUEST_IMAGE_CAPTURE);
                if (gridView.getVisibility() == View.GONE) {

                    gridView.setVisibility(View.VISIBLE);
                }
            }
        } else if (requestCode == REQUEST_IMAGE_ALBUM && resultCode == RESULT_OK) {

            if (data != null) {
                saveAndUpload(data, REQUEST_IMAGE_ALBUM);
                if (gridView.getVisibility() == View.GONE) {

                    gridView.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    /**
     * 显示并保存图片
     *
     * @param data
     */
    private void saveAndUpload(Intent data, int way) {

        if (way == REQUEST_IMAGE_CAPTURE) {
            //android把拍摄的图片封装到bundle中传递回来
            Bundle extras = data.getExtras();
            if (extras != null) {
                final Bitmap photo = extras.getParcelable("data");
                if (photo != null) {
//                    Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/image.jpg");
//                    Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
//                    //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
//                    bitmap.recycle();


                    // 创建文件夹
                    File file = new File(Common.CACHEDIR_IMG);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    //图片位置
                    String imageName = String.valueOf(System.currentTimeMillis());
                    imageUrl = Common.CACHEDIR_IMG + imageName + ".jpg";
                    //将图片保存到本地
                    saveImage(photo, imageUrl);
                    imageList.add(imageUrl);
                }
            }

        } else if (way == REQUEST_IMAGE_ALBUM) {
            imageList.add(data.getData().toString());
        }
        //显示图片
        imageGridAdapter.notifyDataSetChanged();
    }

    /**
     * 保存拍摄的图片
     */
    private void saveImage(Bitmap map, String path) {
        File file = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (map.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
