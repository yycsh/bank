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
			//������δ�˳��û���ʹ���˳�
			Cookie[] cookies = request.getCookies();
			Cookie rpcookie = null;
			if (!(cookies == null)) {
				for (Cookie cookie: cookies) {
					cookie.setPath("/");
					String userName = "";
					if (cookie.getName().equals("username")) {
						try {
							userName = URLDecoder.decode(cookie.getValue(),"utf-8");
							//�����˳�����־
							int userID = accDao.getUserIDByUserName(userName);
							String content = "�˳�";
							accDao.addLog(userID, content);
							
							cookie.setValue("");
							rpcookie = cookie;
							System.out.println("���û�"+userName+"cookies��ɾ��");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
			//������ҳ���ȡ�û���������
			String userName = request.getParameter("username");
			String passWord = request.getParameter("userpwd");
			//���û���������Բ�Ϊ�գ�ת��Ϊutf-8
			if (!userName.equals("") && !passWord.equals("")) {
				userName = new String(userName.getBytes("iso-8859-1"),"utf-8");
				passWord = new String(passWord.getBytes("iso-8859-1"),"utf-8");
			}
			
			//\/\/����ҳ��Ĳ�ͬ���\/\/
			
			//1.���û�����������һ��Ϊ�գ��򷵻ش�������Ϣ��login.jspҳ��
			if (userName.equals("") || passWord.equals("")) {
				request.setAttribute("erroinfo",  URLEncoder.encode("�������û���������","utf-8"));		
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
			//2.���û��������������ݿ�ƥ�䣬�򷵻ش�username��cookie��main.jspҳ��
			else if(accDao.isValidAccount(userName, passWord)) {
				try {
					rpcookie = new Cookie("username", URLEncoder.encode(userName,"utf-8"));
	
					rpcookie.setPath("/");
					rpcookie.setMaxAge(-1);
					response.addCookie(rpcookie);
					System.out.println("cookie setted");
					//�����¼����־
					int userID = accDao.getUserIDByUserName(userName);
					String content = "��¼";
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
			//3.����Ϣ��ƥ��
			else {
					try {
						//���ʻ������ڣ����ش�"�������"��Ϣ��longin.jsp
						if (accDao.isRegisted(userName)) {
							request.setAttribute("erroinfo",  URLEncoder.encode("�������","utf-8"));
						} else {
							//���û��������ڣ��򷵻ش�"���û���������"��Ϣ��login.jsp
							request.setAttribute("erroinfo",  URLEncoder.encode("���û���������","utf-8"));		
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
