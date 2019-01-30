package day0104;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Statement 객체를 사용하여 CRUD를 구현하는 클래스 CRUD는 Create Read Update Delete의 약자.
 * 
 * @author owner
 */
public class UseStatementCRUD {

	/**
	 * VO를 입력받아 VO의 값을 CP_DEPT 테이블에 추가
	 * 
	 * @param cdvo 부서번호, 부서명, 위치를 가진 VO
	 * @throws SQLException 쿼리문이
	 */
	public void insertCpDept(CpDeptVO cdvo) throws SQLException {
		// 1. 드라이버로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");

		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} // end catch

		Connection con = null;
		Statement stmt = null;

		try {
			// 2. Connection 얻기
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			String id = "scott";
			String pwd = "tiger";

			con = DriverManager.getConnection(url, id, pwd);
			// 3. 쿼리문 생성객체 얻기
			stmt = con.createStatement();
			// 4. 쿼리수행 후 결과 얻기
//			String insertCpDept="insert into cp_dept(deptno, dname, loc) values("+
//						cdvo.getDeptno()+",'"+cdvo.getDname()+"','"+cdvo.getLoc()+"')";
			StringBuilder insertCpDept = new StringBuilder();
			insertCpDept.append("insert into cp_dept(deptno, dname, loc) values(").append(cdvo.getDeptno()).append(",'")
					.append(cdvo.getDname()).append("','").append(cdvo.getLoc()).append("')");

			// executeUpdate는 String을 받는데 insertCpDept는 StringBuilder 타입이므로
			// toString()을 이용해 String 타입으로 형변환 해줘야 한다. (안하면 에러 발생)
			int cnt = stmt.executeUpdate(insertCpDept.toString());
//			System.out.println(cnt); //있으면 1 없으면 0?

		} finally {
			// 5. 연결 끊기
			if (stmt != null) {
				stmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // end finally

	}// insertCpDept

	public boolean updateCpDept(CpDeptVO cdvo) throws SQLException {
		boolean flag = false;

		// 1. 드라이버로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} // end catch

		Connection con = null;
		Statement stmt = null;
		try {
			// 2. Connection 얻기
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			String id = "scott";
			String pwd = "tiger";

			con = DriverManager.getConnection(url, id, pwd);
			// 3. 쿼리문 생성객체 얻기
			stmt = con.createStatement();
			// 4. 쿼리 수행후 결과 얻기
			StringBuilder updateCpDept = new StringBuilder();
			updateCpDept.append("update cp_dept set ").append("dname='").append(cdvo.getDname()).append("', ")
					.append("loc='").append(cdvo.getLoc()).append("' ").append("where deptno=")
					.append(cdvo.getDeptno());

			int cnt = stmt.executeUpdate(updateCpDept.toString());
			// 테이블의 구조상 부서번호는 PK이므로 한행만 변경 된다.
			if (cnt != 0) {
				flag = true;
			} // end if

		} finally {
			// 5. 연결끊기
			if (stmt != null) {
				stmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // end finally

		return flag;
	}// updateCpDept

	public boolean deleteCpDept(int deptno) throws SQLException {
		boolean flag = false;
		// 1. 드라이버로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch

		Connection con = null;
		Statement stmt = null;
		try {
			// 2. Connection 얻기
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			String id = "scott";
			String pwd = "tiger";
			con = DriverManager.getConnection(url, id, pwd);
			// 3. 쿼리문 생성객체 얻기
			stmt = con.createStatement();
			// 4. 쿼리 수행 후 결과 얻기
			StringBuilder deleteCpDept = new StringBuilder();
			deleteCpDept.append("delete from cp_dept where deptno=").append(deptno);
			int cnt = stmt.executeUpdate(deleteCpDept.toString());
			if (cnt == 1) { // cnt != 0 으로 해도 동일한 결과를 얻는다.
				flag = true;
			} // end if

		} finally {
			// 5. 연결끊기
			if (stmt != null) {
				stmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // end finally
		return flag;
	}// deleteCpDept

	public List<CpDeptVO> selectAllCpDept() throws SQLException {
		List<CpDeptVO> list = new ArrayList<CpDeptVO>();

		// 1. 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// 2. Connection 얻기
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			String id = "scott";
			String pass = "tiger";

			con = DriverManager.getConnection(url, id, pass);
			// 3. 쿼리문 생성객체 얻기
			stmt = con.createStatement();
			// 4. 쿼리문 수행후 결과 얻기
			String selectCpDept = "select deptno, dname, loc from cp_dept";
			rs = stmt.executeQuery(selectCpDept);

			CpDeptVO cdvo = null;

			while (rs.next()) {// 조회된 레코드가 존재한다면
				// 컬럼의 인덱스로 조회
//			System.out.println(rs.getInt(0)+" "+rs.getString(1)+" "+rs.getString(2)); //0번은 cursor 자리
//			System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));

				// 컬럼명으로 조회
//			System.out.println(rs.getInt("deptno")+" "+rs.getString("dname")+" "+rs.getString("loc"));

				// 조회 결과를 VO에 저장
				cdvo = new CpDeptVO(rs.getInt("deptno"), rs.getString("dname"), rs.getString("loc"));
				// 같은 이름으로 생성된 cdvo객체를 사라지지 않도록 관리하기위해 List에 추가
				list.add(cdvo);

			} // end while

		} finally {
			// 5. 연결 끊기
			if (rs != null) {
				rs.close();
			} // end if
			if (stmt != null) {
				stmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // end finally

		return list;
	}// selectAllCpDept

	public OneCpDeptVO selectCpDept(int deptno) throws SQLException {
		OneCpDeptVO ocdvo = null;

		// 1. 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String id = "scott";
		String pwd = "tiger";

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			// 2. Connection 열기
			con = DriverManager.getConnection(url, id, pwd);

			// 3. 쿼리문 생성 객체 얻기
			stmt = con.createStatement();

			// 4. 쿼리문 수행후 결과 얻기
			StringBuilder selectCpDept = new StringBuilder();
			selectCpDept.append("select dname, loc from cp_dept where deptno=").append(deptno);

			rs = stmt.executeQuery(selectCpDept.toString());

//			rs.getStatement().executeQuery(selectCpDept.toString()); //rs는 cursor의 제어권을 얻는다. //이게 있으면 조회가 안됨. 왜지?

			if (rs.next()) { // 조회된 레코드가 존재 한다면
				ocdvo = new OneCpDeptVO(rs.getString("dname"), rs.getString("loc"));
			} // end if
		} finally {
			// 5. 연결끊기
			if (rs != null) {
				rs.close();
			} // end if
			if (stmt != null) {
				stmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		}
		return ocdvo;
	}// selectCpDept

	/**
	 * CP_DEPt 테이블의 모든 부서번호를 조회
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Integer> selectAllCpDeptNo() throws SQLException {
		List<Integer> list = new ArrayList<Integer>();
		// 1. 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String id = "scott";
		String pwd = "tiger";
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
		// 2. Connection 얻기
		con = DriverManager.getConnection(url, id, pwd);
		
		// 3. 쿼리문 생성 객체 얻기
		stmt = con.createStatement();
		
		// 4. 쿼리문 수행 후 결과 얻기
		String selectDeptno = "select deptno from cp_dept";

		rs = stmt.executeQuery(selectDeptno);
		
		while(rs.next()) {
			list.add(new Integer(rs.getInt("deptno")));
		}//end while
		
		} finally {
		// 5. 연결끊기
			if( rs != null ) { rs.close(); }//end if
			if( stmt != null ) { stmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return list;
	}// selectAllCpDeptNo

}// UseStatementCRUD
