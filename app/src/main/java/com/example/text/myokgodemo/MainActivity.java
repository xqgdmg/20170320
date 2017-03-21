package com.example.text.myokgodemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;

import com.example.text.myokgodemo.bean.LzyResponse;
import com.example.text.myokgodemo.bean.ServerModel;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnGet;
    private Button mBtnGetBitmap;
    private Button getFileDownload;
    private Button postString;
    private Button postJson;
    private Button uploadPicOrFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initListener() {
        mBtnGet.setOnClickListener(this);
        mBtnGetBitmap.setOnClickListener(this);
        getFileDownload.setOnClickListener(this);
        postString.setOnClickListener(this);
        postJson.setOnClickListener(this);
        uploadPicOrFile.setOnClickListener(this);

    }

    private void initView() {
        mBtnGet = (Button) findViewById(R.id.get);
        mBtnGetBitmap = (Button) findViewById(R.id.getBitmap);
        getFileDownload = (Button) findViewById(R.id.getFileDownload);
        postString = (Button) findViewById(R.id.postString);
        postJson = (Button) findViewById(R.id.postString);
        uploadPicOrFile = (Button) findViewById(R.id.postString);
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnGet){
            performGet();
        }else if (v == mBtnGetBitmap){
            performGetBitmap();
        }else if (v == getFileDownload){
            performGetFileDownload();
        }else if (v == postString){
            performPostUploadString();
        }else if (v == postJson){
            performPostUploadJson();
        }else if (v == uploadPicOrFile){
            performUploadPicOrFile();
        }
    }

    /*
    * 上传文件或者图片
    */
    private void performUploadPicOrFile() {

        ArrayList<File> files = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            files.add(new File("请添加路径！！！！！！！！！"));
        }
        //拼接参数
        OkGo.post(Urls.URL_FORM_UPLOAD)//
                .tag(this)//
                .headers("header1", "headerValue1")//
                .headers("header2", "headerValue2")//
                .params("param1", "paramValue1")//
                .params("param2", "paramValue2")//
//                .params("file1",new File("文件路径"))   //这种方式为一个key，对应一个文件
//                .params("file2",new File("文件路径"))
//                .params("file3",new File("文件路径"))
                .addFileParams("file", files)           // 这种方式为同一个key，上传多个文件
                .execute(new JsonCallback<LzyResponse<ServerModel>>() {
                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
//                        btnFormUpload.setText("正在上传中...");
                    }

                    @Override
                    public void onSuccess(LzyResponse<ServerModel> responseData, Call call, Response response) {
//                        handleResponse(responseData.data, call, response);
//                        btnFormUpload.setText("上传完成");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        handleError(call, response);
//                        btnFormUpload.setText("上传出错");
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        System.out.println("upProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);
                    }
                });
    }

    /*
    * 失败
    */
    private void performPostUploadJson() {
        HashMap<String, String> params = new HashMap<>();
        params.put("key1", "value1");
        params.put("key2", "这里是需要提交的json格式数据");
        params.put("key3", "也可以使用三方工具将对象转成json字符串");
        params.put("key4", "其实你怎么高兴怎么写都行");
        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(Urls.URL_TEXT_UPLOAD)//
                .tag(this)//
//	.params("param1", "paramValue1")//  这里不要使用params，upJson 与 params 是互斥的，只有 upJson 的数据会被上传
                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {  // 为何是 StringCallback , 还不能 new JsonCallback
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        LogUtils.chris("performPostUploadJson String=" + s);
                    }


                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }
                });
    }

    /*
    *  Post 请求 上传String类型的文本
    */
    private void performPostUploadString() {
        OkGo.post(Urls.URL_TEXT_UPLOAD)//
                .tag(this)//
//	.params("param1", "paramValue1")//  这里不要使用params，upString 与 params 是互斥的，只有 upString 的数据会被上传
                .upString("这是要上传的长文本数据！")//
//	.upString("这是要上传的长文本数据！", MediaType.parse("application/xml")) // 比如上传xml数据，这里就可以自己指定请求头
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        LogUtils.chris("onSuccess String = " + s);
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }
                });
    }

    /*
    *  文件下载
    */
    private void performGetFileDownload() {
        OkGo.get(Urls.URL_DOWNLOAD)//
                .tag(this)//
                .execute(new FileCallback() {  //文件下载时，可以指定下载的文件目录和文件名
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        // file 即为文件数据，文件保存在指定目录
                        LogUtils.chris("filePath=" + file.getAbsolutePath());
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调下载进度(该回调在主线程,可以直接更新ui)，会一直回调
                        LogUtils.chris("currentSize=" + currentSize + ",totalSize=" + totalSize + ",progress=" + progress + ",networkSpeed=" + networkSpeed);
                    }
                });
    }

    /*
    * get 获取 bitmap
    */
    private void performGetBitmap() {
        OkGo.get(Urls.URL_IMAGE)//
                .tag("performGetBitmap")//
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
                        // bitmap 即为返回的图片数据
                        LogUtils.chris(Thread.currentThread().getName() + ""); // main 回调在子线程中
                        gotoBitmapActivity(bitmap);
                    }

                });
    }

    /*
    * 跳转显示  bitmap
    */
    private void gotoBitmapActivity(final Bitmap bitmap) {
        try {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
                Intent intent = new Intent(MainActivity.this, BitmapActivity.class); // 为什么不跳？
                intent.putExtra("bitmap",bitmap);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
//            }
//        });
    }

    /*
    * 普通的 get 的请求
    */
    private void performGet() {
        OkGo.get(Urls.GET)     // 请求方式和请求url
                .tag("performGet")                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                        LogUtils.chris(s); // 打印的是网页内容
                        LogUtils.chris(call.toString()); // 打印的是地址
                        LogUtils.chris(response.toString()); // protocol，code，message，url
                    }
                });
    }
}
