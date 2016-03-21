package com.whut.myseller.utils;

/**
 * Created by yubo on 2016/3/19.
 */

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
}
