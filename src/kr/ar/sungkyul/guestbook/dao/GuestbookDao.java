package kr.ar.sungkyul.guestbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.ar.sungkyul.guestbook.vo.GuestbookVo;


public class GuestbookDao {

	public static final String ID = "webdb";
	public static final String PWD = "webdb";

	public static boolean insert(GuestbookVo vo) {

		List<GuestbookVo> list = new ArrayList<GuestbookVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. connection
			String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(dburl, ID, PWD);

			System.out.println("연결성공");

			// 3. statement 준비
			String sql = "insert into guestbook values ( seq_guestbook.nextval,?,?,?,sysdate)";

			pstmt = conn.prepareStatement(sql);

			// seq_author.nextva 객체를 먼저 생성해주어야 사용할 수 있다.
			// CREATE SEQUENCE seq_book
			// START WITH 1
			// INCREMENT BY 1
			// MAXVALUE 1000000;
			System.out.println("준비");

			// 4. 바인딩
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());
			System.out.println("바인딩");

			// 5. query 실행
			count = pstmt.executeUpdate();
			System.out.println("쿼리");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 에러 : " + e);
		} catch (SQLException ex) {
			System.out.println("에러 : " + ex);
		} finally {

			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (count == 1);
	}

	public static boolean delete(String no, String password) {

		List<GuestbookVo> list = new ArrayList<GuestbookVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. connection
			String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(dburl, ID, PWD);

			System.out.println("연결성공");

			// 3. statement 준비
			String sql = " delete from guestbook where no = ? and password = ?";
			

			pstmt = conn.prepareStatement(sql);
			System.out.println("준비");

			// 4. 바인딩
			pstmt.setString(1, no);
			pstmt.setString(2, password);
			System.out.println("바인딩");

			// 5. query 실행
			count = pstmt.executeUpdate();
			System.out.println("쿼리");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 에러 : " + e);
		} catch (SQLException ex) {
			System.out.println("에러 : " + ex);
		} finally {

			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (count == 1);
	}

	public static List<GuestbookVo> getList() {
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			stmt = conn.createStatement();

			String sql = "select no, name, password, content, to_char(reg_date, 'yyyy-mm-dd')  from guestbook order by reg_date desc";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String content = rs.getString(4);
				String reg_date = rs.getString(5);

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setContent(content);
				vo.setReg_date(reg_date);

				list.add(vo);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 에러 : " + e);
		} catch (SQLException ex) {
			System.out.println("에러 : " + ex);
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
}
