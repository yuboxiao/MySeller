package com.whut.myseller.net;

import android.os.AsyncTask;
import android.util.Log;

import com.whut.myseller.presenter.LoginPresenter;

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
        Log.d("LoginAsyncRequest", "doInBackground----->");

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        mLoginPresenter.respose(s,mRequestCode);
        super.onPostExecute(s);
    }
}
