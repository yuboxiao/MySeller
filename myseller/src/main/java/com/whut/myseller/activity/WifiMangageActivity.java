package com.whut.myseller.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.whut.myseller.R;
import com.whut.myseller.fragment.VIPFragment;
import com.whut.myseller.fragment.WifiAPFragment;
import com.whut.myseller.interfaces.IBaseView;

/**
 * Created by root on 16-4-1.
 */
public class WifiMangageActivity extends Activity implements IBaseView ,View.OnClickListener{

    private WifiAPFragment mWifiAPFragment;
    private VIPFragment mVipFragment;

    private FragmentManager mFragmentManager;
    private ImageView mWifiAPManager;
    private ImageView mWifiAPPortal;
    private ImageView mWifiAPNone;

    @Override
    public void setInfo(Object object, int code) {

    }

    @Override
    public Object getInfo(int code) {
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_manage);
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {
       mFragmentManager = getFragmentManager();
        setTab(0);
        mWifiAPManager = (ImageView)findViewById(R.id.wifi_ap_manage);
        mWifiAPPortal = (ImageView)findViewById(R.id.wifi_ap_portal);
        mWifiAPManager.setOnClickListener(this);
        mWifiAPPortal.setOnClickListener(this);
    }

    public void setTab(int index){
        FragmentTransaction tran= mFragmentManager.beginTransaction();
        hideFragments(tran);
        switch (index){
            case 0:
                if(mWifiAPFragment == null){
                    mWifiAPFragment = new WifiAPFragment();
                    tran.add(R.id.wifi_frame,mWifiAPFragment);
                }
                tran.show(mWifiAPFragment);
                break;
            case 1:
                if(mVipFragment == null){
                    mVipFragment = new VIPFragment();
                    tran.add(R.id.wifi_frame,mVipFragment);
                }
                tran.show(mVipFragment);
                break;
            case 2:
                break;
        }
        tran.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wifi_ap_manage:
                setTab(0);
                break;
            case R.id.wifi_ap_portal:
                setTab(1);
                break;
            case R.id.wifi_ap_none:
                setTab(2);
                break;
        }
    }
    private void hideFragments(FragmentTransaction tran) {
        // TODO Auto-generated method stub
        if(mWifiAPFragment!=null){
            tran.hide(mWifiAPFragment);
        }
        if(mVipFragment!=null){
            tran.hide(mVipFragment);
        }
    }

}
