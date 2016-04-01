package com.whut.myseller.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.whut.myseller.R;
import com.whut.myseller.config.RequestParams;
import com.whut.myseller.interfaces.IBaseView;
import com.whut.myseller.presenter.WifiCountPresenter;
import com.whut.myseller.utils.ChartFactory;
import com.whut.myseller.utils.SharePreferenceHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 16-3-28.
 */
public class WifiCountFragment extends Fragment implements IBaseView{
    private TextView wifi_use_time_tv;
    private TextView wifi_use_num_tv;
    private static final int BAR_CHART_MAXNUM = 6;
    private View mView;
    private BarChart mBarChart;
    private BarData mBarData;
    private String mCookie;
    private  Handler handler;
    private List<Integer> wifi_user_list;
    private List<String> xValue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_wifi_count,null);
        initView();
        initData();
        return mView;
    }

    private void initView() {

        wifi_use_num_tv = (TextView) mView.findViewById(R.id.wifi_use_num_tv);
        wifi_use_time_tv = (TextView) mView.findViewById(R.id.wifi_use_time_tv);

        mBarChart = (BarChart) mView.findViewById(R.id.wifi_usage_chart);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case 1:
                        System.out.println("before----------");
                        updateData(mBarChart, ChartFactory.BAR_CHART);
                        System.out.println("handler----------");
                        break;
                }

            }
        };

    }

    private void initX(int bAR_CHART_MAXNUM2) {

        for (int i = 0; i < BAR_CHART_MAXNUM; i++) {
            xValue.add((i + 1) * 20 + "");
        }
        xValue.set(BAR_CHART_MAXNUM - 1, "100以上");
    }

    private BarDataSet initY(List<BarEntry> y) {

        BarDataSet ds = new BarDataSet(y, "");
        //将柱星图隔开
        ds.setBarSpacePercent(1);
        return ds;
    }


    protected void updateData(BarChart chart, int chart_type) {

        List<BarDataSet> list = new ArrayList<BarDataSet>();
        List<BarEntry> y = new ArrayList<BarEntry>();
        for (int i = 0; i < wifi_user_list.size(); i++) {
            y.add(new BarEntry(wifi_user_list.get(i), i));
        }


        list.add(initY(y));


        System.out.println("----beofre updateChart----");
        updateChart(chart, list);
    }

    private void updateChart(BarChart chart, List<BarDataSet> list) {
        // TODO Auto-generated method stub

        System.out.println("x Axis is +++"+xValue.size());
        System.out.println("list ++" + list.size());

        BarData data = new BarData(xValue, list);
        System.out.println("data----------" + data);
        chart.setData(data);
        chart.invalidate();
        int y = wifi_user_list.get(0);
        wifi_use_num_tv.setText(y + "");
        wifi_use_time_tv.setText(xValue.get(0));
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                int index = e.getXIndex();
                int y = wifi_user_list.get(index);
                wifi_use_num_tv.setText(y + "");
                wifi_use_time_tv.setText(xValue.get(index));
            }

            @Override
            public void onNothingSelected() {
                // TODO Auto-generated method stub
                int y = wifi_user_list.get(0);
                wifi_use_num_tv.setText(y + "");
                wifi_use_time_tv.setText(xValue.get(0));
            }
        });

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
        xValue = new ArrayList<String>();
        wifi_user_list = new ArrayList<Integer>();
        resetList();

        initX(BAR_CHART_MAXNUM);

        SharePreferenceHelper helper = new SharePreferenceHelper(getActivity(),"ShopIdCookie", Context.MODE_PRIVATE);
        mCookie = helper.getString("cookie", "");
        //知道自己为什么那不到cookie了把  注意ShopIdCookie 而不是  shopIdCookie
        //SharePreferenceHelper helper = new SharePreferenceHelper(getActivity(),"shopIdCookie", Context.MODE_PRIVATE);

        System.out.println("mCookie:----------------" + mCookie);
        new WifiCountPresenter(this).request(RequestParams.REQUEST_QUERY);

    }

    @Override
    public void setInfo(Object object, int code) {
        JSONObject json = JSONObject.parseObject((String) object);
        if(code == RequestParams.REQUEST_QUERY){
            Message msg = new Message();
            msg.what = 1;
            if(json.getIntValue("code") == 1){

                if (json != null) {
                    JSONObject  array = json.getJSONObject("client");
                    if (array != null) {
                        Set<String> set = array.keySet();
                        System.out.println("size is ------->"+set.size());
                        Iterator<String> iterator = set.iterator();
                        while (iterator.hasNext()) {
                            JSONObject obj = array
                                    .getJSONObject(iterator.next());
                            //链接时间
                            int time = obj.getIntValue("cnnTime");
                            checkData(time / 60);
                        }
                        System.out.println("sendmessage----------");
                        handler.sendMessage(msg);
                    } else {
                        Toast.makeText(getActivity(), "数据获取失败！",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }else if((json.getIntValue("code") == 0)&&(json.getString("msg").contains("available"))){
                handler.sendMessage(msg);

            }else{
                Toast.makeText(getActivity(),"获取失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public Object getInfo(int code) {
        switch (code){
            case RequestParams.COOKIE:
                return mCookie;
            case RequestParams.REQUEST_QUERY:
                return "2";

        }
        return null;
    }

    private void resetList() {
        for (int i = 0; i < BAR_CHART_MAXNUM; i++) {
            wifi_user_list.add(0);
        }
    }


    protected void checkData(int time) {
        System.out.println("time--->" + time);
        for (int i = 1; i <= BAR_CHART_MAXNUM; i++) {
            if (time < i * 20 ) {
                wifi_user_list.set(i - 1, wifi_user_list.get(i - 1) + 1);
                break;
            }
            if (i == BAR_CHART_MAXNUM) {
                wifi_user_list.set(BAR_CHART_MAXNUM - 1,
                        wifi_user_list.get(i - 1) + 1);
            }
        }
    }

}
