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
			//���û��������롢ȷ������Բ�Ϊ�գ�ת��Ϊutf-8
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
			//���û����������ȷ��������һ��Ϊ�գ����ش�"�������û��������뼰ȷ������"��register.jsp
			if (userName.equals("") || userPwd1.equals("")
				|| userPwd2.equals(""))
			{
				request.setAttribute("erroinfo",  URLEncoder.encode("�������û��������뼰ȷ������","utf-8"));		
				RequestDispatcher view =
						request.getRequestDispatcher("register.jsp");
				try {
					view.forward(request, response);
				} catch (ServletException| IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//���û����Ѿ�ע����ˣ����ش�"�û�����ע��"��Ϣ��register.jsp
			else if (accDao.isRegisted(userName)) {
				request.setAttribute("erroinfo",  URLEncoder.encode("�û�����ע��","utf-8"));		
				RequestDispatcher view =
						request.getRequestDispatcher("register.jsp");
				try {
					view.forward(request, response);
				} catch (ServletException| IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//��������ȷ�����벻ͬ�����ش�"������ȷ������Ҫһ��"��Ϣ��register.jsp
			else if (!userPwd1.equals(userPwd2)){ 
				request.setAttribute("erroinfo",  URLEncoder.encode("������ȷ������Ҫһ��","utf-8"));		
				RequestDispatcher view =
						request.getRequestDispatcher("register.jsp");
				try {
					view.forward(request, response);
				} catch (ServletException| IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//���û���δע�ᣬ����������ͬ��������û�����ת�ص�¼ҳ
			else {
				AccountBean newUser = new AccountBean();
				newUser.setUserName(userName);
				newUser.setUserPassWord(userPwd1);
				newUser.setUserRealName(userRealName);
				if(accDao.addAccount(newUser)) {
					request.setAttribute("registedinfo",  URLEncoder.encode("�ѳɹ�ע�ᣡ���¼","utf-8"));		
					RequestDispatcher view =
							request.getRequestDispatcher("login.jsp");
					try {
						view.forward(request, response);
					} catch (ServletException| IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					request.setAttribute("erroinfo",  URLEncoder.encode("δע��ɹ�","utf-8"));		
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
