package com.hwt.authority;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author hwt he
 *
 */
public class AuthUtil {

	/*
	 * public static void main(String[] args) { String url =
	 * "jdbc:mysql://localhost:3306/mybatistest"; String username = "root";
	 * String password = "root"; try { Connection con =
	 * DriverManager.getConnection(url, username, password);
	 * AuthUtil.initAuth("com.hwt.controller", con); con.close(); } catch
	 * (SQLException se) { System.out.println("鏁版嵁搴撹繛鎺ュけ璐ワ紒"); se.printStackTrace();
	 * } }
	 */

	/**
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void initAuth(String pName, Connection connection)
			throws SQLException {
		try {
			String[] ps = getClassByPackage(pName);
			for (String p : ps) {
				String pc = pName + "."
						+ p.substring(0, p.lastIndexOf(".class"));
				// 寰楀埌浜嗙被鐨刢lass瀵硅薄
				Class clz = Class.forName(pc);
				if (!clz.isAnnotationPresent(AuthClass.class)
						|| !clz.isAnnotationPresent(Controller.class))
					continue;
//				AuthClass authClass = (AuthClass) clz
//						.getAnnotation(AuthClass.class);
//				String defaultRoles = authClass.defaultRole();
				RequestMapping m1 = (RequestMapping) clz
						.getAnnotation(RequestMapping.class);
				String url1 = "";
				if (m1.value() != null) {
					url1 = m1.value()[0];
					if (!url1.startsWith("/"))
						url1 = "/" + url1;
				}
//				Method[] ms = clz.getDeclaredMethods();
//				/*
//				 * 閬嶅巻method鏉ュ垽鏂瘡涓猰ethod涓婇潰鏄惁瀛樺湪鐩稿簲鐨凙uth鏍囩
//				 */
//				Statement statement = connection.createStatement();
//				for (Method m : ms) {
//					if (!m.isAnnotationPresent(Auth.class)
//							|| !m.isAnnotationPresent(RequestMapping.class))
//						continue;
//					Auth auth = m.getAnnotation(Auth.class);
//					String roles = auth.role();
//					String description = auth.description();
//					if (roles.equals(""))
//						roles = defaultRoles;
//					RequestMapping m2 = m.getAnnotation(RequestMapping.class);
//					String url2 = "";
//					if (m2.value() != null) {
//						url2 = m2.value()[0];
//						if (!url2.startsWith("/"))
//							url2 = "/" + url2;
//					}
//					String url = url1 + url2;
//					String checkRP, checkR, checkP, insertR, insertP, insertRP;
//					for (String role : roles.split(",")) {
//						checkRP = "select * from `role_permit` rp,`role` r,`permit` p where r.id = rp.role_id and p.id = rp.permit_id and r.name = '"
//								+ role + "' and p.url = '" + url + "' ";
//						if (!statement.executeQuery(checkRP).next()) {
//							checkR = "select * from `role` where name = '"
//									+ role + "'";
//							if (!statement.executeQuery(checkR).next()) {
//								insertR = "insert into role (id,name) values ('"
//										+ UUID.randomUUID().toString()
//										+ "', '"
//										+ role + "')";
//								statement.execute(insertR);
//							}
//							checkP = "select * from `permit` where url = '"
//									+ url + "'";
//							if (!statement.executeQuery(checkP).next()) {
//								insertP = "insert into `permit` (id,url,description) values ('"
//										+ UUID.randomUUID().toString()
//										+ "', '"
//										+ url + "' , '" + description + "' )";
//								statement.execute(insertP);
//							}
//							insertRP = "INSERT into `role_permit` (id,role_id,permit_id) values ('"
//									+ UUID.randomUUID().toString()
//									+ "',(select id from `role` where name='"
//									+ role
//									+ "'), (select id from `permit` where url= '"
//									+ url + "')" + ")";
//							statement.execute(insertRP);
//						}
//					}
//				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鏍规嵁鍖呰幏鍙栨墍鏈夌殑绫�
	 * 
	 * @param pName
	 * @return
	 */
	private static String[] getClassByPackage(String pName) {
		String pr = pName.replace(".", "/");
		String pp = AuthUtil.class.getClassLoader().getResource(pr).getPath();
		File file = new File(pp);
		String[] fs = file.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(".class"))
					return true;
				return false;
			}
		});
		return fs;
	}

}
