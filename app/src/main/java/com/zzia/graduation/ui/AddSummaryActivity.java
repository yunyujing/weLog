package com.zzia.graduation.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.zzia.graduation.adapters.UploadImageGridAdapter;
import com.zzia.graduation.common.util.ToastUtils;
import com.zzia.graduation.utils.ActivityUtils;
import com.zzia.graduation.utils.Common;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 添加工作总结
 * Created by yunyujing on 16/5/3.
 */
public class AddSummaryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_ALBUM = 102;

    private MyActionBar myActionBar;
    private TextView addImage;
    private GridView gridView;
    private UploadImageGridAdapter uploadImageGridAdapter;
    private ArrayList<Uri> list = new ArrayList<>();
    private String imageUrl;

    public static void startAddProjectActivity(Context context, String from) {
        Intent intent=new Intent(context,AddSummaryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_summary_layout);
        initView();
    }

    private void initView() {
        myActionBar= (MyActionBar) findViewById(R.id.add_summary_layout_actionbar);
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
                //上传

            }
        });

        addImage = (TextView) findViewById(R.id.add_summary_layout_extra);
        addImage.setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.add_summary_layout_grid);
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
            case R.id.add_summary_layout_extra:
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
                    list.add(Uri.parse(imageUrl));
                }
            }

        } else if (way == REQUEST_IMAGE_ALBUM) {
            list.add(data.getData());
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

