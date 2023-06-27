package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;

public class NoticeService {
	static String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
	static String uid = "newlec";
	static String pwd = "0000";
	static String driver = "oracle.jdbc.driver.OracleDriver";
	
	public int removeNotice(int[] ids){
		return 0;
	}
	
	public int pubNoticeAll(int[] oids, int[] cids) {
		
		List<String> oidsList = new ArrayList<>();
		for(int i=0;i<oids.length;i++)
			oidsList.add(String.valueOf(oids[i]));
		
		List<String> cidsList = new ArrayList<>();
		for(int i=0;i<oids.length;i++)
			cidsList.add(String.valueOf(cids[i]));
		
		return pubNoticeAll(oidsList,cidsList);
	}
	
	public int pubNoticeAll(List<String> oids, List<String> cids) {
		
		//oids의 내용들을 중간에 콤마를 끼워서 하나의 문자열로 생성함
		String oidsCSV = String.join(",", oids);
		String cidSCSV = String.join(",", cids);;
		
		return pubNoticeAll(oidsCSV,cidSCSV);
	}
	
	//"20,30,43,56" 형태의 문자열이 올 때
	public int pubNoticeAll(String oidsCSV, String cidsCSV) {
		
		int result=0;
		
		String sqlOpen = String.format("UPDATE NOTICE SET PUB=1 WHERE ID IN (%s)",oidsCSV);
		String sqlClose = String.format("UPDATE NOTICE SET PUB=0 WHERE ID IN (%s)",cidsCSV);
		
		try {
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url, uid, pwd);
			Statement stOpen=con.createStatement();
			//update한 것은 업데이트한 튜플의 수를 반환한다
			result += stOpen.executeUpdate(sqlOpen);
			
			Statement stClose=con.createStatement();
			result += stClose.executeUpdate(sqlClose);

			stOpen.close();
			stClose.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int insertNotice(Notice notice){
		int result=0;
		
		String sql="INSERT INTO NOTICE(TITLE, CONTENT, WRITER_ID, PUB, REGDATE, FILES) VALUES(?,?,?,?,SYSDATE,?)";
		
		try {
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url, uid, pwd);
			PreparedStatement st=con.prepareStatement(sql);
			st.setString(1, notice.getTitle());
			st.setString(2, notice.getContent());
			st.setString(3, notice.getWriterId());
			st.setBoolean(4, notice.getPub());
			st.setString(5, notice.getFiles());
			//update한 것은 업데이트한 튜플의 수를 반환한다
			result = st.executeUpdate();

			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int deleteNotice(int id){
		return 0;
	}
	
	public int updateNotice(Notice notice){
		return 0;
	}
	
	public List<Notice> getNoticeNewstList(){
		return null;
	}
	
	public List<NoticeView> getNoticeList(){
		
		return getNoticeList("title","",1);
	}
	
	public List<NoticeView> getNoticeList(int page){
		
		return getNoticeList("title","",page);
	}
	
	public List<NoticeView> getNoticeList(String field, String query, int page){
		List<NoticeView> list = new ArrayList<>();
		
		String sql="SELECT * FROM( " + 
				"    SELECT ROWNUM NUM, N.* " + 
				"    FROM (SELECT * FROM NOTICE_VIEW WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N) " + 
				"WHERE NUM BETWEEN ? AND ?";

		try {
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url, uid, pwd);
			PreparedStatement st=con.prepareStatement(sql);
			st.setString(1, '%'+query+'%');
			st.setInt(2, 1+(page-1)*10);
			st.setInt(3, page*10);
			ResultSet rs=st.executeQuery();

			while(rs.next()){
				int id=rs.getInt("ID");
				String title=rs.getString("TITLE");
				Date regdate=rs.getDate("REGDATE");
				String writerId=rs.getString("WRITER_ID");			
				int hit=rs.getInt("HIT");
				String files=rs.getString("FILES");
				//String content=rs.getString("CONTENT");
				int cmtCount=rs.getInt("CMT_COUNT");
				boolean pub = rs.getBoolean("PUB");
				
				NoticeView notice = new NoticeView(id,
						title,
						regdate,
						writerId,
						hit,
						files,
						pub,
						//content,
						cmtCount);
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
		
		return list;
	}
	
	public List<NoticeView> getNoticePubList(String field, String query, int page) {
		List<NoticeView> list = new ArrayList<>();
		
		String sql="SELECT * FROM( " + 
				"    SELECT ROWNUM NUM, N.* " + 
				"    FROM (SELECT * FROM NOTICE_VIEW WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N) " + 
				"WHERE PUB=1 AND NUM BETWEEN ? AND ?";

		try {
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url, uid, pwd);
			PreparedStatement st=con.prepareStatement(sql);
			st.setString(1, '%'+query+'%');
			st.setInt(2, 1+(page-1)*10);
			st.setInt(3, page*10);
			ResultSet rs=st.executeQuery();

			while(rs.next()){
				int id=rs.getInt("ID");
				String title=rs.getString("TITLE");
				Date regdate=rs.getDate("REGDATE");
				String writerId=rs.getString("WRITER_ID");			
				int hit=rs.getInt("HIT");
				String files=rs.getString("FILES");
				//String content=rs.getString("CONTENT");
				int cmtCount=rs.getInt("CMT_COUNT");
				boolean pub = rs.getBoolean("PUB");
				
				NoticeView notice = new NoticeView(id,
						title,
						regdate,
						writerId,
						hit,
						files,
						pub,
						//content,
						cmtCount);
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
		
		return list;
	}
	
	public int getNoticeCount() {
		return getNoticeCount("title","");
	}
	
	public int getNoticeCount(String field, String query) {
		
		int count=0;
		
		String sql="SELECT COUNT(ID) COUNT FROM ( " + 
				"    SELECT ROWNUM NUM, N.* FROM (" + 
				"    SELECT * FROM NOTICE WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N) ";
		
		try {
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url, uid, pwd);
			PreparedStatement st=con.prepareStatement(sql);
			st.setString(1, '%'+query+'%');
			
			ResultSet rs=st.executeQuery();
			
			if(rs.next())
				count = rs.getInt("COUNT");

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
		
		return count;
	}
	
	public Notice getNotice(int id) {
		
		Notice notice = null;
		
		String sql = "SELECT * FROM NOTICE WHERE ID=?";
		
		try {
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url, uid, pwd);
			PreparedStatement st=con.prepareStatement(sql);
			st.setInt(1, id);
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()){
				int nid=rs.getInt("ID");
				String title=rs.getString("TITLE");
				Date regdate=rs.getDate("REGDATE");
				String writerId=rs.getString("WRITER_ID");			
				int hit=rs.getInt("HIT");
				String files=rs.getString("FILES");
				String content=rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");
				
				notice = new Notice(nid,title,regdate,writerId,hit,files,content,pub);
				
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
		
		return notice;
	}
	
	public Notice getNextNotice(int id) {
		Notice notice = null;
		String sql="SELECT * FROM NOTICE " + 
				"	 WHERE ID = ( " + 
				"    SELECT ID FROM NOTICE " + 
				"    WHERE REGDATE > (SELECT REGDATE FROM NOTICE WHERE ID=?) " + 
				"    AND ROWNUM=1 " + 
				")";
		
		try {
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url, uid, pwd);
			PreparedStatement st=con.prepareStatement(sql);
			st.setInt(1, id);
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()){
				int nid=rs.getInt("ID");
				String title=rs.getString("TITLE");
				Date regdate=rs.getDate("REGDATE");
				String writerId=rs.getString("WRITER_ID");			
				int hit=rs.getInt("HIT");
				String files=rs.getString("FILES");
				String content=rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");
				
				notice = new Notice(nid,title,regdate,writerId,hit,files,content,pub);
				
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
		
		return notice;
	}
	
	public Notice getPrevNotice(int id) {
		Notice notice = null;
		String sql="SELECT ID FROM (SELECT * FROM NOTICE ORDER BY REGDATE DESC) " + 
				"    WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID=?) " + 
				"    AND ROWNUM=1 ";
		
		try {
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url, uid, pwd);
			PreparedStatement st=con.prepareStatement(sql);
			st.setInt(1, id);
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()){
				int nid=rs.getInt("ID");
				String title=rs.getString("TITLE");
				Date regdate=rs.getDate("REGDATE");
				String writerId=rs.getString("WRITER_ID");			
				int hit=rs.getInt("HIT");
				String files=rs.getString("FILES");
				String content=rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");
				
				notice = new Notice(nid,title,regdate,writerId,hit,files,content,pub);
				
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
		
		return notice;
	}

	public int deleteNoticeAll(int[] ids) {

		int result=0;
		
		String params="";
		for(int i=0;i<ids.length;i++)
		{
			params+=ids[i];
			if(i<ids.length-1)
				params+=",";
		}
		String sql="DELETE NOTICE WHERE ID IN ("+params+")";
		
		try {
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url, uid, pwd);
			Statement st=con.createStatement();
			
			//update한 것은 업데이트한 튜플의 수를 반환한다
			result = st.executeUpdate(sql);

			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
