package com.zzia.graduation.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zzia.graduation.adapters.UploadImageGridAdapter;
import com.zzia.graduation.bean.Company;
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
 * 添加工作计划
 * Created by yunyujing on 16/5/3.
 */
public class AddPlanActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_IMAGE_CAPTURE = 100;
    public static final int REQUEST_IMAGE_ALBUM = 101;

    private MyActionBar myActionBar;
    private EditText titleEdit, contentEdit;
    private String title, content;
    private Spinner checker;
    private ArrayList<Integer> checkIdList;
    private ArrayList<String> checkList;

    private TextView addImage;
    private GridView gridView;
    private UploadImageGridAdapter uploadImageGridAdapter;
    private ArrayList<String> list = new ArrayList<>();
    private String imageUrl;


    public static void startAddProjectActivity(Context context, String from) {
        Intent intent = new Intent(context, AddPlanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plan_layout);
        initData();
        initView();
    }

    private void initData() {
        checkIdList = new ArrayList<>();
        checkList = new ArrayList<>();
        ArrayList<BaseBean> arrayList = Company.getUsers(getApplicationContext());
        if (arrayList == null || arrayList.size() <= 0) {//当前还没有建立新项目
            ToastUtils.show(getApplicationContext(), "请先添加员工再指定执行者", Toast.LENGTH_LONG);
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                checkIdList.add(arrayList.get(i).getInt("user_id"));
                checkList.add(arrayList.get(i).getStr("user_name"));

            }
        }
    }

    private void initView() {
        myActionBar = (MyActionBar) findViewById(R.id.add_plan_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));
        myActionBar.setRight2Action(new MyActionBar.Action() {
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
                title=titleEdit.getText().toString();
                content=contentEdit.getText().toString();
                if (title != null && !title.isEmpty()) {
                    if (content != null && !content.isEmpty()) {
                        addDataToSql(title, content, list);
                        //添加成功之后返回
                        finish();
                    }
                }else {
                    ToastUtils.show(getApplicationContext(),"请填写工作日志内容");
                }

            }
        });

        checker = (Spinner) findViewById(R.id.add_plan_layout_checker);
        checker.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, checkList));
        checker.setSelection(0);
        checker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checker.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                checker.setSelection(0);

            }
        });

        titleEdit= (EditText) findViewById(R.id.add_plan_layout_title);
        contentEdit= (EditText) findViewById(R.id.add_plan_layout_content);

        addImage = (TextView) findViewById(R.id.add_plan_layout_extra);
        addImage.setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.add_plan_layout_grid);
        uploadImageGridAdapter = new UploadImageGridAdapter(this, list);
        gridView.setAdapter(uploadImageGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteImage(position);
            }
        });

    }

    /**
     * 将数据添加到数据库
     *
     * @param title
     * @param content
     * @param imageList
     */
    private void addDataToSql(String title, String content, ArrayList<String> imageList) {

        int checkerId = 0;
        for (int i = 0; i < checkList.size(); i++) {
            if (checkList.get(i).equals(checker.getSelectedItem().toString())) {
                checkerId = checkIdList.get(i);
                break;
            }
        }

        String time = Common.getNowTimeNoSecond();
        SQLiteDatabase sqlDataBase = new MySQLiteOpenHelper(getApplicationContext()).getWritableDatabase();
        sqlDataBase.execSQL("insert into checkwork (check_creater,check_createtime,check_checker,check_checktime,plan_title,plan_info,check_state,company_id) values (?,?,?,?,?,?,?,?);",
                new String[]{String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.id, 0))
                        , time
                        , String.valueOf(checkerId)
                        , time
                        , title
                        , content
                        , String.valueOf(0)
                        , String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.companyId, 0))});

        //获取插入数据的id
        Cursor cursor = sqlDataBase.rawQuery("select * from checkwork where check_creater=? and check_createtime=? and check_checker=? and check_checktime=? and plan_title=? and plan_info=?",
                new String[]{String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.id, 0))
                        , time
                        , String.valueOf(checkerId)
                        , time
                        , title
                        , content});

        int checkId = 0;
        if (cursor.moveToFirst()) {
            checkId = cursor.getInt(cursor.getColumnIndex("check_id"));
        }
        cursor.close();

        //将相关的image插入到image
        if (imageList != null && imageList.size() > 0) {

            for (int i = 0; i < imageList.size(); i++) {

                sqlDataBase.execSQL("insert into image (img_url,check_id) values (?,?);", new String[]{imageList.get(i), String.valueOf(checkId)});
            }
        }

    }

    /**
     * 删除指定位置的图片
     *
     * @param position
     */
    private void deleteImage(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除图片")
                .setCancelable(true)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        list.remove(position);
                        uploadImageGridAdapter.notifyDataSetChanged();
                    }
                });
        builder.show();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_plan_layout_extra:
                if (list != null && list.size() < 8) {

                    switchImage();
                } else {
                    ToastUtils.show(getApplicationContext(), "超过最大图片上传数目！");
                }
                break;
            default:
                break;
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
            }
        } else if (requestCode == REQUEST_IMAGE_ALBUM && resultCode == RESULT_OK) {

            if (data != null) {
                saveAndUpload(data, REQUEST_IMAGE_ALBUM);
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
                    list.add(imageUrl);
                }
            }

        } else if (way == REQUEST_IMAGE_ALBUM) {
            list.add(data.getData().toString());
        }
        //显示图片
        uploadImageGridAdapter.notifyDataSetChanged();
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



