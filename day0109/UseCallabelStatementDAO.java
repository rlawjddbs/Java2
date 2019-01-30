package day0109;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import day0110.TestProcAllVO;
import day0110.TestProcOneVO;
import day0110.TestProcUpdateVO;
import kr.co.sist.connection.GetConnection;
import oracle.jdbc.internal.OracleTypes;

/**
 * DAO - Data Access Object Procedure를 사용한 CURD
 * 
 * @author owner
 */
public class UseCallabelStatementDAO {

	private static UseCallabelStatementDAO ucs_dao;

	private UseCallabelStatementDAO() {
	}

	public static UseCallabelStatementDAO getInstance() {
		if (ucs_dao == null) {
			ucs_dao = new UseCallabelStatementDAO();
		} // end if
		return ucs_dao;
	}// getInstance

	/**
	 * test_proc 테이블에 사원번호, 사원명, 연봉, 직무를 추가하는 일
	 * 
	 * @param tpvo
	 * @return
	 * @throws SQLException
	 */
	public String insertProc(TestProcVO tpvo) throws SQLException {
		String resultMsg = "";

		Connection con = null;
		CallableStatement cstmt = null;

		try {
			// 1. 드라이버 로딩
			// 2. Connection 얻기
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "scott";
			String pass = "tiger";
			con = GetConnection.getInstance().getConn(url, id, pass);
			// 3. 쿼리문 생성 객체(프로시저 실행 객체) 얻기
			cstmt = con.prepareCall(" { call insert_test_proc(?, ?, ?, ?, ?, ?) } ");
			// 4. 바인드 변수에 값 설정
			// in parameter
			cstmt.setInt(1, tpvo.getEmpno());
			cstmt.setString(2, tpvo.getEname());
			cstmt.setInt(3, tpvo.getSal());
			cstmt.setString(4, tpvo.getJob());
			// out parameter : 프로시저가 처리한 결과를 저장할 데이터형을 설정
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.registerOutParameter(6, Types.NUMERIC);
			// 5. 쿼리 실행 후 결과 얻기
			cstmt.execute();
			// 프로시저가 실행된 후out parameter에 설정된 값 얻기
			resultMsg = cstmt.getString(5);
			int cnt = cstmt.getInt(6); // 추가 성공시 1, 실패시 0
			System.out.println(cnt);

		} finally {
			// 6. 연결끊기
			if (cstmt != null) {
				cstmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		}
		return resultMsg;

	}// insertProc

	public String updateProc(TestProcUpdateVO tpuvo) throws SQLException {
		String msg = "";
		Connection con = null;
		CallableStatement cstmt = null;

		try {
			// 1.
			// 2.
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "scott";
			String pass = "tiger";
			con = GetConnection.getInstance().getConn(url, id, pass);
			// 3.
			cstmt = con.prepareCall("{ call update_test_proc(?, ?, ?, ?, ?) }");
			// 4. bind 변수에 값넣기 => procedure의 매개변수
			// in parameter
			cstmt.setInt(1, tpuvo.getEmpno());
			cstmt.setString(2, tpuvo.getJob());
			cstmt.setInt(3, tpuvo.getSal());
			// out parameter : procedure가 처리한 결과를 저장한 매개변수
			cstmt.registerOutParameter(4, Types.NUMERIC);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			// 5.
			cstmt.execute();
			// 부모의 method 사용 procedure를 실행. (코드의 재사용성 증가, 상속의 장점) => 실행결과가 out parameter에 저장
			int cnt = cstmt.getInt(4);
			msg = cstmt.getString(5);

			System.out.println("update : " + cnt);

		} finally {
			if (cstmt != null) {
				cstmt.close();
			}
			if (con != null) {
				con.close();
			}
		} // end finally

		return msg;
	}// updateProc

	public String deleteProc(int empno) throws SQLException {
		String msg = "";

		Connection con = null;
		CallableStatement cstmt = null;

		try {

			// 1. 드라이버 로딩
			// 2. Connection 얻기
			String url = "jdbc:oracle:thin:@localhost:1521:orcl"; // orcl : 오라클 database의 SID (SID 명은 바꿀 수 있음)
			String id = "scott";
			String pass = "tiger";
			con = GetConnection.getInstance().getConn(url, id, pass);

			// 3. 쿼리문 수행 객체 얻기
			cstmt = con.prepareCall("{ call delete_test_proc(?, ?, ?) }");

			// 4. 바인드 변수에 값 설정
			// in parameter
			cstmt.setInt(1, empno);
			// out parameter
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.NUMERIC);

			// 5. 쿼리문 수행 결과 얻기
			cstmt.execute();
			// out parameter에 설정된 값 받기
			msg = cstmt.getString(2);
			int cnt = cstmt.getInt(3);

			System.out.println(cnt + "건 삭제");

		} finally {
			// 6. 연결 끊기
			if (cstmt != null) {
				cstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return msg;
	}// deleteProc

	public List<TestProcAllVO> selectAllTestProc() throws SQLException {
		List<TestProcAllVO> list = new ArrayList<TestProcAllVO>();

		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
		// 1. 드라이버 로딩
		// 2. Connection 얻기
			String url ="jdbc:oracle:thin:@localhost:1521:orcl";
			String id ="scott";
			String pass="tiger";
			con = GetConnection.getInstance().getConn(url, id, pass);
		// 3. 쿼리문 생성객체 얻기
			
			cstmt = con.prepareCall("{ call select_all_test_proc(?, ?) }"); // PLEdit 으로 compile(생성)한 프로시저를 불러오는 것
		// 4. 바인드 변수에 값 넣기
			//out parameter : sys_refcursor -> OracleTypes.CURSOR
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
//			cstmt.registerOutParameter(1, Types.REF_CURSOR);
			
			//in parameter
			cstmt.setString(2, "mm-dd-yyyy day hh24:mi");
		// 5. 쿼리실행 후 결과 얻기
			cstmt.execute();
			// 커서의 제어권 받기
			rs = (ResultSet)cstmt.getObject(1); // OracleTypes.CURSOR 는 ResultSet의 자식이므로 형 변환 가능하다.
			
			// 제어권을 받아 조회한 모든 컬럼값을 
			// VO(VO 인스턴스 하나당 테이블의 한 레코드(1줄)가 된다.)에 할당하고 
			// List에 추가 한다.
			TestProcAllVO tpavo = null;
			
			while(rs.next()) {
				tpavo = new TestProcAllVO
						(rs.getInt("empno"), rs.getInt("sal"), 
						rs.getString("ename"), rs.getString("hiredate"), rs.getString("job"));
				
				list.add(tpavo);
			}//end while
			
		} finally {
		// 6. 연결끊기
			if( rs != null ) { rs.close(); }//end if
			if( cstmt != null ) { cstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		} // end finally
		return list;
	}// selectAllTestProc

	public TestProcOneVO selectOneTestProc(int empno) throws SQLException{
		TestProcOneVO tpovo = null;
		
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
		//1. 드라이버로딩
		//2. Connection 얻기
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "scott";
		String pass = "tiger";
		con = GetConnection.getInstance().getConn(url, id, pass);
		
	//3 쿼리문 생성객체 얻기
		cstmt = con.prepareCall("{ call select_one_test_proc(?, ?, ?) }");
		
	//4. 바인드 변수에 값 설정
		//in parameter
		cstmt.setInt(1, empno);
		//out parameter
		cstmt.registerOutParameter(2, OracleTypes.CURSOR);
		cstmt.registerOutParameter(3, OracleTypes.VARCHAR);
		
	//5. 쿼리 실행 후 결과 얻기
		cstmt.execute();
		
		rs = (ResultSet)cstmt.getObject(2);
		
		if( rs.next() ) {
			tpovo = new TestProcOneVO
					(rs.getInt("sal"), rs.getString("ename"), rs.getString("job"), rs.getString("hiredate"));
		}//end if
		
		} finally{
			//6. 연결 끊기
			if( rs != null ) { rs.close(); }//end if
			if( cstmt != null ) { cstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		return tpovo;
	}//selectOneTestProc
	
	public static void main(String[] args) {
		UseCallabelStatementDAO u = new UseCallabelStatementDAO();
//		TestProcUpdateVO t = new TestProcUpdateVO(1111, 3000, "과장");
//		TestProcVO t = new TestProcVO(2222, 4200, "공선의", "과장");
		try {
			System.out.println( u.selectOneTestProc(1234) );
//			List<TestProcAllVO> l = u.selectAllTestProc();
//			System.out.println(l);
//			u.insertProc(t);
//			System.out.println(u.deleteProc(1111));
		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
	}// main

}// class
