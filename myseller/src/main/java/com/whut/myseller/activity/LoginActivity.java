package com.whut.myseller.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.whut.myseller.R;
import com.whut.myseller.config.Constants;
import com.whut.myseller.config.RequestParams;
import com.whut.myseller.data.model.LoginModel;
import com.whut.myseller.interfaces.IBaseView;
import com.whut.myseller.presenter.LoginPresenter;
import com.whut.myseller.utils.SharePreferenceHelper;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;


/**
 * Created by yubo on 2016/3/17.
 */
public class LoginActivity  extends InstrumentedActivity implements View.OnClickListener,IBaseView{

    private ProgressDialog progressDialog;
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;
    private String usernameStr;
    private String passwordStr;
    private String sysTime;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initData();
        initView();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);


       // registerMessageReceiver();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");


                System.out.println("==================" + messge);
                System.out.println("===================" + extras);
                System.out.println("=================="+showMsg);
            }
        }
    }





    private void initData() {
        mLoginPresenter = new LoginPresenter(this);
    }

    private void initView() {
        progressDialog =new ProgressDialog(this);
        btnLogin = (Button) findViewById(R.id.btn_login);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);

        btnLogin.setOnClickListener(this);
    }


    @Override
    public void setInfo(Object object, int code) {
        if(RequestParams.REQUEST_GET == code){
            sysTime = (String) object;
            if(sysTime.length()>11){
                System.out.println("sysTime--->"+sysTime);
                Toast.makeText(this,"网络错误",Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
                btnLogin.setClickable(true);
                btnLogin.setBackground(getResources().getDrawable(
                        R.drawable.layout_login_button_shape));
                btnLogin.setText("登录");
            }else{
                Log.d("LoginActivity", "sysTime --> " + sysTime);
                mLoginPresenter.request(RequestParams.REQUEST_QUERY);
            }
        }else if(RequestParams.REQUEST_QUERY == code){
            String result = (String)object;
            Log.d("LoginActivity__ybshow", result);
            JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
            int resultCode = jsonObject.getInteger("code");
            SharePreferenceHelper helper = new SharePreferenceHelper(this,"ShopIdCookie", Context.MODE_PRIVATE);
            helper.setString("cookie",Constants.USER_COOKIE);
            Log.d("LoginActivity_ybshow", "resultCode:" + resultCode);
            if(resultCode == 1){
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                this.finish();
                progressDialog.cancel();
            }else{
                progressDialog.cancel();
                btnLogin.setClickable(true);
                btnLogin.setBackgroundResource((R.drawable.layout_login_button_shape));
                btnLogin.setText("登录");
            }
        }
    }

    @Override
    public Object getInfo(int code) {
        if(code == RequestParams.REQUEST_QUERY){
            LoginModel model = new LoginModel(usernameStr,passwordStr,sysTime);
            return  model;
        }

        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login(v);
                break;
        }
    }

    public void login(View v){
        progressDialog.show();
        progressDialog.setCancelable(false);
        btnLogin.setClickable(false);

        btnLogin.setBackgroundResource(R.drawable.layout_login_button_click_shape);

//        btnLogin.setBackground(getResources().getDrawable(
//                R.drawable.layout_login_button_click_shape));
        btnLogin.setText("登录中...");
        if(!TextUtils.isEmpty(etUsername.getText())&&
                !TextUtils.isEmpty(etPassword.getText())){
            usernameStr = etUsername.getText().toString();
            passwordStr = etPassword.getText().toString();
            Log.d("LoginActivity", "usernameStr+passwordStr" + usernameStr+passwordStr);
        }else {
            Toast.makeText(this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
            progressDialog.cancel();
            btnLogin.setClickable(true);
            btnLogin.setBackgroundResource(R.drawable.layout_login_button_shape);
//            btnLogin.setBackground(getResources().getDrawable(
//                    R.drawable.layout_login_button_shape));
            btnLogin.setText("登录");
            return;
        }
        mLoginPresenter.request(RequestParams.REQUEST_GET);

    }
}
