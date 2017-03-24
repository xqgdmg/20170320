package com.example.text.mpandroidchartdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.text.mpandroidchartdemo.utils.ChartUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LineChart mLineChart;
    private float mPosX;
    private float mPosY;
    private float mCurPosX;
    private float mCurPosY;
    private List<Entry> values;
    private float mOneItemWidth = 50;
    private float x = 6;
    private float y = 26;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLineChart = (LineChart) findViewById(R.id.lineChart);

        init();

        test();
    }

    private void test() {
        mLineChart.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        Log.e("chris","mPosX=" + event.getX() + "，mPosY=" + event.getY() );
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        Log.e("chris","mCurPosX=" + event.getX() + "，mCurPosY=" + event.getY() );

                        if (Math.abs(mCurPosX - mPosY) > mOneItemWidth){
                            refreshData();
                        }

                        break;
                }
                return true;
            }

        });
    }

    private void refreshData() {
//        values.remove(0);
//        values.add(new Entry(7, 20));

        /*values.clear();
        values.add(new Entry(0, 20));
        values.add(new Entry(1, 21));
        values.add(new Entry(2, 22));
        values.add(new Entry(3, 23));
        values.add(new Entry(4, 24));
        values.add(new Entry(5, 25));
        values.add(new Entry(x, y)); // 只看最后一条*/
//        x = x + 1;
//        y = y + 1;

//        for (int i = 0; i < values.size(); i++) {
//            Log.e("chris","item=" + values.get(i).toString());
//        }

        ChartUtils.notifyDataSetChanged(mLineChart, values, ChartUtils.weekValue);
    }

    private void init() {
        ChartUtils.initChart(mLineChart);
        ChartUtils.notifyDataSetChanged(mLineChart, getData(), ChartUtils.weekValue);

         // 计算每个条目之间的间距
        mLineChart.measure(0,0);
        mOneItemWidth = mLineChart.getMeasuredWidth()/7;
        Log.e("chris","mOneItemWidth=" + mOneItemWidth);
    }

    /*
    * 坐标点
    */
    private List<Entry> getData() {
        values = new ArrayList<>();
        values.add(new Entry(0, 15));
        values.add(new Entry(1, 15));
        values.add(new Entry(2, 15));
        values.add(new Entry(3, 20));
        values.add(new Entry(4, 25));
        values.add(new Entry(5, 20));
        values.add(new Entry(6, 20));
        values.add(new Entry(6, 30));
        values.add(new Entry(6, 30));
        values.add(new Entry(6, 30));
        values.add(new Entry(6, 30));


        return values;
    }
}
