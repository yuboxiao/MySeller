package com.whut.myseller.data.model;

/**
 * Created by root on 16-4-2.
 */
public class APListModel {

    private int id;
    private String mac;
    private int shopId;
    private String type;
    private String authcode;
    private String ssid;
    private String openid;
    private int runstate;
    private int owner;
    public int getRunstate() {
        return runstate;
    }
    public void setRunstate(int runstate) {
        this.runstate = runstate;
    }

    private int upload;
    private int download;
    private int online;
    private String nickname;


//	public APListModel(int id,int shopId,String ssid,String nickname,String mac,int upload,int dowmload,int online) {
//		// TODO Auto-generated constructor stub
//
//		this.id = id;
//		this.shopId = shopId;
//		this.ssid = ssid;
//		this.nickname = nickname;
//		this.mac = mac;
//		this.upload = upload;
//		this.download = dowmload;
//		this.online = online;
//	}


    public int getUpload() {
        return upload;
    }
    public void setUpload(int upload) {
        this.upload = upload;
    }
    public int getDownload() {
        return download;
    }
    public void setDownload(int download) {
        this.download = download;
    }
    public int getOnline() {
        return online;
    }
    public void setOnline(int online) {
        this.online = online;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMac() {
        return mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public int getShopId() {
        return shopId;
    }
    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getAuthcode() {
        return authcode;
    }
    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }
    public String getSsid() {
        return ssid;
    }
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public int getOwner() {
        return owner;
    }
    public void setOwner(int owner) {
        this.owner = owner;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
