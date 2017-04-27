package com.hwt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwt.mapper.UserMapper;
import com.hwt.po.User;
import com.hwt.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements
		UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(userMapper);
	}
	public User getByUsername(String username,String password) {
	User user=userMapper.getbyUsername(username);
	if(user==null)return null;
	if(password.equals(user.getPassword()))return user;
	else return null;
	}
	public boolean getByName(String username){
		 if(userMapper.getbyUsername(username)==null)return true;
		 else return false;
	}
}
