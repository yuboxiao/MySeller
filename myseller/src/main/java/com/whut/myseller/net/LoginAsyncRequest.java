package com.whut.myseller.net;

import android.os.AsyncTask;
import android.util.Log;

import com.whut.myseller.presenter.LoginPresenter;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yubo on 2016/3/18.
 * AsyncTask
 * 1.params 是参数
 * 2.过程
 * 3.结果
 */
public class LoginAsyncRequest extends AsyncTask<String, Void, String> {

    private LoginPresenter mLoginPresenter;
    private int mRequestCode;

    public LoginAsyncRequest(LoginPresenter loginPresenter,int reqestCode){
        this.mLoginPresenter = loginPresenter;
        this.mRequestCode = reqestCode;
    }


    @Override
    protected String doInBackground(String... params) {
//        Log.d("LoginAsyncRequest", "doInBackground----->");
//        Log.d("parms[0]",params[0]);
//        Log.d("parms[0]",params[1]);
//        Log.d("parms[0]",params[2]);
//        Log.d("parms[0]",params[3]);
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Map form = new HashMap();
        form.put("account",params[1]);
        form.put("password",params[2]);
        form.put("time",params[3]);



        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        mLoginPresenter.respose(s,mRequestCode);
        super.onPostExecute(s);
    }
}
