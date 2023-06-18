package com.newlecture.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calculator")
public class Calculator extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//html에서 소문자로 method 형식을 입력했어도 여기서는 반드시 대문자로 GET/POST 비교해줘야한다.
		if(req.getMethod().equals("GET")) {
			System.out.println("GET 요청옴");
		}
		else if(req.getMethod().equals("POST")) {
			System.out.println("POST 요청옴");
		}
		
		//부모의 service 메소드는 GET메소드면 doGet메소드 실행, POST면 doPOST메소드를 실행한다.
		//단, doGet과 doPOST 메소드가 정의되어 있어야 오류가 발생하지 않는다.
		//따라서 위 메소드 정의하지 않을거면 주석처리해야 문제없이 코드가 동작한다.
		//위 service 메소드를 밑의 super.service가 동작하기 이전 동작들을 수행시키는 용도로 사용할 수 있다.
		//service 메소드를 Override하지않으면 알아서 super.service가 동작한다.(service가 라우팅하는 역할)
		//GET방식과 POST방식 둘다 동일한 처리를 한다면 service작성하고 super.service는 없어도 됨
		super.service(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doGET 메소드가 호출되었습니다.");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doPOST 메소드가 호출되었습니다.");
	}
}
