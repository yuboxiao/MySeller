package com.whut.myseller.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.whut.myseller.config.Constants;

/**
 * Created by root on 16-3-29.
 */
public class ChartFactory {


    public static final int LINE_CHART = 1; // 线形图
    public static final int BAR_CHART = 2; // 条形图
    public static final int HORIZONTAL_BAR_CHART = 3; // 水平条形图
    public static final int RADAR_CHART = 4; // 雷达图
    public static final int BUBBLE_CHART = 5; // 散列图
    public static final int PIE_CHART = 6;        //饼形图

    private static Chart mChart;
    public static int mChartType;

    public static List<LineDataSet> sets = new ArrayList<LineDataSet>();

    public static List getXData(int chart_type, List<LineDataSet> list) {
        switch (chart_type) {
            case LINE_CHART:
                List<String> x = new ArrayList<String>();
                // List<String> x=mchart.getData().getXVals();
                Calendar calendar = Calendar.getInstance();

                long time = calendar.getTimeInMillis();

                for (int i = 0; i < list.get(0).getEntryCount(); i++) {

                    x.add(0,
                            (String) DateFormat.format("HH:mm:ss",
                                    calendar.getTimeInMillis()));
                    calendar.add(Calendar.SECOND, -1);

                }

                return x;
        }
        return null;

    }

    public static void initChart(Chart chart, int chart_type, Context context) {
        mChart = chart;
        mChartType = chart_type;
        XAxis x;
        chart.setNoDataText(Constants.CHART_NO_DATA_TEXT);
        switch (chart_type) {
            case LINE_CHART:
                x = ((LineChart) chart).getXAxis();
                x.setPosition(XAxisPosition.BOTTOM);
                chart.animateXY(1000, 1000);
                ((LineChart) chart).setGridBackgroundColor(Color.WHITE);
                ((LineChart) chart).setPinchZoom(true);
                chart.setDescription("");
                chart.getLegend().setEnabled(false);
                ((LineChart) chart).getAxisRight().setEnabled(false);
                ((LineChart) chart).getXAxis().setDrawGridLines(false);
                ((LineChart) chart).getXAxis().setSpaceBetweenLabels(0);
                x.setAxisLineWidth(1f);
                //x.setTextSize(8f);
                break;
            case BAR_CHART:
                x = ((BarChart) chart).getXAxis();
                x.setPosition(XAxisPosition.BOTTOM);
                chart.animateXY(1000, 1000);
                ((BarChart) chart).setGridBackgroundColor(Color.WHITE);
                ((BarChart) chart).setPinchZoom(true);
                chart.setDescription("");
                chart.getLegend().setEnabled(false);
                ((BarChart) chart).getAxisRight().setEnabled(false);
                ((BarChart) chart).getXAxis().setDrawGridLines(false);
                ((BarChart) chart).getXAxis().setSpaceBetweenLabels(0);
                Typeface mTf = Typeface.createFromAsset(context.getAssets(),
                        "OpenSans-Regular.ttf");
                x.setTypeface(mTf);
                x.setTextSize(8f);
                break;
            case PIE_CHART:
                chart.animateXY(1000, 1000);
                chart.setDescription("");
                chart.getLegend().setEnabled(false);
                ((PieChart) chart).setCenterText(generateCenterSpannableText());
                break;
        }
        chart.setOnChartGestureListener(new MyOnChartGestureListener());
    }

    private static class MyOnChartGestureListener implements OnChartGestureListener {
        private boolean flag = true;

        @Override
        public void onChartGestureStart(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {
        }

        @Override
        public void onChartGestureEnd(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {
        }

        @Override
        public void onChartLongPressed(MotionEvent motionEvent) {
        }

        @Override
        public void onChartDoubleTapped(MotionEvent motionEvent) {
            switch (mChartType) {
                case BAR_CHART:
                    BarChart barChart = (BarChart) mChart;
                    if (flag) {
                        barChart.zoomIn();
                    } else {
                        barChart.fitScreen();
                    }
                    break;
                case LINE_CHART:
                    LineChart lineChart = (LineChart) mChart;
                    if (flag) {
                        lineChart.zoomIn();
                    } else {
                        lineChart.fitScreen();
                    }
                    break;
                default:
                    break;
            }
            flag = !flag;

        }

        @Override
        public void onChartSingleTapped(MotionEvent motionEvent) {
        }

        @Override
        public void onChartFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        }

        @Override
        public void onChartScale(MotionEvent motionEvent, float v, float v1) {
            flag = false;
        }

        @Override
        public void onChartTranslate(MotionEvent motionEvent, float v, float v1) {
        }
    }


    private static SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("新老客户");
//	        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
//	        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//	        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//	        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//	        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//	        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
}
