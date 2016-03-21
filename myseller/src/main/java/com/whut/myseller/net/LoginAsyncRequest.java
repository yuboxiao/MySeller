package com.whut.myseller.net;

import android.os.AsyncTask;
import android.util.Log;

import com.whut.myseller.config.Constants;
import com.whut.myseller.presenter.LoginPresenter;
import com.whut.myseller.utils.MapToJson;
import com.whut.myseller.utils.RSAUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.security.Policy;
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
        final StringBuilder res = new StringBuilder("");
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Map form = new HashMap();
        form.put("account",params[1]);
        form.put("password",params[2]);
        form.put("time",params[3]);

        RSAUtils rsaUtils = new RSAUtils();
        String encryptStr = null;
        String encodeData = null;
        try {
            //要加密的字符串
            encryptStr = MapToJson.MapToJson(form);
            /**
             * loadPublicKey 加载公要
             * 1.对明文进行base64解码
             * 2.使用rsa算法生成公钥
             */
            rsaUtils.loadPublicKey(Constants.PUBLIC_KEY);
            System.out.println("公钥加密成功！！");
        }catch (JSONException e) {
            System.out.println("json格式转换失败");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("公钥加密失败！！");
            e.printStackTrace();
        }
        try {
            //使用公钥和要加密的内容进行加密
            byte[] cipher =  rsaUtils.encrypt(rsaUtils.getPublicKey(), encryptStr.getBytes());
            Class clazz = Class.forName("org.bouncycastle.util.encoders.Base64");
            Method mainMethod = clazz.getMethod("encode", byte[].class);
            mainMethod.setAccessible(true);
            //将加密的内容进行base64编码
            encodeData = new String((byte[]) mainMethod.invoke(null, new Object[]{cipher}));
            List list1 = new ArrayList();
            list.add(new BasicNameValuePair("form",encodeData));
            //将编码后的内容进行网络传输发送给后台
            HttpEntity entity = new UrlEncodedFormEntity(list);
            HttpPost httpPost= new HttpPost(params[0]);
            Log.d("LoginAsyncRequest", params[0]);
            httpPost.setEntity(entity);
            HttpParams p = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(p, 5000);
            HttpConnectionParams.setSoTimeout(p, 5000);
            HttpClient client = new DefaultHttpClient(p);
            HttpResponse [] responses = new HttpResponse[1];
            responses[0]= client.execute(httpPost);

            Header[] headers= responses[0].getHeaders("set-cookie");

            if(headers.length!=0){
                Cookie cookies = new BasicClientCookie("cookie",headers[0].getValue());
                Log.i("getCookie", cookies.getValue() + "---------->");
                Log.i("getCookie", cookies.getValue().substring(cookies.getValue().indexOf("=") + 1, cookies.getValue().indexOf(";")));
                String c = headers[0].getValue();
//                cookie = c.substring(c.indexOf("=") + 1, c.indexOf(";"));
                Constants.USER_COOKIE = c;
            }
            HttpEntity respondEntity = responses[0].getEntity();
            InputStream is = respondEntity.getContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is));
            String line = "";
            while (null != (line = reader.readLine())) {
                res.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("LoginAsyncRequest--->", encodeData);
        Log.d("LoginAsyncRequest++++>", res.toString());
        return res.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        mLoginPresenter.respose(s,mRequestCode);
        super.onPostExecute(s);
    }
}
