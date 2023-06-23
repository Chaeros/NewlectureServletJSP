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

@WebServlet("/notice/list")
public class NoticeListController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Notice> list = new ArrayList<>();
		
		String USERNAME = "root";//DBMS접속 시 아이디
		String PASSWORD = "0000";//DBMS접속 시 비밀번호
		String URL = "jdbc:mysql://localhost:3306/newlecture";//DBMS접속할 db명
		String sql = "Select * from notice";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(sql);

			while(rs.next()){
				int id=rs.getInt("ID");
				String title=rs.getString("TITLE");
				Date regdate=rs.getDate("REGDATE");
				String writerId=rs.getString("WRITER_ID");			
				int hit=rs.getInt("HIT");
				String files=rs.getString("FILES");
				String content=rs.getString("CONTENT");
				
				Notice notice = new Notice(id,title,regdate,writerId,hit,files,content);
				list.add(notice);
			}

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
		
		request.setAttribute("list", list);
		request.getRequestDispatcher("/notice/list.jsp")
		   .forward(request, response);
	}
}
