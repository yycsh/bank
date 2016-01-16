package com.bankcore.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.bankcore.model.account.bean.AccountBean;
import com.bankcore.model.account.dao.IAccountDao;
import com.bankcore.model.account.dao.impl.IAccountDaoImpl;

public class Register extends HttpServlet{
	public void doPost (HttpServletRequest request,
						HttpServletResponse response) {
		System.out.println("registing");
		response.setContentType("text/html");
		IAccountDao accDao = new IAccountDaoImpl();
		try {
			String userName = request.getParameter("username");
			String userRealName = request.getParameter("userrealname");
			String userPwd1 = request.getParameter("userpwd1");
			String userPwd2 = request.getParameter("userpwd2");
			//若用户名、密码、确认密码皆不为空，转码为utf-8
			if (!userName.equals("") && !userPwd1.equals("")
				&& !userPwd2.equals(""))
			{
				userName = new String(userName.getBytes("iso-8859-1"),"utf-8");
				userPwd1 = new String(userPwd1.getBytes("iso-8859-1"),"utf-8");
				userPwd2 = new String(userPwd2.getBytes("iso-8859-1"),"utf-8");
				if (!userRealName.equals(""))
					userRealName = new String(userRealName.getBytes("iso-8859-1"),"utf-8");
				else userRealName = "";
			}
			//若用户名或密码或确认密码有一个为空，返回带"请输入用户名、密码及确认密码"的register.jsp
			if (userName.equals("") || userPwd1.equals("")
				|| userPwd2.equals(""))
			{
				request.setAttribute("erroinfo",  URLEncoder.encode("请输入用户名、密码及确认密码","utf-8"));		
				RequestDispatcher view =
						request.getRequestDispatcher("register.jsp");
				try {
					view.forward(request, response);
				} catch (ServletException| IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//若用户名已经注册过了，返回带"用户名已注册"信息的register.jsp
			else if (accDao.isRegisted(userName)) {
				request.setAttribute("erroinfo",  URLEncoder.encode("用户名已注册","utf-8"));		
				RequestDispatcher view =
						request.getRequestDispatcher("register.jsp");
				try {
					view.forward(request, response);
				} catch (ServletException| IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//若密码与确认密码不同，返回带"密码与确认密码要一致"信息的register.jsp
			else if (!userPwd1.equals(userPwd2)){ 
				request.setAttribute("erroinfo",  URLEncoder.encode("密码与确认密码要一致","utf-8"));		
				RequestDispatcher view =
						request.getRequestDispatcher("register.jsp");
				try {
					view.forward(request, response);
				} catch (ServletException| IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//若用户名未注册，两次密码相同：则添加用户后跳转回登录页
			else {
				AccountBean newUser = new AccountBean();
				newUser.setUserName(userName);
				newUser.setUserPassWord(userPwd1);
				newUser.setUserRealName(userRealName);
				if(accDao.addAccount(newUser)) {
					request.setAttribute("registedinfo",  URLEncoder.encode("已成功注册！请登录","utf-8"));		
					RequestDispatcher view =
							request.getRequestDispatcher("login.jsp");
					try {
						view.forward(request, response);
					} catch (ServletException| IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					request.setAttribute("erroinfo",  URLEncoder.encode("未注册成功","utf-8"));		
					RequestDispatcher view =
							request.getRequestDispatcher("register.jsp");
					try {
						view.forward(request, response);
					} catch (ServletException| IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} 
	}
}
