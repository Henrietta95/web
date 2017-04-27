package com.hwt.service;

import com.hwt.po.User;

public interface UserService extends BaseService<User, String> {
	public User getByUsername(String username,String password);
	public boolean getByName(String username);
}
