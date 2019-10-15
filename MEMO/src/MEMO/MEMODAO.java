package MEMO;

import java.sql.*;
import java.util.ArrayList;

import MEMO.MEMODTO;

// 전달 데이터단위인 (DTO : Data Transfer Object)를 사용하면서 DB 데이터를 직접 처리하는
// DAO(Data Access Object)
public class MEMODAO {

	Connection conn = null;
	PreparedStatement pstmt = null;

	/* MySQL 연결정보 */
	String jdbc_driver = "com.mysql.jdbc.Driver";
	
	String jdbc_url = "jdbc:mysql://127.0.0.1/jspdb?useSSL=true&verifyServerCertificate=false&serverTimezone=UTC";

	
	/******************************************************************************************/
	// DB연결 메서드
	/******************************************************************************************/
	void connect() {
		try {
			Class.forName(jdbc_driver);

			conn = DriverManager.getConnection(jdbc_url,"jspbook","1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/******************************************************************************************/
	// DB 연결해제 메소드
	/******************************************************************************************/
	void disconnect() {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


}
