package com.whut.myseller.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.whut.myseller.R;

import java.util.ArrayList;

/**
 * Created by root on 16-3-28.
 */
public class WifiCountFragment extends Fragment {

    private View mView;
    private BarChart mBarChart;
    private BarData mBarData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_wifi_count,null);
        initView();
        initData();
        return mView;
    }

    private void initView() {
        mBarChart = (BarChart) mView.findViewById(R.id.wifi_usage_chart);
        mBarData = getBarChartData(6,100);
        mBarChart.setData(mBarData);

        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        mBarChart.setNoDataTextDescription("You need to provide data for the chart.");
        mBarChart.animate();
        mBarChart.setDescription("");

    }

    private BarData getBarChartData(int XNum, int YRange) {
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < XNum-1; i++) {
            xValues.add((i + 1)*20+"");
        }
            xValues.add("以上");

        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();

        for (int i = 0; i < XNum; i++) {
            float value = (float) (Math.random() * YRange/*100以内的随机数*/) + 3;
            yValues.add(new BarEntry(value, i));
        }

        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues,"");

        barDataSet.setColor(Color.rgb(114, 188, 223));

        ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
        barDataSets.add(barDataSet); // add the datasets

        BarData barData = new BarData(xValues, barDataSets);

        return barData;
    }

    private void initData() {

    }

}
