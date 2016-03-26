package com.whut.myseller.net;


import com.alibaba.fastjson.JSONObject;
import com.whut.myseller.config.RequestParams;
import com.whut.myseller.utils.WebHelper;

import java.util.concurrent.Callable;

/**
 * Created by root on 16-3-25.
 */
public class APCallable implements Callable<Integer> {

    private String shopId;
    private String cookie;

    public APCallable (int shopId,String cookie){
        this.shopId = ""+shopId;
        this.cookie = cookie;
    }

    @Override
    public Integer call() throws Exception {
        Integer res = -1;
        String url = RequestParams.GET_AP_LIST + "?shopId="+shopId;
        System.out.println("url----->"+url);
        String json  = WebHelper.getJsonString(url,cookie);
        JSONObject jsonObject =  JSONObject.parseObject(json);
        if(jsonObject.getIntValue("code") == 1){
            res = jsonObject.getJSONArray("list").size();
        }
        return res;
    }
}
