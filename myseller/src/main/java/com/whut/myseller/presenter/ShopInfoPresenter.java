package com.whut.myseller.presenter;

import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whut.myseller.config.RequestParams;
import com.whut.myseller.data.model.ShopModel;
import com.whut.myseller.interfaces.IBasePresenter;
import com.whut.myseller.interfaces.IBaseView;
import com.whut.myseller.net.APCallable;
import com.whut.myseller.net.AsyncHttpGet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * Created by root on 16-3-25.
 */
public class ShopInfoPresenter implements IBasePresenter{

    private String cookie;
    private IBaseView iBaseView;

    public ShopInfoPresenter(IBaseView iBaseView){
        this.iBaseView = iBaseView;
    }

    @Override
    public void request(int code) {
        cookie = (String)iBaseView.getInfo(RequestParams.COOKIE);

        System.out.println("cookie--->"+cookie);
        new AsyncHttpGet(RequestParams.GET_SHOPID,cookie,this,code).execute();
        System.out.println("after get send--->" + cookie);
    }

    @Override
    public void respose(String data, int code) {
        System.out.println("data----->" + data);
        JSONObject jsonObject = JSONObject.parseObject(data);
        List<ShopModel> shopModelList = new ArrayList<ShopModel>();
        if(jsonObject.getInteger("code") == 1){
            JSONArray array = jsonObject.getJSONArray("list");
            for(int i=0;i<array.size();i++){
                JSONObject object =  (JSONObject)array.get(i);
                ShopModel model = new ShopModel();
                model.setShopId(object.getIntValue("id"));
                model.setName(object.getString("name"));
                model.setPhone(object.getString("phone"));
                model.setIntroduction(object.getString("introduction"));
                model.setAddress(object.getString("address"));
                model.setImageUrl(object.getString("imageUrl"));
                //model.setAPNum(object.getIntValue(""));
                System.out.println("apnum++++" + model.getAPNum());
                System.out.println("id++++" + model.getShopId());
                APCallable apNUm = new APCallable(model.getShopId(),cookie);
                FutureTask<Integer> task = new FutureTask<Integer>(apNUm);
                new Thread(task).start();
                int num = -1;
                try {
                    num = task.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                System.out.println("apnum + >>>"+num);
                model.setAPNum(num);
                shopModelList.add(model);
            }
            System.out.println("---++++"+shopModelList);

             iBaseView.setInfo(shopModelList,code);
        }
    }
}
