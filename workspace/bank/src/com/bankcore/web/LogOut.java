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
		//��û��cookie�����ص�¼ҳ
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
					//����username���cookie�������˳�����־
					try {
						userName = URLDecoder.decode(cookie.getValue(),"utf-8");
						//�����˳�����־
						int userID = accDao.getUserIDByUserName(userName);
						String content = "�˳�";
						accDao.addLog(userID, content);
						
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				System.out.println("cookies��ɾ��");
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
