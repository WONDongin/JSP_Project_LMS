package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection { //DBConnection 클래스는 객체 불가 => 생성자 접근 불가
	private DBConnection() {}  //생성자. 
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection
					("jdbc:mariadb://kdt.goodee.co.kr:10004/LMS","user","1234");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return conn;
	}
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}