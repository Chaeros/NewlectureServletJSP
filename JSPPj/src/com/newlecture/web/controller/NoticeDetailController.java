package com.newlecture.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// @WebServlet("/notice/detail")을 통해서 detail.jsp가 실행됨
@WebServlet("/notice/detail")
public class NoticeDetailController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));

		String USERNAME = "root";//DBMS접속 시 아이디
		String PASSWORD = "0000";//DBMS접속 시 비밀번호
		String URL = "jdbc:mysql://localhost:3306/newlecture";//DBMS접속할 db명
		String sql = "Select * from notice where id=?";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(URL, USERNAME, PASSWORD);
			PreparedStatement st=con.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs=st.executeQuery();

			rs.next();

			String title=rs.getString("TITLE");
			Date regdate=rs.getDate("REGDATE");
			String writerId=rs.getString("WRITER_ID");			
			int hit=rs.getInt("HIT");
			String files=rs.getString("FILES");
			String content=rs.getString("CONTENT");
			
			request.setAttribute("title",title);
			request.setAttribute("regdate",regdate);
			request.setAttribute("writerId",writerId);
			request.setAttribute("hit",hit);
			request.setAttribute("files",files);
			request.setAttribute("content",content);

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 현재 홈 디렉터리가 WebContent
		// notice에 있는 detail.jsp를 요청하면서 현재 사용하고 있는 저장소의 위치인
		// request와 출력도구 response를 이어서 공유 사용할 수 있게끔 전송함
		request.getRequestDispatcher("/notice/detail.jsp")
			   .forward(request, response);
	}
}
