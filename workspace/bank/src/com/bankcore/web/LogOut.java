package com.bankcore.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.bankcore.model.account.dao.IAccountDao;
import com.bankcore.model.account.dao.impl.IAccountDaoImpl;

public class LogOut extends HttpServlet {
	public void doGet (HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html");
		IAccountDao accDao = new IAccountDaoImpl();
		System.out.println("logout");
		Cookie[] cookies = request.getCookies();
		//若没有cookie，跳回登录页
		if (cookies == null) {
			try {
				response.sendRedirect("../../login.html");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			for (Cookie cookie: cookies) {
				cookie.setPath("/");
				String userName = "";
				if (cookie.getName().equals("username")) {
					if (cookie.getValue().equals(""))
						break;
					//若有username这个cookie，保存退出到日志
					try {
						userName = URLDecoder.decode(cookie.getValue(),"utf-8");
						//保存退出到日志
						int userID = accDao.getUserIDByUserName(userName);
						String content = "退出";
						accDao.addLog(userID, content);
						
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				System.out.println("cookies已删除");
			}
			RequestDispatcher view =
					request.getRequestDispatcher("login.jsp");
			try {
				view.forward(request, response);
			} catch (ServletException| IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
