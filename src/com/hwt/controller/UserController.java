package com.hwt.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hwt.authority.Auth;
import com.hwt.authority.AuthClass;
import com.hwt.po.LinkMan;
import com.hwt.po.User;
import com.hwt.service.LinkManService;
import com.hwt.service.UserService;
import com.hwt.util.Json;

@AuthClass
@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private LinkManService linkManService;

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
	 * 用户进入页面获取用户信息
	 * @param session
	 * @return
	 */
	@Auth(role = "admin,user", description = "getUser")
	@RequestMapping("getUser")
	@ResponseBody
	public Json getUser(	HttpSession session) {
		Json json = new Json();
		User user=null;
		user=(User)session.getAttribute("userSession");
		if(user!=null){
		json.data=user;
		json.success=true;}else{
			json.success=false;
		}
		return json;
	}
	/**
	 * 用户退出登录
	 * @param session
	 * @return
	 */
	@Auth(role = "admin,user", description = "getUser")
	@RequestMapping("OutUser")
	@ResponseBody
	public Json OutUser(	HttpSession session) {
		Json json = new Json();
		session.removeAttribute("userSession");
		json.success=true;
		return json;
	}

	/**
	 * 用户更改密码
	 * 
	 * @param oldPassowrd
	 * @param newPassword
	 * @param session
	 * @return
	 */
	@Auth(role = "admin,user", description = "changePassword")
	@ResponseBody()
	@RequestMapping("changePassword")
	public Json changePassword(String oldPassowrd, String newPassword,
			HttpSession session) {
		Json json = new Json();
		User user=(User)session.getAttribute("userSession");
				if(user==null){
					json.success = false;
					json.message = "未登录!请登录后重新选择修改密码";
					json.href = "login.html";
					json.data="true";//跳转
	}else 
		if(user.getPassword().equals(oldPassowrd)){
		user.setPassword(newPassword);
		userService.update(user);
		json.success = true;
		json.message = "修改密码成功，请重新登录";
		session.removeAttribute("userSession");
		json.href = "login.html";
		json.data="true";//跳转
	}else {
		json.success = true;
		json.message = "你输入旧密码错误！";
		json.href = "changepassword.html";
		json.data="false";//不跳转
	}
				return json;
	}

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
//			password = MD5Encryption.getMD5(password);
		
			User user = userService.getByUsername(loginName, password);
			if (user == null) {
				json.success = false;
				json.message = "密码和帐号不可用！";	 
			
			} else {
				session.setAttribute("userSession", user);
				json.success = true;
				json.message = "匹配成功";
				json.href = "index.html";
				json.data = user;
			}
			return json;
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
		if (userService.getByName(loginName)==true) {
//		password = MD5Encryption.getMD5(password);
		User user=new User();
		user.setUsername(loginName);
		user.setPassword(password);
		userService.add(user);
		session.setAttribute("userSession", user);
		json.success = true;
		json.message = "匹配成功,可用";
		json.href = "index.html";
		json.data = user;
	}else{
		json.success = false;
		json.message = "用户名已存在";	 
		}	

		return json;
	}
	/**
	 * 用户添加联系人
	 * 
	 */
	@Auth(role = "admin,user", description = "AddLinkMan")
	@ResponseBody()
	@RequestMapping("AddLinkMan")
	public Json  AddLinkMan(String Account, String Tel,
			HttpSession session) {
		Json json = new Json();
		User user=(User)session.getAttribute("userSession");
     if(user==null){
    		json.success = false;
    		json.message = "登录已经失效，请重新登录";	 
    		json.href = "login.html";
    		return json;
     }
		

		return json;
	}
	
	/**
	 * 用户查询联系人
	 * 
	 */
	@Auth(role = "admin,user", description = "AddLinkMan")
	@ResponseBody()
	@RequestMapping("selectLinkMan")
	public Json  selectLinkMan(String Account, String Tel,
			HttpSession session) {
		Json json = new Json();
		User user=(User)session.getAttribute("userSession");
     if(user==null){
    		json.success = false;
    		json.message = "登录已经失效，请重新登录";	 
    		json.href = "login.html";
    		return json;
     } 
    List<LinkMan> linkManList=  linkManService.selectLinkMan(user.getId(),Account,Tel);
    json.success = true;
	json.message = "成功";	 
	json.data=linkManList;
		return json;
	}
	/**
	 * 用户编辑联系人
	 * 
	 */
	@Auth(role = "admin,user", description = "editLinkMan")
	@ResponseBody()
	@RequestMapping("editLinkMan")
	public Json  editLinkMan(String Account, String Tel,String id,
			HttpSession session) {
		Json json = new Json();
		User user=(User)session.getAttribute("userSession");
     if(user==null){
    		json.success = false;
    		json.message = "登录已经失效，请重新登录";	 
    		json.href = "login.html";
    		return json;
     } 
   LinkMan linkman=  linkManService.getById(id);
   linkman.setAccount(Account);
   linkman.setTel(Tel);
   linkManService.update(linkman);
   List<LinkMan> linkManList=  linkManService.selectLinkMan(user.getId(),null,null);
    json.success = true;
	json.message = "成功";
	json.data=linkManList;
		return json;
	}

	/**
	 * 用户删除联系人
	 * 
	 */
	@Auth(role = "admin,user", description = "deleteLinkMan")
	@ResponseBody()
	@RequestMapping("deleteLinkMan")
	public Json  deleteLinkMan(String id,
			HttpSession session) {
		Json json = new Json();
		User user=(User)session.getAttribute("userSession");
     if(user==null){
    		json.success = false;
    		json.message = "登录已经失效，请重新登录";	 
    		json.href = "login.html";
    		return json;
     } 
   if(1!=linkManService.deleteById(id)){  json.success = false;json.message = "失败";return json;}
   List<LinkMan> linkManList=  linkManService.selectLinkMan(user.getId(),null,null);
    json.success = true;
	json.message = "成功";
	json.data=linkManList;
		return json;
	}
}
