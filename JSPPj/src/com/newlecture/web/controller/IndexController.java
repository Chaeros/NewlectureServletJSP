package com.newlecture.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index")
public class IndexController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//WEB-INF라는 디렉토리는 웹에서 절대로 사용자가 요청할 수 없는 폴더이다. 서버에서만 호출 가능
		request
		.getRequestDispatcher("/WEB-INF/view/index.jsp")
		.forward(request, response);
	}
}
