package com.whut.myseller.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.whut.myseller.R;
import com.whut.myseller.config.RequestParams;
import com.whut.myseller.interfaces.IBaseView;
import com.whut.myseller.presenter.WifiManagePresent;
import com.whut.myseller.utils.PullToRefreshBase;
import com.whut.myseller.utils.PullToRefreshListView;
import com.whut.myseller.utils.SharePreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by root on 16-4-1.
 */
public class WifiAPFragment extends Fragment implements IBaseView,View.OnClickListener{

    private  PullToRefreshListView mPullToRefreshListView;
    private View mView;
    private ListView mWifiList;

    private String mShopId;
    private String mCookie;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_wifi_ap,null);

        initView();
        initData();

        return mView;
    }

    private void initView() {
        mPullToRefreshListView=(PullToRefreshListView)mView.findViewById(R.id.wifi_list);
        mPullToRefreshListView.setOnRefreshListener(new MyOnRefreshListener());
        mWifiList = mPullToRefreshListView.getRefreshableView();
    }

    private void initData() {
        SharePreferenceHelper helper = new SharePreferenceHelper(getActivity(),"ShopIdCookie", Context.MODE_PRIVATE);
        mShopId = helper.getString("shopId", "");
        System.out.println("-----WIFIAPManager----getshipID"+mShopId);

        mCookie = helper.getString("cookie", "");
        if (mCookie.isEmpty()) {
            Toast.makeText(getActivity(), "天啦撸！COOKIE居然获取不到",
                    Toast.LENGTH_SHORT).show();
        }
        new WifiManagePresent(this).request(RequestParams.REQUEST_QUERY);
    }

    public class MyOnRefreshListener implements PullToRefreshBase.OnRefreshListener {

        @Override
        public void onRefresh() {
            Toast.makeText(getActivity(), "下拉刷新", Toast.LENGTH_SHORT).show();
            //send request
            mPullToRefreshListView.onRefreshComplete();

        }
    }

    @Override
    public void setInfo(Object object, int code) {

    }

    @Override
    public Object getInfo(int code) {
        if(RequestParams.REQUEST_QUERY == code){
            HashMap<String,String> info = new HashMap<String, String>();
            info.put("ShopId",mShopId);
            info.put("Cookie",mCookie);
            return info;
        }
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}
