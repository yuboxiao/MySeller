package com.whut.myseller.presenter;

import com.whut.myseller.config.RequestParams;
import com.whut.myseller.fragment.WifiCountFragment;
import com.whut.myseller.interfaces.IBasePresenter;
import com.whut.myseller.interfaces.IBaseView;
import com.whut.myseller.net.AsyncHttpGet;

/**
 * Created by root on 16-3-29.
 */
public class WifiCountPresenter implements IBasePresenter{

    private IBaseView iBaseView;
    public WifiCountPresenter(IBaseView iBaseView){
        this.iBaseView = iBaseView;
    }


    @Override
    public void request(int code) {
        String cookie = (String) iBaseView.getInfo(RequestParams.COOKIE);
        if(RequestParams.REQUEST_QUERY == code){
            String mall = (String) iBaseView.getInfo(RequestParams.REQUEST_QUERY);
            System.out.println("url = " + RequestParams.WIFI_USER_COUNT + "?mall=" + mall);
            new AsyncHttpGet(RequestParams.WIFI_USER_COUNT+"?mall="+mall,cookie,this,code).execute();
        }
    }

    @Override
    public void respose(String data, int code) {
        //{"code":1,"client":{"c4:66:99:6c:a9:0a":{"cnnTime":27600,"authTime":27480,"state":0},"d0:7a:b5:ee:01:78":{"cnnTime":23940,"authTime":23340,"state":0},
        System.out.println("???????????data = "+ data);
        iBaseView.setInfo(data,code);
    }
}
