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
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.whut.myseller.R;
import com.whut.myseller.config.RequestParams;
import com.whut.myseller.interfaces.IBaseView;
import com.whut.myseller.presenter.WifiCountPresenter;
import com.whut.myseller.utils.SharePreferenceHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by root on 16-3-28.
 */
public class WifiCountFragment extends Fragment implements IBaseView{

    private View mView;
    private BarChart mBarChart;
    private BarData mBarData;
    private String mCookie;
    private  Handler handler;

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



        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case 1:
                       // updateData(chart, ChartsFactory.BAR_CHART);
                        System.out.println("handler----------");
                        break;
                }

            }
        };

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
        SharePreferenceHelper helper = new SharePreferenceHelper(getActivity(),"shopIdCookie", Context.MODE_PRIVATE);
        mCookie = helper.getString("cookie", "");
        System.out.println("mCookie:----------------"+mCookie);
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
//                    JSONObject  array = json.getJSONObject("client");
//                    if (array != null) {
//                        Set<String> set = array.keySet();
//                        Iterator<String> iterator = set.iterator();
//                        while (iterator.hasNext()) {
//                            JSONObject object = array
//                                    .getJSONObject(iterator.next());
//                            int time = object.getIntValue("cnnTime");
//                            checkData(time / 60);
//                        }
//                        System.out.println("sendmessage----------");
//                        handler.sendMessage(msg);
//                    } else {
//                        Toast.makeText(getActivity(), "数据获取失败！",
//                                Toast.LENGTH_SHORT).show();
//                    }
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
}
