package com.bankcore.web.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankcore.model.account.dao.IAccountDao;
import com.bankcore.model.account.dao.impl.IAccountDaoImpl;

/*
 * 验证用户名是否已被注册
 * 若已被注册返回isregistered = true
 */
public class CheckName extends HttpServlet{
	public void doGet(HttpServletRequest request,
					HttpServletResponse response) {
		//
		IAccountDao iADao = new IAccountDaoImpl();
		String name = request.getParameter("name");
		String userName;
		try {
			userName = new String(name.getBytes("iso-8859-1"),"utf-8");
			System.out.println("待注册用户名："+userName);
			boolean isRegistered = iADao.isRegisted(userName);
		
			//
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control","no-cache");
			PrintWriter out = response.getWriter();
			StringBuffer results = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			results.append("<isregistered id=\"isreg\">");
			results.append(isRegistered);
			results.append("</isregistered>");
			out.write(results.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
