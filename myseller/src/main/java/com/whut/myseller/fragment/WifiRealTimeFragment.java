package com.whut.myseller.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.whut.myseller.R;
import com.whut.myseller.config.RequestParams;
import com.whut.myseller.interfaces.IBaseView;
import com.whut.myseller.presenter.WifiCountPresenter;
import com.whut.myseller.utils.ChartFactory;
import com.whut.myseller.utils.SharePreferenceHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class WifiRealTimeFragment extends Fragment implements IBaseView{

    private Context context;
    private LineChart today_count_chart;
    private int LINE_CHART_XNUM = 8;
    private Handler handler;
    private int wifi_user_data;
    private TextView wifi_user_tv;
    private String cookie;
    private boolean flag;

    private Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_wifi_real_time, null);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        // TODO Auto-generated method stub
        context = getActivity();
        timer = new Timer();
        wifi_user_data = 0;
        SharePreferenceHelper helper = new SharePreferenceHelper(context,"shopIdCookie",Context.MODE_PRIVATE);
        cookie = helper.getString("cookie", "");
        flag = true;
    }

    private void initTodayHandle() {
        // TODO Auto-generated method stub

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                sendMessage();
            }
        }, 0, 1000);

    }

    private void sendMessage() {

        // TODO Auto-generated method stub

        Calendar c = Calendar.getInstance();
        long end = (long) Math.floor(c.getTimeInMillis() / 1000);
        long start = end - 600;
        new WifiCountPresenter(this).request(RequestParams.REQUEST_QUERY);

        System.out.println(wifi_user_data);
    }

    private void initView(View view) {
        // TODO Auto-generated method stub
        today_count_chart = (LineChart) view
                .findViewById(R.id.wifi_real_time_chart);
        wifi_user_tv = (TextView) view.findViewById(R.id.wifi_real_time_num_tv);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case 1:
                        updateData(today_count_chart, ChartFactory.LINE_CHART);
                        break;
                }
            }
        };
        initTodayHandle();
        initChart(today_count_chart, ChartFactory.LINE_CHART);

    }

    private void initChart(Chart chart, int chart_type) {
        // TODO Auto-generated method stub
        ChartFactory.initChart(chart, chart_type, getActivity());
    }

    private void updateData(Chart chart, int chart_type) {
        // TODO Auto-generated method stub

        switch (chart_type) {
            case ChartFactory.LINE_CHART:
                List<LineDataSet> list;
                if (chart.getData() == null) {
                    list = new ArrayList<LineDataSet>();

                    List<Entry> wifiuserY = new ArrayList<Entry>();
                    list.add(initY(wifiuserY, wifi_user_data));
                } else {
                    list = chart.getData().getDataSets();
                    List<Entry> wifiuserY = list.get(0).getYVals();
                    list.set(0, initY(wifiuserY, wifi_user_data));
                }
                updateChart(chart, ChartFactory.LINE_CHART, list);
                break;

        }

    }

    private LineDataSet initY(List<Entry> list, float data) {
        // TODO Auto-generated method stub
      LineDataSet ds =null;
        if (list.size() == 0)
            for (int i = 0; i < LINE_CHART_XNUM; i++) {
                list.add(new Entry(0, i));
            }

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) != null) {
                list.get(i).setXIndex(i - 1);
            }
        }
        list.remove(0);
        list.add(new Entry(data, list.size()));
        ds = new LineDataSet(list, "");
        try{
            ds.setColor(getActivity().getResources().getColor(
                    R.color.real_time_line));
        }catch (Exception e){
            e.printStackTrace();
        }
  return ds;
    }

    // 生成X轴数据，并显示图表
    private void updateChart(Chart chart, int chart_type, List<LineDataSet> list) {
        switch (chart_type) {
            case ChartFactory.LINE_CHART:
                List<String> x = ChartFactory.getXData(chart_type, list);
                LineData data = new LineData(x, list);
                chart.setData(data);
                chart.invalidate();
                wifi_user_tv.setText(String.valueOf(wifi_user_data));
                break;

            default:
                break;
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            timer.cancel();
        } else {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    sendMessage();
                }
            }, 0, 1000);
        }
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        timer.cancel();
        super.onPause();
    }

    @Override
    public Object getInfo(int code) {
        switch (code){
            case RequestParams.COOKIE:
                return cookie;
            case RequestParams.REQUEST_QUERY:
                return "2";

        }
        return null;
    }

    @Override
    public void setInfo(Object obj, int code) {
        JSONObject json = JSONObject.parseObject((String) obj);
        if(code == RequestParams.REQUEST_QUERY) {
            Message message = new Message();
            message.what = 1;
            if (json.getIntValue("code") == 1) {

                JSONObject array = json.getJSONObject("client");
                if (array != null){
                    wifi_user_data = array.size();
                    handler.sendMessage(message);
                }

            }else if((json.getIntValue("code") == 0)&&(json.getString("msg").contains("available"))){
                wifi_user_data = 0;
                handler.sendMessage(message);

            }else{
                Toast.makeText(context, "网络出错！", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
