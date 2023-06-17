package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add2")
public class Add2 extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request
			, HttpServletResponse response) throws ServletException, IOException {
		//UTF-8 형식으로 전송하는 것
		response.setCharacterEncoding("UTF-8");
		//웹 브라우저에 UTF-8 형식으로 읽으라고 하는 것
		response.setContentType("text/html; charset=UTF-8");
		//웹 브라우저가 보낸 입력 값을 UTF-8 형식으로 읽어오는 것(필터로 적용시킬 것임)
		//request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		String[] num_ = request.getParameterValues("num");
		
		int result=0;
		
		for(int i=0;i<num_.length;++i) {
			int num = Integer.parseInt(num_[i]);
			result+=num;
		}
		
		out.printf("결과 : %d\n" ,result);
	}
}
