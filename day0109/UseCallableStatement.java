package day0109;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

import kr.co.sist.connection.GetConnection;

/**
 *	Procedure 호출
 * @author owner
 */
public class UseCallableStatement {

	public UseCallableStatement() throws SQLException{
		Connection con = null;
		CallableStatement cstmt = null; //조회를 해서 가져오는것이 아니므로 ResultSet은 필요 없다.
		
		String tempData = JOptionPane.showInputDialog("숫자 2개 입력\n입력 예)숫자,숫자");
		int num1 = Integer.parseInt(tempData.split(",")[0]);
		int num2 = Integer.parseInt(tempData.split(",")[1]);
		
		
		try {
		//1. 드라이버 로딩
		//2. Connection 얻기
			String url="jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "scott";
			String pass = "tiger";
			con = GetConnection.getInstance().getConn(url, id, pass);
		//3. 쿼리문 생성객체 얻기
			cstmt = con.prepareCall("{ call proc_plus(?, ?, ?) }"); //(in, in, out)
		//4. 바인드 변수에 값 설정
			// in parameter (2 개)
			cstmt.setInt(1, num1);
			cstmt.setInt(2, num2);
			// out parameter (1 개)
			cstmt.registerOutParameter(3, Types.NUMERIC);
			
		//5. 쿼리(procedure)실행 후 결과 얻기
			// 프로시저를 실행하면 in paramete와 out parameter에 값이 할당 됨.
			cstmt.execute();
			
			int total=cstmt.getInt(3);
			
			JOptionPane.showMessageDialog(null, num1+" + "+num2+" = "+total);
		} finally {
			//6. 연결끊기
			if ( cstmt != null ) { cstmt.close(); }//end if
			if ( con != null ) { con.close(); }//end if
		}//end finally
	}//UseCallableStatement
	
	public static void main(String[] args) {
		try {
			new UseCallableStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
	}//main

}//class
