package com.example.text.mpandroidchartdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.example.text.mpandroidchartdemo.utils.ChartUtils;
import com.example.text.mpandroidchartdemo.utils.TimeUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LineChart mLineChart;
    private float mPosX;
    private float mPosY;
    private float mCurPosX;
    private float mCurPosY;
    public List<Entry> values;
    private float mOneItemWidth = 20;
    public static int pager = 0; // 索引
    public HashMap<Integer,List<Entry>> hashMap;
    private int mTotalPager; // 真实的页数，不是索引
    private List<Entry> newlist = new ArrayList<>();
    private List<Entry> newlist2 = new ArrayList<Entry>();;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLineChart = (LineChart) findViewById(R.id.lineChart);


        initListData();
        initView();

        initListener();
    }


    private void initListener() {
        mLineChart.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
//                        Log.e("chris","mPosX=" + event.getX() + "，mPosY=" + event.getY() );
                        break;
                    case MotionEvent.ACTION_UP:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
//                        Log.e("chris","mCurPosX=" + event.getX() + "，mCurPosY=" + event.getY() );

                        if (Math.abs(mCurPosX - mPosX) > mOneItemWidth){
                            refreshData();
                        }

                        break;
                }
                return true;
            }

        });
    }

    private void refreshData() {

//        values.clear();
        pager = pager + 1;

        // 让 x 轴的 坐标向前一天
//        ChartUtils.currentTime += (4 * 24 * 60 * 60 * 1000);
//        ChartUtils.monthValuesArray.clear(); // 清空上一页的 x 轴
//        ArrayList<String> listNew = ChartUtils.refreshMonyList();
//        ChartUtils.monthValuesArray.addAll(listNew);

         // 实在是找不出问题，直接判断一下
//        if (values.size() % 7 != 0 && flag == true){
//            pager = pager - 1;
//            flag = false;
//        }

        Log.e("chris","pager=" + pager + "，mTotalPager=" + mTotalPager );

        if (flag){
            if (mTotalPager - 1 < pager){  // 没有更多页了，mTotalPager 要转为 索引才能比较
                Log.e("chris","没有更多页了" );
                pager = -1;  // pager + 1 返回初始值，不然一直在增加
                ChartUtils.needRefresh = false;
                return;
            }
        }else{
            if (mTotalPager - 1 <= pager){  // 没有更多页了，mTotalPager 要转为 索引才能比较
                Log.e("chris","没有更多页了" );
                pager = -1;  // pager + 1 返回初始值，不然一直在增加
                ChartUtils.needRefresh = false;
                return;
            }
        }


        List<Entry> listNextPager = hashMap.get(pager);
        Log.e("chris","hashMap.size()=" + hashMap.size() +"" );
//        for (int i = 0; i < hashMap.size(); i++) {
//            Log.e("chris","listNextPager=" + listNextPager.get(i)+"" );
//        }


//        values.add(new Entry(0, 20));
//        values.add(new Entry(1, 21));
//        values.add(new Entry(2, 22));
//        values.add(new Entry(3, 23));
//        values.add(new Entry(4, 24));
//        values.add(new Entry(5, 25));
//        values.add(new Entry(6, 26)); //

//        for (int i = 0; i < values.size(); i++) {
//            Log.e("chris","item=" + values.get(i).toString());
//        }

        ChartUtils.needRefresh = true;
        ChartUtils.notifyDataSetChanged(mLineChart, listNextPager, ChartUtils.monthValue); // 刷新下一页
    }

    private void initView() {
        ChartUtils.initChart(mLineChart);
        ChartUtils.notifyDataSetChanged(mLineChart, setData(), ChartUtils.monthValue);

         // 计算每个条目之间的间距
//        mLineChart.measure(0,0);
//        mOneItemWidth = mLineChart.getMeasuredWidth()/7 * 5; // 测试
//        Log.e("chris","mOneItemWidth=" + mOneItemWidth);
    }

    /*
    * 坐标点,只能加7个
    */
    private List<Entry> setData() {

        return hashMap.get(pager);
    }

    private void initListData() {
        values = new ArrayList<Entry>();
        values.add(new Entry(0.12f, 1));
        values.add(new Entry(1, 1));
        values.add(new Entry(2, 1));
        values.add(new Entry(3, 1));
        values.add(new Entry(4, 1));
        values.add(new Entry(5, 1));
        values.add(new Entry(6, 1));

        values.add(new Entry(0, 2));
        values.add(new Entry(1, 2));
        values.add(new Entry(2, 2));
        values.add(new Entry(3, 2));
        values.add(new Entry(4, 2));
        values.add(new Entry(5, 2));
        values.add(new Entry(6, 2));

        values.add(new Entry(0, 3));
        values.add(new Entry(1, 3));
        values.add(new Entry(2, 3));
        values.add(new Entry(3, 3));
        values.add(new Entry(4, 3));
        values.add(new Entry(5, 3));
        values.add(new Entry(6, 3));

        values.add(new Entry(0, 4));
        values.add(new Entry(1, 4));
        values.add(new Entry(2, 4));
        values.add(new Entry(3, 4));
        values.add(new Entry(4, 4));
        values.add(new Entry(5, 4));
        values.add(new Entry(6, 4));

        values.add(new Entry(0, 5));
        values.add(new Entry(1, 5));
        values.add(new Entry(2, 5));
        values.add(new Entry(3, 5));
        values.add(new Entry(4, 5));
        values.add(new Entry(5, 5));
        values.add(new Entry(6, 5));

        values.add(new Entry(0, 6));
        values.add(new Entry(1, 6));
        values.add(new Entry(2, 6));
        values.add(new Entry(3, 6));
        values.add(new Entry(4, 6));
        values.add(new Entry(5, 6));
        values.add(new Entry(6, 6));

        sortList(values);

        ChartUtils.hashMap2.putAll(hashMap); // 模拟拷贝 x 轴 分组后的数据
    }

    int key = 0;
    private HashMap<Integer,List<Entry>> sortList(List<Entry> values) {
        int totalPager = values.size() / 7 ;

//        for (int i = 0; i < totalPager; i++) {
//            ArrayList<Entry> list = new ArrayList<Entry>();
//            for (int i = 0; i < ; i++) {
//                list.add(); //  0-6(0) ; 7-13(1) ; 14-20(2) ; ...
//            }
//
//        }

        // 用 map 排序
        //用map存起来新的分组后数据
        hashMap = new HashMap<Integer,List<Entry>>();


        if (totalPager == 0  && values.size() > 0 ){// 只有一页，一页不足7个,0分7分，没有余数的
            mTotalPager = 1;
            for(int i = 0;i < values.size();i ++){
                newlist.add(values.get(i));
            }
            hashMap.put(key, newlist);
            return hashMap;
        }else if (totalPager == 0  && values.size() == 0 ){// 只有一页，这一页也是没有，也就是说没有任何数据
            mTotalPager = 0;
//            newlist.clear();
            return hashMap;
        }else if (totalPager == 1 && values.size() % 7 == 0 ){ // 只有一页，一页刚好7个，没有多余的余数
            mTotalPager = 1;
            equals7(values);
            return hashMap;
        }else{ // 多页（判断还有没有多一个）
            if (values.size() - totalPager * 7 != 0 ){  // 还有一页不足7 个
                mTotalPager = totalPager + 1;

                 // 前面的整页
                for (int i = 0; i < totalPager; i++) {
                    equals7(values);
                }

                 // 最后一页
                for(int i = 0;i < values.size() % 7 ; i ++){
//                    newlist.add(values.get(totalPager * 7 + i)); // 不能使用这个集合了
//                    Log.e("chris",values.get(totalPager * 7 + i) + "！！！！" );
                    newlist2.add(values.get(totalPager * 7 + i));
                }
                flag = true;
//                key = key - 1;
                hashMap.put(key, newlist2);

            }else if (values.size() % 7 == 0){ // 没有多的一页
                mTotalPager = totalPager;
                equals7(values);
                flag = true;
            }
            return hashMap;
        }




         // 这个要求刚好是 7 的倍数
//        equals7(values);

//        return hashMap;
    }

    private void equals7(List<Entry> values) {
        for(int i = 0;i < values.size();i += 7){
             // 添加判断，防止一直执行
            if (values.size() - (key)*7 < 7){
//                key++;
                return;
            }
            newlist = values.subList(i,i+7); // 返回的这个子列表的幕后其实还是原列表；也就是说，修改这个子列表，将导致原列表也发生改变；反之亦然。
            hashMap.put(key, newlist);
            key++;
        }
    }

}
