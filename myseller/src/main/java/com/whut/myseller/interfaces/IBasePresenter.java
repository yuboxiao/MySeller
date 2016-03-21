package com.whut.myseller.interfaces;

/**
 * Created by yubo on 2016/3/18.
 */
public interface IBasePresenter {

    //请求数据
    public void request(int code);
    //显示数据
    public void respose(String data,int code);
}
