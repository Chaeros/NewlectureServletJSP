package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/calc3")
public class Calc3 extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request
			, HttpServletResponse response) throws ServletException, IOException {
		//어플리케이션 저장소에 서블릿 값을 저장
		ServletContext application = request.getServletContext();
		
		//세션을 이용하여 상태유지
		HttpSession session = request.getSession();
		
		//쿠키들을 배열로 읽어옴
		Cookie[] cookies = request.getCookies();
		
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
			//int x = (Integer)application.getAttribute("value"); //앞선 서블릿에서의 값
			//int x = (Integer)session.getAttribute("value"); //앞선 서블릿에서의 값
			int x=0;
			
			for(Cookie c : cookies) {
				if(c.getName().equals("value")) {
					x=Integer.parseInt(c.getValue());
					break;
				}
			}
			
			int y = v;  // 현재 서블릿에서의 값
			//String operator = (String)application.getAttribute("op"); // 앞서 입력한 연산자
			//String operator = (String)session.getAttribute("op"); // 앞서 입력한 연산자
			String operator="";
			
			for(Cookie c : cookies) {
				if(c.getName().equals("op")) {
					operator=c.getValue();
					break;
				}
			}
			
			int result = 0;
			if(operator.equals("+"))
				result = x+y;
			else
				result = x-y;
			
			out.printf("결과 : %d\n",result);
		}
		else //값 저장
		{
			//application.setAttribute("value", v);
			//application.setAttribute("op", op);
			//session.setAttribute("value", v);
			//session.setAttribute("op", op);
			
			//쿠키를 생성하고 클라이언트에게 전송해준다.
			//쿠키안에는 문자열만 저장시킬 수 있다.
			Cookie valueCookie = new Cookie("value", String.valueOf(v));
			Cookie opCookie = new Cookie("op", op);
			
			valueCookie.setPath("/calc2");
			//setMaxAge는 초단위로 아래는 24시간을 의미한다.
			valueCookie.setMaxAge(24*60*60);
			opCookie.setPath("/calc2");
			
			response.addCookie(valueCookie);
			response.addCookie(opCookie);
			
			//이번 서블릿 실행 후 표기해줄 페이지를 지정
			response.sendRedirect("calc2.html");
		}
	}
}
