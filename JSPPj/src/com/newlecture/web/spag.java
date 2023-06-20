package com.newlecture.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/spag")
public class spag extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = 0;
		String num_ = request.getParameter("n");
		if(num_ != null && !num_.equals(""))
			num = Integer.parseInt(num_);
		
		String result;
		
		if(num%2==1)
			result="홀수";
		else
			result="짝수";
		
		// foward를 통해 주고 받은 데이터를 저장소에 저장
		request.setAttribute("result", result);
		
		String[] names = {"newlec","dragon"};
		request.setAttribute("names", names);
		
		Map<String, Object> notice = new HashMap<String, Object>();
		notice.put("id", 1);
		notice.put("title","EL은 좋아요");
		request.setAttribute("notice", notice);
		
		//foward : 위 내용을 다음에 전달할 서블릿에 이어붙여 동작하도록 함
		//	- request 필드는 faward를 통해 현재 클래스와 전송하는 서블릿 사이의 저장소 역할을 수행한다. 
		//redirect : 위 내용과 상관없이 새로운 페이지 요청함
		
		//spag.jsp에 위 내용을 전달
		RequestDispatcher dispatcher 
			= request.getRequestDispatcher("spag.jsp");
		dispatcher.forward(request, response); // 보냄
		
	}
}
