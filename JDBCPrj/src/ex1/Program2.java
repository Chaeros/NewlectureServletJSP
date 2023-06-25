package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class Program2 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		String title="TEST2";
		String writerId="newlec";
		String content="hahaha";
		String files="";
		
		String url = "jdbc:oracle:thin:@118.42.165.226:1521/xepdb1";
		String sql = "INSERT INTO notice(" + 
				"    title," + 
				"    writer_id," + 
				"    content," + 
				"    files" + 
				")VALUES(?,?,?,?)";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url,"newlec","0000");
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
	}

}
