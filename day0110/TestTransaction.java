package day0110;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.connection.GetConnection;

/**
 *	Transaction처리
 * @author owner
 */
public class TestTransaction {
	// commit과 rollback을 DB작업 외부에서 처리할 수 있도록 class field 정의.
	private Connection con = null;

	public boolean insert(TransactionVO tv) throws SQLException{
		// transaction에 사용할 쿼리의 수만큼 쿼리 실행 객체 선언
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		
		boolean flag = false;
		
		try {
		//1. 드라이버 로딩
		//2. Connection 얻기
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "scott";
			String pass = "tiger";
			con=GetConnection.getInstance().getConn(url, id, pass);
			//auto commit 해제
			con.setAutoCommit(false);
		//3. 쿼리문 생성객체 얻기
			String sql = "insert into test_transaction1(subject, writer) values(?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tv.getSubject());
			pstmt.setString(2, tv.getWriter());
		//4.  
			int cnt = pstmt.executeUpdate();
			//3. 쿼리문 생성객체 얻기
			String sql1 = "insert into test_transaction2(subject, writer) values(?, ?)";
			pstmt1 = con.prepareStatement(sql1);
			pstmt1.setString(1, tv.getSubject());
			pstmt1.setString(2, tv.getWriter());

			//4.  
			int cnt1 = pstmt1.executeUpdate();
			
			// 트랜잭션에 해당하는 모든 쿼리의 목표 수행 수를 비교하ㅑ여
			// commit과 rollback 여부를 설정한다.
			if( cnt == 1 && cnt1 == 1) {
				flag=true;
			}//end if
		} finally {
		//6. 연결끊기
		}//end finally
		
		return flag;
	}//insert
	
	public void add() {
		String inputData = JOptionPane.showInputDialog("제목과 작성자를 입력해주세요.\n제목-작성자");
		
		String[] data = inputData.split("-");
		
		if(data.length != 2) {
			JOptionPane.showMessageDialog(null, "입력 형식을 확인해 주세요.");
			return;
		}//end if
		TransactionVO tv = new TransactionVO(data[0], data[1]);
		
		try {
			//DB업무를 사용하는 곳에서 수행 결과를 받아
			boolean flag = insert(tv);
			if(flag) {
				//커밋하거나
				con.commit();
				System.out.println("커밋!!!");
			}else { 																// update나 delete가 transaction일 때
				//롤백한다.
				con.rollback();
				System.out.println("update나 delete 롤백!!!");
			}//end else
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.println("insert 롤백!!!");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}//end catch
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
		}//end finally
		
	}//add
	
	public static void main(String[] args) {
		TestTransaction tt = new TestTransaction();
		tt.add();
	}//main

}//class
