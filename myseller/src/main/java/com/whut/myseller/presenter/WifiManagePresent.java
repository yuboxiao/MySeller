package com.whut.myseller.presenter;

import com.whut.myseller.config.RequestParams;
import com.whut.myseller.interfaces.IBasePresenter;
import com.whut.myseller.interfaces.IBaseView;
import com.whut.myseller.net.AsyncHttpGet;

import java.util.HashMap;

/**
 * Created by root on 16-4-2.
 */
public class WifiManagePresent implements IBasePresenter {

    private IBaseView iBaseView;
    public WifiManagePresent(IBaseView iBaseView){
        this.iBaseView = iBaseView;
    }

    @Override
    public void request(int code) {
        HashMap info=(HashMap)iBaseView.getInfo(code);
        String shopID = (String)info.get("ShopId");
        String cookie = (String)info.get("Cookie");
        System.out.println("cookie:----->"+cookie+"shopID:--->"+shopID);
        if(RequestParams.REQUEST_QUERY == code){
            new AsyncHttpGet(RequestParams.GET_AP_LIST+"?shopId="+shopID,cookie,this,code);
        }
    }

    @Override
    public void respose(String data, int code) {
        System.out.println("wifiMangePresent-data-->"+data);
        iBaseView.setInfo(data,code);
    }
}
