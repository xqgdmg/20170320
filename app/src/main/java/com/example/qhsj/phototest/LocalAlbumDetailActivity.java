package com.example.qhsj.phototest;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qhsj.utils.Constant;
import com.example.qhsj.utils.FileUtils;
import com.example.qhsj.utils.LocalImageHelper;
import com.example.qhsj.utils.NativeImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author linjizong
 * @Description:选择图片
 * @date 2015-4-11
 */
public class LocalAlbumDetailActivity extends Activity implements  View.OnClickListener
        , CompoundButton.OnCheckedChangeListener {

    GridView gridView;
    View pagerContainer;//图片显示部分
    TextView finish, headerFinish;
//    AlbumViewPager viewpager;//大图显示pager
    TextView mCountView;
    List<LocalImageHelper.LocalFile> currentFolder = null;

    ImageView mBackView;
    View headerBar;
    CheckBox checkBox;
    List<LocalImageHelper.LocalFile> checkedItems;
    LocalImageHelper helper = LocalImageHelper.getInstance();
    private String cameraFilePath;

    public static final int PREVIEW = 5;

    /**
     * 删除的图片
     */
    private ArrayList<String> deleteImageUrls = new ArrayList<String>();
    /**
     * 选中的图片
     */
    private ArrayList<String> selectImageUrls = new ArrayList<String>();

    /**
     * 所有图片数据
     */
    private List<LocalImageHelper.LocalFile> folders;
    /**
     * 取消选中
     */
    private TextView tvCancelSelect;
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_album_detail);

        init();
    }


    private void init() {

        finish = (TextView) findViewById(R.id.album_finish);
        tvCancelSelect = (TextView) findViewById(R.id.tvCancelSelect);
        headerFinish = (TextView) findViewById(R.id.header_finish);
        gridView = (GridView) findViewById(R.id.gridview);
//        viewpager = (AlbumViewPager) findViewById(R.id.albumviewpager);
        pagerContainer = findViewById(R.id.pagerview);
        mCountView = (TextView) findViewById(R.id.header_bar_photo_count);
//        viewpager.setOnPageChangeListener(pageChangeListener);
//        viewpager.setOnSingleTapListener(this);
        mBackView = (ImageView) findViewById(R.id.header_bar_photo_back);
        headerBar = findViewById(R.id.album_item_header_bar);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(this);
        mBackView.setOnClickListener(this);
        finish.setOnClickListener(this);
        headerFinish.setOnClickListener(this);
        tvCancelSelect.setOnClickListener(this);

        findViewById(R.id.tvPreview).setOnClickListener(this);

        selectImageUrls = getIntent().getStringArrayListExtra("selectImageUrls");


        new Thread(new Runnable() {
            @Override
            public void run() {
                //防止停留在本界面时切换到桌面，导致应用被回收，图片数组被清空，在此处做一个初始化处理
                helper.initImage();

                //获取该文件夹下地所有文件
                folders = helper.getFolder("所有图片");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //添加第一个位置的相机
                        if (folders.size() > 0) {
                            LocalImageHelper.LocalFile file = folders.get(0);
                            if (!file.getOriginalUri().equals("camera")) {
                                LocalImageHelper.LocalFile file2 = new LocalImageHelper.LocalFile();
                                file2.setOriginalUri("camera");
                                folders.add(0, file2);
                            }
                        }

                        if (folders != null) {
                            currentFolder = folders;
                            adapter = new MyAdapter(LocalAlbumDetailActivity.this, folders);
                            gridView.setAdapter(adapter);
                            //设置当前选中数量
                            if (checkedItems.size() + LocalImageHelper.getInstance().getCurrentSize() > 0) {
                                finish.setText("确定" + "(" + selectImageUrls.size() + "/9)");
                                finish.setEnabled(true);
                                headerFinish.setText("确定" + "(" + selectImageUrls.size() + "/9)");
                                headerFinish.setEnabled(true);
                            } else {
                                finish.setText("确定");
                                headerFinish.setText("确定");
                            }
                        }
                    }
                });
            }
        }).start();
        checkedItems = helper.getCheckedItems();
        LocalImageHelper.getInstance().setResultOk(false);
    }


    private void showViewPager(int index) {

        pagerContainer.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
//        viewpager.setCurrentItem(index);
        mCountView.setText((index + 1) + "/" + currentFolder.size());
        //第一次载入第一张图时，需要手动修改
        if (index == 0) {
            checkBox.setTag(currentFolder.get(index));
            checkBox.setChecked(checkedItems.contains(currentFolder.get(index)));
        }
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation((float) 0.9, 1, (float) 0.9, 1, pagerContainer.getWidth() / 2, pagerContainer.getHeight() / 2);
        scaleAnimation.setDuration(300);
        set.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation((float) 0.1, 1);
        alphaAnimation.setDuration(200);
        set.addAnimation(alphaAnimation);
        pagerContainer.startAnimation(set);
    }

    private void hideViewPager() {

        pagerContainer.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, (float) 0.9, 1, (float) 0.9, pagerContainer.getWidth() / 2, pagerContainer.getHeight() / 2);
        scaleAnimation.setDuration(200);
        set.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(200);
        set.addAnimation(alphaAnimation);
        pagerContainer.startAnimation(set);
        ((BaseAdapter) gridView.getAdapter()).notifyDataSetChanged();
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
//            if (viewpager.getAdapter() != null) {
//                String text = (position + 1) + "/" + viewpager.getAdapter().getCount();
//                mCountView.setText(text);
//                checkBox.setTag(currentFolder.get(position));
//                checkBox.setChecked(checkedItems.contains(currentFolder.get(position)));
//            } else {
//                mCountView.setText("0/0");
//            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

//    @Override
//    public void onSingleTap() {
//        if (headerBar.getVisibility() == View.VISIBLE) {
//            AlphaAnimation animation = new AlphaAnimation(1, 0);
//            animation.setDuration(300);
//            headerBar.startAnimation(animation);
//            headerBar.setVisibility(View.GONE);
//        } else {
//            headerBar.setVisibility(View.VISIBLE);
//            AlphaAnimation animation = new AlphaAnimation(0, 1);
//            animation.setDuration(300);
//            headerBar.startAnimation(animation);
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_bar_photo_back:
                hideViewPager();
                break;
            case R.id.album_finish://确定
            case R.id.header_finish:
                LocalImageHelper.getInstance().setResultOk(true);

                Intent mIntent = new Intent();
                mIntent.putStringArrayListExtra("deleteImageUrls", deleteImageUrls);
                mIntent.putStringArrayListExtra("selectImageUrls", selectImageUrls);
                setResult(RESULT_OK, mIntent);

                finish();
                break;
            case R.id.tvCancelSelect://取消选中
                selectImageUrls.clear();
                adapter.notifyDataSetChanged();
                break;
            case R.id.tvPreview://预览
//                if (selectImageUrls.size() > 0) {
//                    Intent intent = new Intent(mContext, PictureViewPagerActivity.class);
//                    intent.putStringArrayListExtra("imageUrls", selectImageUrls);
//                    intent.putExtra("currentPos", 0);
//                    startActivityForResult(intent, PREVIEW);
//                } else {
////                    ToastUtil.showTextToast("请选择图片");
//                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (pagerContainer.getVisibility() == View.VISIBLE) {
            hideViewPager();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!b) {
            if (selectImageUrls.contains(compoundButton.getTag())) {
                selectImageUrls.remove(compoundButton.getTag());
            }

            String path = (String) compoundButton.getTag();
            deleteImageUrls.add(path);

        } else {
            if (!selectImageUrls.contains(compoundButton.getTag())) {
                if (selectImageUrls.size() >= 9) {
                    Toast.makeText(LocalAlbumDetailActivity.this,"最多9",Toast.LENGTH_SHORT).show();
                    compoundButton.setChecked(false);
                    return;
                }
                selectImageUrls.add((String) compoundButton.getTag());
            }

            try {
                String filePath = (String) compoundButton.getTag();
                for (int i = 0; i < deleteImageUrls.size(); i++) {
                    if (deleteImageUrls.get(i).equals(filePath)) {
                        deleteImageUrls.remove(filePath);
                    }
                }
            } catch (Exception e) {
            }
        }


        finish.setText("确定" + "(" + selectImageUrls.size() + "/9)");
        finish.setEnabled(true);
        headerFinish.setText("确定"+ "(" + selectImageUrls.size() + "/9)");
        headerFinish.setEnabled(true);
    }

    public class MyAdapter extends BaseAdapter {
        private Context m_context;
        private LayoutInflater miInflater;
        //        DisplayImageOptions options;
        List<LocalImageHelper.LocalFile> paths;

        public MyAdapter(Context context, List<LocalImageHelper.LocalFile> paths) {
            m_context = context;
            this.paths = paths;
//            options=new DisplayImageOptions.Builder()
//                    .cacheInMemory(true)
//                    .cacheOnDisk(false)
//                    .showImageForEmptyUri(R.drawable.dangkr_no_picture_small)
//                    .showImageOnFail(R.drawable.dangkr_no_picture_small)
//                    .showImageOnLoading(R.drawable.dangkr_no_picture_small)
//                    .bitmapConfig(Bitmap.Config.RGB_565)
//                    .setImageSize(new ImageSize(((AppContext) context.getApplicationContext()).getQuarterWidth(), 0))
//                    .displayer(new SimpleBitmapDisplayer()).build();
        }

        @Override
        public int getCount() {
            return paths.size();
        }

        @Override
        public LocalImageHelper.LocalFile getItem(int i) {
            return paths.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;

            if (convertView == null || convertView.getTag() == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.simple_list_item, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                viewHolder.checkBox.setOnCheckedChangeListener(LocalAlbumDetailActivity.this);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            LocalImageHelper.LocalFile localFile = paths.get(i);
            String filePath = FileUtils.getUriPath(LocalAlbumDetailActivity.this, Uri.parse(localFile.getOriginalUri()));

            if (i == 0) {//相机
                viewHolder.checkBox.setVisibility(View.GONE);
            } else {
                viewHolder.checkBox.setVisibility(View.VISIBLE);
            }
            viewHolder.imageView.setTag(i);
            //开线程去加载图片
            new MyThread(filePath, viewHolder.imageView).start();

            viewHolder.checkBox.setTag(filePath);
            viewHolder.checkBox.setChecked(selectImageUrls.contains(filePath));

            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    showViewPager(i);

                    if (i == 0) {
                        doTakePhoto();
                    } else {
                        if (finalViewHolder.checkBox.isChecked()) {
                            finalViewHolder.checkBox.setChecked(false);
                        } else {
                            finalViewHolder.checkBox.setChecked(true);
                        }
                    }
                }
            });


            return convertView;
        }

        private class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
        }
    }

//    SimpleImageLoadingListener loadingListener=new   SimpleImageLoadingListener() {
//        @Override
//        public void onLoadingComplete(String imageUri, View view, final Bitmap bm) {
//            if (TextUtils.isEmpty(imageUri)) {
//                return;
//            }
//            //由于很多图片是白色背景，在此处加一个#eeeeee的滤镜，防止checkbox看不清
//            try {
//                ((ImageView) view).getDrawable().setColorFilter(Color.argb(0xff, 0xee, 0xee, 0xee), PorterDuff.Mode.MULTIPLY);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };

    class MyThread extends Thread {
        private String mUrl;
        private ImageView imageView;
        private Bitmap bitmap;


        public MyThread(String url, ImageView imageView) {
            this.mUrl = url;
            this.imageView = imageView;

            imageView.setImageResource(R.mipmap.ic_launcher);
        }

        @Override
        public void run() {
            super.run();
            bitmap = NativeImageLoader.decodeThumbBitmapForFile(mUrl, 200, 200);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int i = (int) imageView.getTag();
                    if (i == 0) {//相机
                        imageView.setImageResource(R.drawable.camera);
                    } else {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            });
        }
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
                startActivityForResult(intent, MainActivity.CAMERA_WITH_DATA);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(LocalAlbumDetailActivity.this,"no SDCard",Toast.LENGTH_SHORT).show();
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
        switch (requestCode) {
            case MainActivity.CAMERA_WITH_DATA:
                if (BitmapHelp.tempSelectBitmap.size() <= 9 && resultCode == RESULT_OK) {
                    setResult(MainActivity.CAMERA_WITH_DATA, new Intent().putExtra("cameraFilePath", cameraFilePath));

                    MainActivity.curruState = MainActivity.CAMERA_WITH_DATA;


                    finish();

//                    try {
//                        int scale = FileUtils.getZoomScale(cameraFilePath);//得到缩放倍数
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inSampleSize = scale;
//                        Bitmap bitmap = BitmapFactory.decodeFile(cameraFilePath, options);
//                        if (!TextUtils.isEmpty(cameraFilePath)) {
//                            uploadImage(cameraFilePath, bitmap);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
                break;

            case PREVIEW:
                if (resultCode == RESULT_OK) {
                    LocalImageHelper.getInstance().setResultOk(true);

                    data.putStringArrayListExtra("deleteImageUrls", data.getStringArrayListExtra("deleteImageUrls"));
                    data.putStringArrayListExtra("selectImageUrls", data.getStringArrayListExtra("selectImageUrls"));
                    setResult(RESULT_OK, data);

                    finish();
                }
                break;
        }
    }
}