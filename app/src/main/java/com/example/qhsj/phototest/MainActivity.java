package com.example.qhsj.phototest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int CAMERA_WITH_DATA = 1;
    private String cameraFilePath;
    private Button btn01;
    public static  int curruState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        LocalImageHelper helper = LocalImageHelper.getInstance();
        helper.getCheckedItems().clear();


    }

    private void initView() {
        btn01 = (Button) findViewById(R.id.btn1);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                doTakePhoto();
                doPickPhotoFromGallery();
            }
        });
    }

    /**
     * 调用自定义相册
     */
    private void doPickPhotoFromGallery() {

        ArrayList<String> selectImageUrls = new ArrayList<String>();
        for (int i = 0; i < BitmapHelp.tempSelectBitmap.size(); i++) {
            selectImageUrls.add(BitmapHelp.tempSelectBitmap.get(i).getImagePath());
        }


        curruState = ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP;

        Intent intent = new Intent(this, LocalAlbumDetailActivity.class);
        intent.putExtra("selectImageUrls",selectImageUrls);
        startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
    }

    /**
     * 拍照获取图片
     */
    private void doTakePhoto() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                FileUtils.createImageDir();
                File out = new File(Constant.IMG_PATH, FileUtils.getPhotoFileName());
                cameraFilePath = out.getAbsolutePath();
                Uri uri = Uri.fromFile(out);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//根据uri保存照片
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);//保存照片的质量
                notifySystemUpdate(true);

                startActivityForResult(intent, CAMERA_WITH_DATA);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this,"未检测到sdCard",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 通知系统更新
     */
    private void notifySystemUpdate(boolean isAdd) {
        MediaScannerConnection.scanFile(this, new String[]{Constant.IMG_PATH}, null, null);
        if (isAdd) {
            ContentValues localContentValues = new ContentValues();
            localContentValues.put("_data", cameraFilePath);
            localContentValues.put("description", "save image ---");
            localContentValues.put("mime_type", "image/jpeg");
            ContentResolver localContentResolver = PWalletApplication.getInstance().getContentResolver();
            Uri localUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            localContentResolver.insert(localUri, localContentValues);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("haha","onActivityResult");
    }
}
