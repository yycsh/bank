package com.bankcore.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankcore.model.account.dao.IAccountDao;
import com.bankcore.model.account.dao.impl.IAccountDaoImpl;

public class ToLogs extends HttpServlet{
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html");
		IAccountDao accDao = new IAccountDaoImpl();
		//定义是否有不为空的username的cookie
		boolean has = false;
		System.out.println("getLogs");
		
		Cookie[] cookies = request.getCookies();
		
		for (Cookie cookie: cookies) {
			cookie.setPath("/");
			System.out.println("1:"+cookie.getName()+"2:"+cookie.getValue());
			if (cookie.getName().equals("username")) {
				if (cookie.getValue().equals(""))
					break;
				has = true;
				//若有username的cookie 读取日志并绑定到logs.jsp
				try {
					System.out.println("found username cookie");
					String userName = URLDecoder.decode(cookie.getValue(),"utf-8");
					
					int userID = accDao.getUserIDByUserName(userName);
					List<String> logs = accDao.readLog(userID);
					request.setAttribute("logs", logs);
					RequestDispatcher view =
							request.getRequestDispatcher("/jsp/logs/logs.jsp");
					view.forward(request, response);
				} catch (IOException | ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("not found username cookie");
		}
		//若没有，返回登录页
		if (!has) {
			System.out.println("用户已退出");
			try {
				RequestDispatcher view =
						request.getRequestDispatcher("login.html");
				view.forward(request, response);
			} catch (IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
