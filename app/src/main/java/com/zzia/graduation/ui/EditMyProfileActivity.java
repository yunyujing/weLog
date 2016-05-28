package com.zzia.graduation.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zzia.graduation.bean.User;
import com.zzia.graduation.common.util.StringUtils;
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

/**
 * Created by yunyujing on 16/5/15.
 */
public class EditMyProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private MyActionBar myActionBar;
    private SimpleDraweeView headImg;
    private EditText nameEdit, sexEdit, ageEdit, telEdit, addressEdit;
    private String imageUrl, name, sex, age, tel, address;
    private String newImageUrl;
    private Button submit;
    private final int REQUEST_IMAGE_CAPTURE = 101;
    private final int REQUEST_IMAGE_ALBUM = 102;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_my_profile_layout);
        initView();
    }

    private void initView() {
        imageUrl = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.icon, ""));
        newImageUrl = imageUrl;
        name = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.name, ""));
        sex = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.sex, ""));
        age = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.age, ""));
        tel = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.tel, ""));
        address = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.address, ""));

        myActionBar = (MyActionBar) findViewById(R.id.edit_my_profile_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));
        headImg = (SimpleDraweeView) findViewById(R.id.edit_my_profile_head_img);
        findViewById(R.id.edit_my_profile_img_rl).setOnClickListener(this);
        headImg.setOnClickListener(this);
        nameEdit = (EditText) findViewById(R.id.edit_my_profile_name);
        sexEdit = (EditText) findViewById(R.id.edit_my_profile_sex);
        ageEdit = (EditText) findViewById(R.id.edit_my_profile_age);
        telEdit = (EditText) findViewById(R.id.edit_my_profile_tel);
        addressEdit = (EditText) findViewById(R.id.edit_my_profile_address);
        submit = (Button) findViewById(R.id.edit_my_profile_layout_submit);
        submit.setOnClickListener(this);
        if (imageUrl != null && !StringUtils.isEmpty(imageUrl)) {
            headImg.setImageURI(Uri.parse(imageUrl));

        }
        nameEdit.setHint(name);
        sexEdit.setHint(sex);
        ageEdit.setHint(age);
        telEdit.setHint(tel);
        addressEdit.setHint(address);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_my_profile_img_rl:
                switchImage();
                break;
            case R.id.edit_my_profile_layout_submit:
//                updateDataToSql();
                finish();
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
                    newImageUrl = Common.CACHEDIR_IMG + imageName + ".jpg";
                    //将图片保存到本地
                    saveImage(photo, newImageUrl);

                }
            }

        } else if (way == REQUEST_IMAGE_ALBUM) {
            newImageUrl = data.getData().toString();
        }

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


    private void updateDataToSql() {
        String newName = nameEdit.getText().toString();
        String newSex = sexEdit.getText().toString();
        String newAge = ageEdit.getText().toString();
        String newTel = telEdit.getText().toString();
        String newAddress = addressEdit.getText().toString();
        if (newName.equals(name) && newSex.equals(sex) && newAge.equals(age) && newTel.equals(tel) && newAddress.equals(address) && (imageUrl).equals(newImageUrl)) {
            ToastUtils.show(getApplicationContext(), "没有编辑任何内容，不能提交");
        } else {
            SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(getApplicationContext()).getWritableDatabase();
            if (!imageUrl.equals(newImageUrl)) {//更新用户头像
                Cursor cursor = sqLiteDatabase.rawQuery("select * from image where user_id=?",
                        new String[]{String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.id, 0))});
                if (cursor.moveToFirst()) {
                    sqLiteDatabase.execSQL("update  image set img_url=" + newImageUrl + " where user_id=? ",
                            new String[]{String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.id, 0))});
                } else {
                    sqLiteDatabase.execSQL("insert into  image (img_url, user_id ) values (?,?)",
                            new String[]{imageUrl, String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.id, 0))});
                }

                cursor.close();
            }
            //更新基本信息
//            sqLiteDatabase.execSQL("update user (user_name,user_sex,user_age,user_tel,user_address) where user_id=?",
            sqLiteDatabase.execSQL("update user set user_name=" + newName + ", user_sex=" + newSex + ", user_age=" + newAge + ",user_tel=" + newTel + ",user_address= " + newAddress + " " +
                            "where user_id=?",
                    new String[]{String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.id, 0))});
        }

    }
}
