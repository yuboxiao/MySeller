package com.whut.myseller.data.model;

import java.io.Serializable;

public class ShopModel implements Serializable{
	private int shopId;
	private String name;
	private String introduction;
	private String address;
	private String imageUrl;
	private String phone;
	private int APNum;
	private String contact;

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}




	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getAPNum() {
		return APNum;
	}

	public void setAPNum(int APNum) {
		this.APNum = APNum;
	}
}
