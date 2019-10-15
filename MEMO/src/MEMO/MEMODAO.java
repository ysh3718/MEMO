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

	/******************************************************************************************/
	// 메모장 입력 메서드
	/******************************************************************************************/
	public boolean insertDB(MEMODTO MEMODTO) {
		
		
		connect();
		
		// id 는 자동 등록 되므로 입력하지 않는다.				
		String sql ="insert into MEMO(title,content, memoDate) values(?,?,?)";
		
		try {
			
			pstmt = conn.prepareStatement(sql);

			// SQL문에 변수 입력
			pstmt.setString(1,MEMODTO.getTitle());
			pstmt.setString(2,MEMODTO.getContent());
			pstmt.setString(3,MEMODTO.getMemoDate());
			
			//SQL문 실행
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	
	/******************************************************************************************/
	// 메모장 조회 메서드
	/******************************************************************************************/
	public ArrayList<MEMODTO> getDBList() {
		
		connect();
		
		ArrayList<MEMODTO> MEMOList = new ArrayList<MEMODTO>();
		
		String sql = "select id, title, content, memoDate from MEMO";

		try {
			
			pstmt = conn.prepareStatement(sql);
			
			//SQL문 실행
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				
				// DO 객체 생성
				MEMODTO MEMODTO = new MEMODTO();
				
				// DB Select결과를 DO 객체에 저장
				MEMODTO.setId(rs.getInt("id"));
				MEMODTO.setTitle(rs.getString("title"));
				MEMODTO.setContent(rs.getString("content"));
				MEMODTO.setMemoDate(rs.getString("memoDate"));
				
				MEMOList.add(MEMODTO);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return MEMOList;
	}

	/******************************************************************************************/
	// edit용 메모장 1건 조회 메서드
	/******************************************************************************************/
	public MEMODTO getDB(int id) {
		
		connect();
		
		MEMODTO MEMODTO = new MEMODTO();
		
		String sql = "select * from MEMO where id = ?";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			// SQL문에 조회조건 입력
			pstmt.setInt(1,id);

			//SQL문 실행
			ResultSet rs = pstmt.executeQuery();

			// 데이터가 하나만 있으므로 rs.next()를 한번만 실행 한다.
			rs.next();
			
			// DB Select결과를 DO 객체에 저장
			MEMODTO.setId(rs.getInt("id"));
			MEMODTO.setTitle(rs.getString("title"));
			MEMODTO.setContent(rs.getString("content"));
			MEMODTO.setMemoDate(rs.getString("memoDate"));
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return MEMODTO;
	}
	
}
