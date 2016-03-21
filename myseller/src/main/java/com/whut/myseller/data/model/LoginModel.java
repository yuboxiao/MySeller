package com.whut.myseller.data.model;

/**
 * Created by yubo on 2016/3/20.
 */

import java.io.Serializable;

/**
 * 登录信息
 * Created by lx on 2016/3/8.
 */
public class LoginModel implements Serializable {
    public final String account;
    public final String password;
    public final String sysTime;

    public LoginModel(String account,String password,String sysTime){
        this.account = account;
        this.password = password;
        this.sysTime = sysTime;
    }
}