package com.newlecture.app.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.app.entity.Notice;

public class NoticeService {
	private String url = "jdbc:oracle:thin:@118.42.165.226:1521/xepdb1";
	private String uid = "newlec";
	private String pwd = "0000";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	
	public List<Notice> getList(int page, String field, String query) throws ClassNotFoundException, SQLException{
		
		int start=1+(page-1)*10;
		int end=10*page;
		
		//field를 ?와 st.setString으로 나타내지 않는 이유는, 그렇게하면 값 양끝에 따옴표가 붙기떄문
		String sql = "SELECT * FROM NOTICE_VIEW WHERE "+field+" LIKE ? AND NUM BETWEEN ? AND ?";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url,uid,pwd);
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+query+"%");
		st.setInt(2, start);
		st.setInt(3, end);
		ResultSet rs = st.executeQuery();
		
		List<Notice> list = new ArrayList<Notice>();
		
		while(rs.next()) {
			int id=rs.getInt("ID");
			String title=rs.getString("TITLE");
			String writerId=rs.getString("WRITER_ID");			
			Date regDate=rs.getDate("REGDATE");
			String content=rs.getString("CONTENT");
			int hit=rs.getInt("HIT");
			String files = rs.getString("FILES");
			
			Notice notice = new Notice(
					id,title,writerId,regDate,content,hit,files);
			
			list.add(notice);
		}
		
		rs.close();
		st.close();
		con.close();
		
		return list;
	}
	
	public int getCount() throws ClassNotFoundException, SQLException {
		int count=0;
		String sql = "SELECT COUNT(ID) COUNT FROM NOTICE";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url,uid,pwd);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		if(rs.next())
			count=rs.getInt("COUNT");
		
		rs.close();
		st.close();
		con.close();
		
		return count;
	}
	
	public int insert(Notice notice) throws ClassNotFoundException, SQLException {
		String title=notice.getTitle();
		String writerId=notice.getWriterId();
		String content=notice.getContent();
		String files=notice.getFiles();
		
		String sql = "INSERT INTO notice(" + 
				"    title," + 
				"    writer_id," + 
				"    content," + 
				"    files" + 
				")VALUES(?,?,?,?)";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url,uid,pwd);
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, title);
		st.setString(2, writerId);
		st.setString(3, content);
		st.setString(4, files);
		
		int result = st.executeUpdate();
		
		System.out.println(result);
		
		st.close();
		con.close();
		return result;
	}
	
	public int update(Notice notice) throws ClassNotFoundException, SQLException {
		String title=notice.getTitle();
		String content=notice.getContent();
		String files=notice.getFiles();
		int id = notice.getId();
		
		String sql = "UPDATE NOTICE " + 
				"	SET" + 
				"    TITLE=?," + 
				"    CONTENT=?," + 
				"    FILES=?" + 
				"WHERE ID=?";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url,uid,pwd);
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, title);
		st.setString(2, content);
		st.setString(3, files);
		st.setInt(4, id);
		
		int result = st.executeUpdate();
		
		st.close();
		con.close();
		return result;
	}
	
	public int delete(int id) throws ClassNotFoundException, SQLException {
		
		String sql = "DELETE NOTICE WHERE ID=?";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url,uid,pwd);
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
		
		int result = st.executeUpdate();
		
		st.close();
		con.close();
		return result;
	}


}
