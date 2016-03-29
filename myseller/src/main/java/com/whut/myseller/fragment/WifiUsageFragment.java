package com.whut.myseller.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whut.myseller.R;

/**
 * Created by root on 16-3-28.
 */
public class WifiUsageFragment extends Fragment implements View.OnClickListener{

    private TextView tvCount ;
    private TextView tvRealTime;

    private FragmentManager mFragmentManager;

    private WifiCountFragment mWifiCountFragment;
    private WifiRealTimeFragment mWifiRealTImeFragment;
    private View mView;
    private FragmentTransaction mTran;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView =
        inflater.inflate(R.layout.fragment_wifi_usage,null);

        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        tvCount =(TextView)mView.findViewById(R.id.count);
        tvRealTime = (TextView)mView.findViewById(R.id.real_time);
        tvCount.setOnClickListener(this);
        tvRealTime.setOnClickListener(this);
        mFragmentManager = getChildFragmentManager();
        setTab(0);
    }

    private void setTab(int index) {
        mTran = mFragmentManager.beginTransaction();
        reset();
        switch (index){
            case 0:
                if(mWifiCountFragment == null){
                    mWifiCountFragment = new WifiCountFragment();
                    mTran.add(R.id.wifi_fragment, mWifiCountFragment);
                }else{
                    mTran.show(mWifiCountFragment);
                }
            break;
            case 1:
                if(mWifiRealTImeFragment == null){
                    mWifiRealTImeFragment = new WifiRealTimeFragment();
                    mTran.add(R.id.wifi_fragment, mWifiRealTImeFragment);
                }else {
                    mTran.show(mWifiRealTImeFragment);
                }
                break;
        }
        mTran.commit();
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.count:
                setTab(0);
                System.out.println("count被点击");
                break;

            case R.id.real_time:
                setTab(1);
                System.out.println("real_time被点击");
                break;
        }
    }


    public  void reset(){
        // TODO Auto-generated method stub
        tvCount.setTextColor(getResources().getColor(
                R.color.cloud_monitor_word));
        tvRealTime.setTextColor(getResources().getColor(
                R.color.cloud_monitor_word));
        if (mWifiCountFragment != null)
            mTran.hide(mWifiCountFragment);
        if (mWifiRealTImeFragment != null)
            mTran.hide(mWifiRealTImeFragment);
    }

}
