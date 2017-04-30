package com.hwt.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.hwt.util.ExcelFile;
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
	@RequestMapping("register")
	public Json  register(String loginName, String password,
			HttpSession session) {

		Json json = new Json();
		if(!loginName.matches("^[a-zA-Z0-9\u4e00-\u9fa5]{6,12}$")){
			json.success = false;
			json.message = "账号必须由英文，中文，数字组成，并且要求在6-12内";	 
			return json;
		}
		if(!password.matches("^[a-zA-Z0-9]{6,}$")){
			json.success = false;
			json.message = "密码必须由英文，数字组成，并且要求大于6位";	 
			return json;
		}
		if (userService.getByName(loginName)==true) {
//		password = MD5Encryption.getMD5(password);
		User user=new User();
		user.setUsername(loginName);
		user.setPassword(password);
		userService.add(user);
		 user = userService.getByUsername(loginName, password);
		session.setAttribute("userSession", user);
		json.success = true;
		json.message = "注册成功";
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
    		json.data="-1";
    		return json;
     }
     List<LinkMan> linkManList= linkManService.selectLinkManByTel(Tel,user.getId());
     if(linkManList.size()!=0){
 		json.success = false;
		json.message = "电话存在重复，请确认后重新添加";	 
		json.data="0";
		return json;
     }else{
    	 LinkMan   	 linkMan=new LinkMan();
    	 linkMan.setAccount(Account);
    	 linkMan.setTel(Tel);
    	 linkMan.setUserId(user.getId());
    	 linkManService.add(linkMan);
    	 json.success = true;
 		json.message = "添加成功";	 
 		return json;
     }
     
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
    		json.data="-1";
    		return json;
     } 
     List<LinkMan> linkManList= linkManService.selectLinkManByTel(Tel,user.getId());
     if(linkManList.size()!=0){
    	 LinkMan linkMan=	 linkManList.get(0);
    	 if(!linkMan.getId().equals(id)){
  		json.success = false;
 		json.message = "电话存在重复，请确认后重新添加";	 
 		json.data="0";
 		return json;}
      }
   LinkMan linkman=  linkManService.getById(id);
   linkman.setAccount(Account);
   linkman.setTel(Tel);
   linkManService.update(linkman);
  
	json.message = "成功";
//	json.data=linkManList;
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
	
	
	@Auth(role = "admin,user", description = "exportExamExcel")
	@RequestMapping("exportExamExcel")
	@ResponseBody
	public Json   exportExcel(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) {
		Json json=new Json();
		User user=(User)session.getAttribute("userSession");
	     if(user==null){
	    		json.success = false;
	    		json.message = "登录已经失效，请重新登录";	 
	    		json.href = "login.html";
	    		return json;
	     } 
	        String title[] = {"通讯录"};
	        List<List<String>> header=new ArrayList<List<String>>(); 
	        //表头数据
	        List<String> header0 =new ArrayList<String>();
	        header0.add("姓名/Name");
	        header0.add("号码/Tel");
	    
	        //表头
	        header.add(header0);
	      
	        
	        //查数据
	        List<List<List<String>>> data = new ArrayList<List<List<String>>>();
	        List<List<String>> data0 =new ArrayList<List<String>>();
	        //个人信息表
	        List<LinkMan> linkManList= linkManService.selectLinkMan(user.getId(),null,null);
	   
	        for (int i = 0; i < linkManList.size(); ++i) {
            	List<String> aList =new ArrayList<String>();
            	aList.add(linkManList.get(i).getAccount());
            	aList.add(linkManList.get(i).getTel());
                data0.add(aList);
            }  
            data.add(data0);
	        ExcelFile ef = new ExcelFile(title, header, data,1);
	        try {
				ef.saveMultisheet(request,response,user.getUsername()+"的通讯录");
			} catch (IOException e) {
				e.printStackTrace();
			}	
	        json.success = true;
    		json.message = "导出成功";	 
    		return json;
	}
}
