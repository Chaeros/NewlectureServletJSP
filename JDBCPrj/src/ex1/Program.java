package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


//메모리상에 jdbc드라이버를 올림 
//Class.forName("oracle.jdbc.driver.OracleDriver");
//오라클 서버와 연결을 함
//Connection con=DriverManager.getConnection(...);
//사용자로부터 요구받은 쿼리를 받음
//Statement st=concreateStatement();
//받은 쿼리를 실행하여 나온 결과 집합의 주소를 받아옴
//ResultSet rs=st.executeQuery(sql);
//5.rs에 있는 레코드 하나를 가리키며 받아옴(파일의 끝 = EoF)
//rs.next();
//6.받아온 레코드에서 특정 속성값을 요청
//String title=rs.getString("title");
public class Program {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String url = "jdbc:oracle:thin:@118.42.165.226:1521/xepdb1";
		String sql = "SELECT * FROM NOTICE WHERE HIT>10";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url,"newlec","0000");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {
			int id=rs.getInt("ID");
			String title=rs.getString("TITLE");
			String writerId=rs.getString("WRITER_ID");			
			Date regDate=rs.getDate("REGDATE");
			String content=rs.getString("CONTENT");
			int hit=rs.getInt("HIT");
			
			System.out.printf(" id:%d, title:%s, writerId:%s, regDate:%s, content:%s, hit:%d\n", 
					id, title, writerId, regDate, content, hit);
		}
		
		rs.close();
		st.close();
		con.close();
	}

}
