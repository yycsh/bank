package com.bankcore.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.bankcore.model.account.dao.IAccountDao;
import com.bankcore.model.account.dao.impl.IAccountDaoImpl;


public class LogIn extends HttpServlet{
	public void doPost(HttpServletRequest request,
						HttpServletResponse response) {
		response.setContentType("text/html");
		IAccountDao accDao = new IAccountDaoImpl();
		//System.out.println("logging");
		
		try {
			//若存在未退出用户，使其退出
			Cookie[] cookies = request.getCookies();
			Cookie rpcookie = null;
			if (!(cookies == null)) {
				for (Cookie cookie: cookies) {
					cookie.setPath("/");
					String userName = "";
					if (cookie.getName().equals("username")) {
						try {
							userName = URLDecoder.decode(cookie.getValue(),"utf-8");
							//保存退出到日志
							int userID = accDao.getUserIDByUserName(userName);
							String content = "退出";
							accDao.addLog(userID, content);
							
							cookie.setValue("");
							rpcookie = cookie;
							System.out.println("旧用户"+userName+"cookies已删除");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
			//从请求页面读取用户名和密码
			String userName = request.getParameter("username");
			String passWord = request.getParameter("userpwd");
			//若用户名和密码皆不为空，转码为utf-8
			if (!userName.equals("") && !passWord.equals("")) {
				userName = new String(userName.getBytes("iso-8859-1"),"utf-8");
				passWord = new String(passWord.getBytes("iso-8859-1"),"utf-8");
			}
			
			//\/\/返回页面的不同情况\/\/
			
			//1.若用户名或密码有一个为空，则返回带错误信息的login.jsp页面
			if (userName.equals("") || passWord.equals("")) {
				request.setAttribute("erroinfo",  URLEncoder.encode("请输入用户名和密码","utf-8"));		
				response.addCookie(rpcookie);
				RequestDispatcher view =
						request.getRequestDispatcher("login.jsp");
				try {
					view.forward(request, response);
				} catch (ServletException| IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//2.若用户名或密码与数据库匹配，则返回带username的cookie的main.jsp页面
			else if(accDao.isValidAccount(userName, passWord)) {
				try {
					rpcookie = new Cookie("username", URLEncoder.encode(userName,"utf-8"));
	
					rpcookie.setPath("/");
					rpcookie.setMaxAge(-1);
					response.addCookie(rpcookie);
					System.out.println("cookie setted");
					//保存登录到日志
					int userID = accDao.getUserIDByUserName(userName);
					String content = "登录";
					accDao.addLog(userID, content);
					
					request.setAttribute("username", userName);
					RequestDispatcher view =
						request.getRequestDispatcher("main.jsp");
					view.forward(request, response);
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//3.若信息不匹配
			else {
					try {
						//若帐户名存在，返回带"密码错误"信息的longin.jsp
						if (accDao.isRegisted(userName)) {
							request.setAttribute("erroinfo",  URLEncoder.encode("密码错误","utf-8"));
						} else {
							//若用户名不存在，则返回带"此用户名不存在"信息的login.jsp
							request.setAttribute("erroinfo",  URLEncoder.encode("此用户名不存在","utf-8"));		
						}
						response.addCookie(rpcookie);
						RequestDispatcher view =
								request.getRequestDispatcher("login.jsp");
						view.forward(request, response);
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
}
