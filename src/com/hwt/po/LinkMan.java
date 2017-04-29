package com.hwt.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LinkMan implements Serializable {

	private String id;
	private String account;
	private String tel;	
	private String userId;

	public LinkMan() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}
