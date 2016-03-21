package com.whut.myseller.net;

import android.os.AsyncTask;

import com.whut.myseller.interfaces.IBasePresenter;
import com.whut.myseller.utils.WebHelper;

/**
 * Created by yubo on 2016/3/20.
 */
public class AsyncHttpGet extends AsyncTask<Void, Void, String> {

    private String url;
    private String cookie;
    private IBasePresenter presenter;
    private int resultCode;

    public AsyncHttpGet(String url, String cookie, IBasePresenter presenter, int resultCode) {
        this.url = url;
        this.cookie = cookie;
        this.presenter = presenter;
        this.resultCode = resultCode;
    }


    @Override
    protected String doInBackground(Void... params) {
        String result=WebHelper.getJsonString(url, cookie);
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        presenter.respose(s,resultCode);
        super.onPostExecute(s);
    }
}
