package com.bankcore.web.transfer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankcore.model.account.dao.IAccountDao;
import com.bankcore.model.account.dao.impl.IAccountDaoImpl;

public class Transfer extends HttpServlet {
	public void doPost (HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("transfer");
		response.setContentType("text/html");
		IAccountDao accDao = new IAccountDaoImpl();
		Cookie[] cookies = request.getCookies();
		
		String tName = request.getParameter("taccount");
		try {
			tName = new String(tName.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sTAmount = request.getParameter("tamount");
		
		BigDecimal tAmount = new BigDecimal(0);
		if (!sTAmount.matches("[0-9]+[\\.]?+[0-9]*")) {
			sTAmount = "notNum";
		} else{
			tAmount = new BigDecimal(sTAmount);
		}
		
		
		if (!accDao.isRegisted(tName)) {
			try {
				request.setAttribute("username-erro",  URLEncoder.encode("�޴��û�","utf-8"));
				RequestDispatcher view =
						request.getRequestDispatcher("/jsp/transfer/transfer.jsp");
				view.forward(request, response);
			} catch (IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (sTAmount.equals("notNum")) {
			try {
				request.setAttribute("erro",  URLEncoder.encode("��������","utf-8"));
				RequestDispatcher view =
						request.getRequestDispatcher("/jsp/transfer/transfer.jsp");
				view.forward(request, response);
			} catch (IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (tAmount.floatValue() <= 0) {
			try {
				request.setAttribute("erro",  URLEncoder.encode("������","utf-8"));
				RequestDispatcher view =
						request.getRequestDispatcher("/jsp/transfer/transfer.jsp");
				view.forward(request, response);
			} catch (IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			boolean has = false;
			for (Cookie cookie: cookies) {
				cookie.setPath("/");
				//System.out.println("1:"+cookie.getName()+"2:"+cookie.getValue());
				if (cookie.getName().equals("username")) {
						has = true;
					
						System.out.println("found username cookie");
						String userName = "";
						try { 
							userName = URLDecoder.decode(cookie.getValue(),"utf-8");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//ȡ���û�ID
						int userID = accDao.getUserIDByUserName(userName);
						
						BigDecimal balance = accDao.getBalanceByuserID(userID);
						System.out.println("yu  e:"+balance.doubleValue());
						
						//ȡ��ת��ID
						
						int toID = accDao.getUserIDByUserName(tName);
						
						if (balance.doubleValue() < tAmount.doubleValue()) {
							try {
								request.setAttribute("erro", URLEncoder.encode("����","utf-8"));
								RequestDispatcher view =
										request.getRequestDispatcher("/jsp/transfer/transfer.jsp");
								view.forward(request, response);
							} catch (IOException | ServletException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (accDao.saveMoney(toID, tAmount)) {
							try {
								//����ת����־
								String content = "ת�˵�" + tName;
								accDao.addLog(userID, content+": "+tAmount+"Ԫ");
								
								request.setAttribute("done",  URLEncoder.encode("ת����ɣ���ѡ�����...","utf-8"));
								RequestDispatcher view =
										request.getRequestDispatcher("/main.jsp");
								view.forward(request, response);
							} catch (IOException | ServletException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							try {
								request.setAttribute("erro", URLEncoder.encode("ת��ʧ��","utf-8"));
								RequestDispatcher view =
										request.getRequestDispatcher("/main.jsp");
								view.forward(request, response);
							} catch (IOException | ServletException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					
				}
				System.out.println("not found username cookie");
			}
			if (!has) {
				System.out.println("�û����˳�");
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
}