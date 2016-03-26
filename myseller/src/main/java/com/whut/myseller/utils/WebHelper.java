package com.whut.myseller.utils;

/**
 * Created by yubo on 2016/3/19.
 */

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class WebHelper {

    public static String getJsonString(String url, String cookie) {
        try {
            System.out.println("--------->"+url);
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type","application/json");
            httpGet.addHeader("cookie", cookie);
            HttpParams p = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(p, 5000);
            HttpConnectionParams.setSoTimeout(p, 5000);
            HttpClient client = new DefaultHttpClient(p);
            HttpResponse
                    response = client.execute(httpGet);
            return showResponseResult(response);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"code\":0,\"msg\":\"" + e.toString() + "\"}";
        }
    }

    private static String showResponseResult(HttpResponse response) {
        try {
            if (response == null)
                return null;
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuilder res = new StringBuilder("");
            while (null != (line = reader.readLine())) {
                res.append(line);
            }
            return res.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":0,\"msg\":\"" + e.toString() + "\"}";
        }
    }

    public static WebSocketClient getWebSocket(String url , String cookie,final Handler handler, final Map msg){
        Map cookies = new HashMap();
        WebSocketClient webSocketClient = new WebSocketClient(URI.create(url),new Draft_10(),cookies,1000*60*60) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                System.out.println("onOpen--->HttpStatusMessage"+serverHandshake.getHttpStatusMessage());
                String text=(String)JSONObject.toJSON(msg);
                System.out.println("onOpen--->msg"+text);
                send(text);
                System.out.println("open");
            }

            @Override
            public void onMessage(String s) {

                System.out.println("result --->"+s);
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("key", s);
                msg.setData(bundle);
                handler.sendMessage(msg);
                System.out.println("message");
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                System.out.println("close");
            }

            @Override
            public void onError(Exception e) {
                System.out.println("error");
            }
        };
                return webSocketClient;
    }


}
