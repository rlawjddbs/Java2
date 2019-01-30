package day0107;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsePreparedStatementDAO {

	public UsePreparedStatementDAO() {

	}// UsePreparedStatementDAO

	private Connection getConn() throws SQLException {
		Connection con = null;
		// 1.
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch
			// 2.
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "scott";
		String pass = "tiger";

		con = DriverManager.getConnection(url, id, pass);
		return con;
	}// getConn

	/**
	 * 사원번호, 사원명, 연봉을 입력받아 CpEmp2 테이블에 삽입하는 일
	 * 
	 * @param cevo
	 * @throws SQLException
	 */
	public void insertCpEmp2(CpEmp2VO cevo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1. 드라이버로딩
			// 2. Connection 얻기
			con = getConn();
			// 3. 쿼리문 생성객체 얻기 : PreparedStatement 객체는 실행되는 쿼리문을 알고있다.
			String insertCpEmp = "insert into cp_emp2(empno, ename, hiredate, sal) values(?, ?, sysdate, ?)";
			pstmt = con.prepareStatement(insertCpEmp);
			// 4. bind 변수에 값 넣기
			pstmt.setInt(1, cevo.getEmpno());
			pstmt.setString(2, cevo.getEname());
			pstmt.setInt(3, cevo.getSal());
			// 5. 쿼리수행 후 결과얻기
			pstmt.executeUpdate();

		} finally {
			// 6. 연결끊기
			if (pstmt != null) {
				pstmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // end finally
	}// inesrtCpEmp2

	/**
	 * 사원번호, 사원명, 연봉을 입력받아 사원번호에 해당하는 사원명, 연봉을 변경하는 일.
	 * 
	 * @param cevo
	 * @return
	 * @throws SQLException
	 */
	public boolean updateCpEmp2(CpEmp2VO cevo) throws SQLException {
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1. 드라이버로딩
			// 2. Connection 얻기
			con = getConn();
			// 3. 쿼리문 생성객체 얻기
			String updateCpEmp2 = "update cp_emp2 set ename=?, sal=? where empno=?"; //
			pstmt = con.prepareStatement(updateCpEmp2);
			// 4. 바인드변수에 값 넣기
			pstmt.setString(1, cevo.getEname());
			pstmt.setInt(2, cevo.getSal());
			pstmt.setInt(3, cevo.getEmpno());

			// 5. 쿼리문 수행 후 결과 얻기
			int cnt = pstmt.executeUpdate();
			if( cnt != 0 ) {
				flag = true;
			}//end if
			
		} finally {
			// 6. 연결 끊기
			if ( pstmt != null ) { pstmt.close(); }//end if
			if ( con != null ) { con.close(); }//end if
		} // end finally
		return flag;
	}// updateCpEmp2


	/**
	 * 사원번호를 입력받아 사원번호에 해당하는 사원을 삭제하는 일.
	 * @param empno
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean deleteCpEmp2(int empno) throws SQLException {
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			//1. 드라이버 로딩
			//2. Connection 얻기
			con = getConn();
			//3. 쿼리문 생성객체 얻기
			String deleteCpEmp2 = "delete from cp_emp2 where empno = ?"; // 바인드 변수에는 ' 을 붙이면 에러 발생
			pstmt = con.prepareStatement(deleteCpEmp2);
			//4. 바인드 변수에 값 넣기
			pstmt.setInt(1, empno);
			//5. 쿼리문 수행 후 결과 얻기
			int cnt = pstmt.executeUpdate();
			
			if(cnt!=0) {
				flag = true;
			}//end if
			
		} finally {
			//6. 연결 끊기
			if ( pstmt != null ) { pstmt.close(); }//end if
			if ( con != null ) { con.close(); }//end if
		}//end finally
		return flag;
	}// deleteCpEmp2
	

	/**
	 * 모든 부서 사원정보를 조회
	 * @return 
	 * @throws SQLException
	 */
	public List<CpEmp2AllVO> selectAllCpEmp2() throws SQLException {
		List<CpEmp2AllVO> list = new ArrayList<CpEmp2AllVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//1. 드라이버 로딩
			//2. Connection 얻기
			con = getConn();
			//3. 쿼리문 생성객체 얻기
			String selectAllCpEmp2 = "select empno, ename, sal, to_char(hiredate, 'yyyy-mm-dd') as hiredate from cp_emp2";
			pstmt = con.prepareStatement(selectAllCpEmp2);
			//4. 바인드 변수에 값 넣기 // 바인드 변수(물음표) 없음
			
			//5. 쿼리문 수행 후 결과 얻기
			rs = pstmt.executeQuery();
			
			// Network의 투명성은 DB서버의 위치가 어디에 있는지 모르게 만드는 것이다. (Location Transparency)
			
			// 조회 결과를 VO에 할당
			CpEmp2AllVO cda_vo = null;
			while( rs.next() ) {
				cda_vo = new CpEmp2AllVO(rs.getInt("empno"), rs.getInt("sal"), rs.getString("ename"), rs.getString("hiredate"));
				list.add(cda_vo);
			}//end while
			
		} finally {
			//6. 연결끊기
			if ( rs != null ) { rs.close(); }//end if
			if ( pstmt != null ) { pstmt.close(); }//end if
			if ( con != null ) { con.close(); }//end if
		}//end finally
		return list;
	}// selectAllCpEmp2
	

	public CpEmp2OneVO selectOneCpEmp2(int empno) throws SQLException {
		CpEmp2OneVO cevo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//1. 드라이버 로딩
			//2. Connection 얻기
			con = getConn();
			//3. 쿼리문 생성 객체 얻기
			String selectOneCpEmp = "select empno, ename, sal, hiredate from cp_emp2 where empno=?";
			pstmt = con.prepareStatement(selectOneCpEmp);
			//4. 바인드 변수에 값 설정
			pstmt.setInt(1, empno);
			//5. 쿼리문 수행 후 결과 얻기
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				cevo = new CpEmp2OneVO(rs.getInt("sal"), rs.getString("ename"), rs.getDate("hiredate"));
			}//end if
			
		} finally {
			//6. 연결끊기
			if ( rs != null ) { rs.close(); }//end if
			if ( pstmt != null ) { pstmt.close(); }//end if
			if ( con != null ) { con.close(); }//end if
		}//end finally
		return cevo;
	}// selectOneCpEmp2
	
//	public static void main(String[] args) {
//		// 단위 테스트를 위해 임시로 만든 main 메서드
//		UsePreparedStatementDAO u = new UsePreparedStatementDAO();
//		try {
//			CpEmp2OneVO c = u.selectOneCpEmp2(7521);
//			System.out.println( c );
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}//end catch
//		
//	}//main

}// class
