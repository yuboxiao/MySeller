package com.whut.myseller.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.whut.myseller.R;
import com.whut.myseller.config.RequestParams;
import com.whut.myseller.interfaces.IBaseView;
import com.whut.myseller.presenter.LoginPresenter;

/**
 * Created by yubo on 2016/3/17.
 */
public class LoginActivity  extends Activity implements View.OnClickListener,IBaseView{

    private ProgressDialog progressDialog;
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initData();
        initView();
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
            String sysTime = (String) object;
            Log.d("LoginActivity", "sysTime --> " + sysTime);
        }
    }

    @Override
    public Object getInfo(int code) {
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
            String usernameStr = etUsername.getText().toString();
            String passwordStr = etPassword.getText().toString();
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
