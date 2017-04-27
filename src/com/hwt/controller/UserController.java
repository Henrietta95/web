package com.hwt.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hwt.authority.Auth;
import com.hwt.authority.AuthClass;
import com.hwt.page.Page;
import com.hwt.po.User;
import com.hwt.service.UserService;
import com.hwt.util.Json;
import com.hwt.util.MD5Encryption;

@AuthClass
@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

//	// 返回json，localhost:8080/zyy2/user/addUser.do?username=111&password=111
//	//@Auth(role = "admin,user", description = "test1")
//	@RequestMapping("addUser")
//	@ResponseBody
//	public User add(String username, String password) {
//		User user = new User();
//		user.setUsername(username);
//		user.setPassword(password);
//		userService.add(user);
//		return user;
//	}



	/**
	 * 用户登陆
	 * 
	 * @param loginName
	 * @param password
	 * @param session
	 * @return
	 */
	@Auth(role = "admin,user", description = "login")
	@SuppressWarnings("finally")
	@ResponseBody()
	@RequestMapping("login")
	public Json login(String loginName, String password,
			HttpSession session) {
	
		Json json = new Json();
		try {
			password = MD5Encryption.getMD5(password);
		
			User user = userService.getByUsername(loginName, password);
			if (user == null) {
				json.success = false;
				json.message = "密码和帐号不可用！";	 
			
			} else {
				session.setAttribute("userSession", user);
				json.success = true;
				json.message = "匹配成功";
				json.href = "success.jsp";
				json.data = user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return json;
		}
	}

	/**
	 * 用户注册
	 * 
	 */
	@Auth(role = "admin,user", description = "register")
	@ResponseBody()
	@SuppressWarnings("finally")
	@RequestMapping("register")
	public Json  register(String loginName, String password,
			HttpSession session) {

		Json json = new Json();
		try {	
		if (userService.getByName(loginName)==true) {
		password = MD5Encryption.getMD5(password);
		User user=new User();
		user.setUsername(loginName);
		user.setPassword(password);
	
		userService.add(user);
		


		session.setAttribute("userSession", user);
		json.success = true;
		json.message = "匹配成功,可用";
		json.href = "success.jsp";
		json.data = user;

	}else{

		json.success = false;
		json.message = "用户名已存在";	 

		}	
		}catch (Exception e) {
		e.printStackTrace();
	} finally {

		return json;
	}
	}
	}
