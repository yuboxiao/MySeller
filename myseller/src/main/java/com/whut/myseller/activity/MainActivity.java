package com.whut.myseller.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.whut.myseller.R;
import com.whut.myseller.config.Constants;
import com.whut.myseller.config.RequestParams;
import com.whut.myseller.data.model.ShopModel;
import com.whut.myseller.interfaces.IBaseView;
import com.whut.myseller.presenter.ShopInfoPresenter;
import com.whut.myseller.utils.SharePreferenceHelper;
import com.whut.myseller.utils.WebHelper;

import org.bouncycastle.asn1.crmf.CertReqMsg;
import org.java_websocket.client.WebSocketClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;


public class MainActivity extends Activity implements IBaseView,View.OnClickListener{

    private WebSocketClient wsc;
    private String cookie;
    private String shopId="";

    private  ShopModel myshop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();


    }

    private void initData() {
        SharePreferenceHelper helper = new SharePreferenceHelper(this,"ShopIdCookie", Context.MODE_PRIVATE);
        cookie = helper.getString("cookie");
        shopId = helper.getString("shopId","");
        System.out.println("initData----,"+cookie);
        new ShopInfoPresenter(this).request(RequestParams.REQUEST_GET);
    }

    private void initView() {
        Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
               Bundle bundle =  msg.getData();
                String text  = bundle.getString("key");
                System.out.println("--->MainActivity"+text);
            }
        };
        Map map = new HashMap();
        map.put("mail_id","2");
        System.out.println("shipid???"+shopId);
        map.put("shopId",shopId);
        wsc  = WebHelper.getWebSocket(Constants.VIP_BROADCAST,cookie,handler,map);
        System.out.println("wsc----"+wsc);

        wsc.connect();
    }
    @Override
    public void setInfo(Object object, int code) {
        System.out.println("code---->"+code);
        if (code == RequestParams.REQUEST_GET) {
            List<ShopModel> list = (List<ShopModel>) object;
            if (list.size() < 0) {
                Toast.makeText(this, "当前用户没有有效店铺", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                this.finish();
            } else {
                int i = list.size() - 1;
                System.out.println("i=" + i);
                ShopModel available = null;
                //多个店铺，循环，
                for (; i > -1; i--) {
                    if (list.get(i).getAPNum() > 0) {
                        System.out.println("MainACtivity---<" + shopId);
                        System.out.println("MainACtivity---<" + list.get(i));
                        System.out.println("MainACtivity---<" + list.get(i).getShopId());

                        if (shopId.equals(Integer.toString(list.get(i).getShopId()))) {
                            myshop = list.get(i);
                            break;
                        } else {
                            available = list.get(i);
                        }
                    }

                }

                if (i == -1) {
                    if (available == null) {
                        Toast.makeText(this, "当前用户店铺没有有效AP", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        this.finish();

                        System.out.println("----------1---------");
                        return;
                    }
                    myshop = available;
                    shopId = "" + myshop.getShopId();

                }
                //Constants.SHOPNAME = myshop.getName();
                System.out.println("----------2---------");
                SharePreferenceHelper helper = new SharePreferenceHelper(
                        this, "shopIdCookie", Context.MODE_PRIVATE);
                helper.setString("shopId", shopId);
                System.out.println("shopID---->" + shopId);
            }

        }
    }
    @Override
    public Object getInfo(int code) {
        return cookie;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        System.out.println("show in MainActivity----+onClick "+shopId);
        if(shopId == null){
            return;
        }
        switch (v.getId()){
            case R.id.goto_preferential_manage: // 优惠管理
//			intent = new Intent(context, PreferentialManagerActivity.class);
//			startActivity(intent);

                break;
            case R.id.goto_guest_flow: // 客流管理
//			intent = new Intent(context, GuestStateActivity.class);
//			startActivity(intent);
                break;
            case R.id.goto_my_store: // 我的店铺
//                if(null!=myshop){
//                    intent = new Intent(this, StoreManageActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("myshop", myshop);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
                break;

            case R.id.goto_store_manage: //店铺管理
//                intent = new Intent(this, ShopManageActivity.class);
//                startActivity(intent);
                break;

            case R.id.goto_wifi_manage: // wifi管理
//                intent = new Intent(this, WifiManageActivityNew.class);
//                startActivity(intent);
                break;
            case R.id.goto_cloud_monitor: // 云监控
                intent = new Intent(this, CloudMonitorActivity.class);
                startActivity(intent);
                break;
            case R.id.goto_ads_manage: // portal管理
//                intent = new Intent(this, AdsManageActivity.class);
//                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
