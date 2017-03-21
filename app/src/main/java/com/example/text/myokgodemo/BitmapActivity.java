package com.example.text.myokgodemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Chris on 2017/3/21.
 */
public class BitmapActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        LogUtils.chris("BitmapActivity");

        Bitmap bitmap = getIntent().getParcelableExtra("bitmap");
        ImageView   imageView = (ImageView) findViewById(R.id.iv);
        imageView.setImageBitmap(bitmap);
    }
}
