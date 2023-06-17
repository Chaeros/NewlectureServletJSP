package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//web.xml에서 서블릿 
@WebServlet("/hello2")
public class Nana extends HttpServlet{
		@Override
		protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			//UTF-8 형식으로 전송하는 것
			response.setCharacterEncoding("UTF-8");
			//웹 브라우저에 UTF-8 형식으로 읽으라고 하는 것
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			String cnt_ = request.getParameter("cnt");
			
			int cnt = 100;
			if(cnt_ != null && !cnt_.contentEquals(""))
				cnt = Integer.parseInt(cnt_);
			
			for(int i=0;i<cnt;++i)
			{
				out.println((i+1)+": 안녕 Servlet!! <br>");
			}
		}
}
