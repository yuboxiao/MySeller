package com.whut.myseller.data.model;
/**
 * Created by jiang on 2016/2/25.
 */

/*****
 * ssid : ap名
 * encrypt : 是否加密（0:不加密  5:加密）
 * encrypt_key : 密碼
 * encrypt_algorithm : 加密算法
 */
public class APModel {

    private String ssid;
    private int encrypt;
    private String encrypt_key;
    private int encrypt_algorithm;

    public int getEncrypt_algorithm() {
        return encrypt_algorithm;
    }

    public void setEncrypt_algorithm(int encrypt_algorithm) {
        this.encrypt_algorithm = encrypt_algorithm;
    }

    public int getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(int encrypt) {
        this.encrypt = encrypt;
    }


    public String getEncrypt_key() {
        return encrypt_key;
    }

    public void setEncrypt_key(String encrypt_key) {
        this.encrypt_key = encrypt_key;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}