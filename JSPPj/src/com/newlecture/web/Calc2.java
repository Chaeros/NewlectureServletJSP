package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calc2")
public class Calc2 extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request
			, HttpServletResponse response) throws ServletException, IOException {
		//어플리케이션 저장소에 서블릿 값을 저장
		ServletContext application = request.getServletContext();
		
		//UTF-8 형식으로 전송하는 것
		response.setCharacterEncoding("UTF-8");
		//웹 브라우저에 UTF-8 형식으로 읽으라고 하는 것
		response.setContentType("text/html; charset=UTF-8");
		//웹 브라우저가 보낸 입력 값을 UTF-8 형식으로 읽어오는 것(필터로 적용시킬 것임)
		//request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		String v_ = request.getParameter("v");
		String op = request.getParameter("operator");
		
		int v = 0;
		if(!v_.contentEquals(""))	v = Integer.parseInt(v_);
		
		//값 계산
		if(op.equals("=")) {
			int x = (Integer)application.getAttribute("value"); //앞선 서블릿에서의 값
			int y = v;  // 현재 서블릿에서의 값
			String operator = (String)application.getAttribute("op"); // 앞서 입력한 연산자
			
			int result = 0;
			if(operator.equals("+"))
				result = x+y;
			else
				result = x-y;
			
			out.printf("결과 : %d\n",result);
		}
		else //값 저장
		{
			application.setAttribute("value", v);
			application.setAttribute("op", op);
		}
	}
}
