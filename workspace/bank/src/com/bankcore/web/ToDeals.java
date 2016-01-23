package com.bankcore.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToDeals extends HttpServlet {
	public void doGet (HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("tosave");
		
		response.setContentType("text/html");
		response.setDateHeader("Expires", -1);
		Cookie[] cookies = request.getCookies();
		
		String d = request.getParameter("d").trim();
		String dest = "/jsp/"+d+"/"+d+".jsp";
		
		boolean has = false;
		for (Cookie cookie: cookies) {
			cookie.setPath("/");
			System.out.println("1:"+cookie.getName()+"2:"+cookie.getValue());
			if (cookie.getName().equals("username")) {
				if (cookie.getValue().equals(""))
					break;
				has = true;
				try {
					System.out.println("found username cookie");
					
					RequestDispatcher view =
							request.getRequestDispatcher(dest);
					view.forward(request, response);
				} catch (IOException | ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("not found username cookie");
		}
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
