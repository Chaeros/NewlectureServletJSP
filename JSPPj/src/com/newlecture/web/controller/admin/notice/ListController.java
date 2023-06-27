package com.newlecture.web.controller.admin.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;
import com.newlecture.web.service.NoticeService;

@WebServlet("/admin/board/notice/list")
public class ListController extends HttpServlet{
	// url이 있지만 받아 줄 수 없는 메소드가 호출되면 405
	// 애초에 url이 없으면 404
	// 내가 url도 있고 메소드도 있지만 권한이 없으면 403
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] openIds = request.getParameterValues("open-id");
		String[] delIds = request.getParameterValues("del-id");
		String cmd = request.getParameter("cmd");
		String ids_ = request.getParameter("ids");
		//trim을 통해 앞 문자열의 앞뒤 공백을 지움
		String[] ids = ids_.trim().split(" ");
		
		NoticeService service = new NoticeService();
		
		switch(cmd) {
		case "일괄공개":
			for(String openId:openIds)
				System.out.printf("open id : %s\n",openId);
			
			//배열을 리스트 형식으로 바꿔주는 함수 asList
			List<String> oids = Arrays.asList(openIds);
			
			//Arrays는 정적 객체로서 여기서 무언가 추가하고 더하여 길이를 늘릴 수 없다.
			//따라서 추가 삭제에는 단독으로 사용이 어렵고 ArrayList를 통해 동적 객체로 만들어줘야함
			List<String> cids = new ArrayList(Arrays.asList(ids));
			cids.removeAll(oids);
			System.out.println(Arrays.asList(ids));
			System.out.println(oids);
			System.out.println(cids);
			
			service.pubNoticeAll(oids,cids); //UPDATE NOTICE SET PUB=1 WHERE ID IN (...);
			//service.closeNoticeList(clsIds); //
			
			break;
		case "일괄삭제":
			
			int[] ids1 = new int[delIds.length];
			for(int i=0;i<delIds.length;i++)
				ids1[i]=Integer.parseInt(delIds[i]);
			
			int result = service.deleteNoticeAll(ids1);
			break;
		}
		
		//doGet 메소드 재요청하여 호출시킴, 따라서 doGet 맨 밑줄의 Dispatcher를 통해 페이지 실행시킨다.
		//sendRedirect : 다른 페이지로 이동시킴
		response.sendRedirect("list");
	}
	
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
		List<NoticeView> list = service.getNoticeList(field,query,page);
		int count = service.getNoticeCount(field,query);
		
		request.setAttribute("list", list);
		request.setAttribute("count",count);
		
		//WEB-INF라는 디렉토리는 웹에서 절대로 사용자가 요청할 수 없는 폴더이다. 서버에서만 호출 가능
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp")
		   .forward(request, response);
	}
}
