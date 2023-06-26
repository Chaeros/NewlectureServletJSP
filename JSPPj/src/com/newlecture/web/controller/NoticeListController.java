package com.newlecture.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

@WebServlet("/notice/list")
public class NoticeListController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String field_=request.getParameter("f");
		String query_=request.getParameter("q");
		// page에 int를 사용하면 안되는데, 이유는 아무 입력이 없는 경우 null과 비교했을 때 오류가 발생하기 때문
		// 따라서 빈문자열을 입력할 수 있는 String 타입을 사용한다.
		String page_=request.getParameter("p");
		
		String field="TITLE";
		if(field_!=null && !field_.equals(""))
			field=field_;
		
		String query="";
		if(query_!=null && !query_.equals(""))
			query=query_;
		
		int page=1;
		if(page_!=null && !page_.equals(""))
			page=Integer.parseInt(page_);
		
		NoticeService service = new NoticeService();
		List<Notice> list = service.getNoticeList(field,query,page);
		int count = service.getNoticeCount(field,query);
		
		request.setAttribute("list", list);
		request.setAttribute("count",count);
		
		//WEB-INF라는 디렉토리는 웹에서 절대로 사용자가 요청할 수 없는 폴더이다. 서버에서만 호출 가능
		request.getRequestDispatcher("/WEB-INF/view/notice/list.jsp")
		   .forward(request, response);
	}
}
