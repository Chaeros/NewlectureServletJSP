package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
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
		//쿠키들을 배열로 읽어옴
		Cookie[] cookies = request.getCookies();
		
		//UTF-8 형식으로 전송하는 것
		response.setCharacterEncoding("UTF-8");
		//웹 브라우저에 UTF-8 형식으로 읽으라고 하는 것
		response.setContentType("text/html; charset=UTF-8");
		
		String value = request.getParameter("value");
		String operator = request.getParameter("operator");
		String dot = request.getParameter("dot");
		
		String exp="";
		if(cookies!=null) {
			for(Cookie c : cookies) {
				if(c.getName().equals("exp")) {
					exp=c.getValue();
					break;
				}
			}
		}
		
		if(operator!=null && operator.equals("=")){
			//javascript로 계산
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			try {
				exp=String.valueOf(engine.eval(exp));
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(operator!=null && operator.equals("C")){
			//exp 내용 비우기
			exp="";
		}
		else {
			exp+=(value==null)?"":value;
			exp+=(operator==null)?"":operator;
			exp+=(dot==null)?"":dot;
		}
		
		
	
		
		Cookie expCookie = new Cookie("exp",exp);
		
		// 이걸 입력해야 쿠키가 바로 소멸된다.
		if(operator!=null && operator.equals("C"))
			expCookie.setMaxAge(0); 
		
		expCookie.setPath("/");
		//이번 서블릿 실행 후 표기해줄 페이지를 지정
		response.addCookie(expCookie);
		response.sendRedirect("calcpage");
		
	}
}
