package com.whut.myseller.presenter;

import android.util.Log;
import android.view.View;

import com.whut.myseller.config.RequestParams;
import com.whut.myseller.data.model.LoginModel;
import com.whut.myseller.interfaces.IBasePresenter;
import com.whut.myseller.interfaces.IBaseView;
import com.whut.myseller.net.AsyncHttpGet;
import com.whut.myseller.net.LoginAsyncRequest;

import org.apache.http.client.methods.HttpGet;

/**
 * Created by yubo on 2016/3/18.
 */
public class LoginPresenter implements IBasePresenter{

    private IBaseView iBaseView;

    public LoginPresenter( IBaseView iBaseView){
        this.iBaseView = iBaseView;
    }

    @Override
    public void request(int code) {
        LoginModel model= (LoginModel) iBaseView.getInfo(code);
        switch (code){
            case RequestParams.REQUEST_GET://获取系统时间
                Log.d("LoginPresenter", "这里AsyncHttpGet要发送请求了");
                new AsyncHttpGet(RequestParams.GET_SYSTEM_TIME,"",this,code).execute();
                break;
        }
    }

    @Override
    public void respose(String data, int code) {
        iBaseView.setInfo(data,code);
    }
}
