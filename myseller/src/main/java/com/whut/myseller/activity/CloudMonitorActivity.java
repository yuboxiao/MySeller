package com.whut.myseller.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.whut.myseller.R;
import com.whut.myseller.fragment.TodayCountFragment;
import com.whut.myseller.fragment.WifiUsageFragment;


public class CloudMonitorActivity extends Activity implements View.OnClickListener{

    private ImageView ivWifiInfo;
    private ImageView ivTodayCount;


    private FragmentManager mFragmentManager;

    private WifiUsageFragment mWifiUsageFragment;
    private TodayCountFragment mTodayCountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_monitor);

        initView();
        initData();

    }

    private void initView() {
        ivTodayCount = (ImageView) findViewById(R.id.today_count_icon);
        ivWifiInfo = (ImageView) findViewById(R.id.wifi_info_icon);
        ivWifiInfo.setOnClickListener(this);
        ivTodayCount.setOnClickListener(this);

        mFragmentManager = getFragmentManager();
        setFragment(0);
    }

    private void setFragment(int index) {

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        reset(fragmentTransaction);
        switch (index){
            case 0:
                if(mWifiUsageFragment == null){
                    mWifiUsageFragment = new WifiUsageFragment();
                    fragmentTransaction.add(R.id.cloud_monitor_fragment,mWifiUsageFragment);
                }else{
                    fragmentTransaction.show(mWifiUsageFragment);
                }
                ivWifiInfo.setImageResource(R.mipmap.wifi_usage_on);
                break;
            case 1:
                if(mTodayCountFragment == null){
                    mTodayCountFragment = new TodayCountFragment();
                    fragmentTransaction.add(R.id.cloud_monitor_fragment,mTodayCountFragment);
                }else{
                    fragmentTransaction.show(mTodayCountFragment);
                }
                ivTodayCount.setImageResource(R.mipmap.today_count_on);
                break;
        }
        fragmentTransaction.commit();
    }

    private void reset(FragmentTransaction fragmentTransaction) {
        ivWifiInfo.setImageResource(R.mipmap.wifi_usage_off);
        ivTodayCount.setImageResource(R.mipmap.today_count_off);
        if(mWifiUsageFragment != null){
            fragmentTransaction.hide(mWifiUsageFragment);
        }
        if(mTodayCountFragment != null){
            fragmentTransaction.hide(mTodayCountFragment);
        }
    }

    private void initData() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wifi_info_icon:
                setFragment(0);
                break;
            case R.id.today_count_icon:
                setFragment(1);
                break;
        }
    }
}
