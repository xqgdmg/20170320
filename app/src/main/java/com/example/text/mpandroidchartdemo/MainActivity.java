package com.example.text.mpandroidchartdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.text.mpandroidchartdemo.utils.ChartUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LineChart mLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLineChart = (LineChart) findViewById(R.id.lineChart);

        init();
    }

    private void init() {
        ChartUtils.initChart(mLineChart);
        ChartUtils.notifyDataSetChanged(mLineChart, getData(), ChartUtils.weekValue);
    }

    /*
    * 坐标点
    */
    private List<Entry> getData() {
        List<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 15));
        values.add(new Entry(1, 15));
        values.add(new Entry(2, 15));
        values.add(new Entry(3, 20));
        values.add(new Entry(4, 25));
        values.add(new Entry(5, 20));
        values.add(new Entry(6, 20));


        return values;
    }
}
