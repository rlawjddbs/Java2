package day0110;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.connection.GetConnection;

/**
 *	Transactionó��
 * @author owner
 */
public class TestTransaction {
	// commit�� rollback�� DB�۾� �ܺο��� ó���� �� �ֵ��� class field ����.
	private Connection con = null;

	public boolean insert(TransactionVO tv) throws SQLException{
		// transaction�� ����� ������ ����ŭ ���� ���� ��ü ����
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		
		boolean flag = false;
		
		try {
		//1. ����̹� �ε�
		//2. Connection ���
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "scott";
			String pass = "tiger";
			con=GetConnection.getInstance().getConn(url, id, pass);
			//auto commit ����
			con.setAutoCommit(false);
		//3. ������ ������ü ���
			String sql = "insert into test_transaction1(subject, writer) values(?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tv.getSubject());
			pstmt.setString(2, tv.getWriter());
		//4.  
			int cnt = pstmt.executeUpdate();
			//3. ������ ������ü ���
			String sql1 = "insert into test_transaction2(subject, writer) values(?, ?)";
			pstmt1 = con.prepareStatement(sql1);
			pstmt1.setString(1, tv.getSubject());
			pstmt1.setString(2, tv.getWriter());

			//4.  
			int cnt1 = pstmt1.executeUpdate();
			
			// Ʈ����ǿ� �ش��ϴ� ��� ������ ��ǥ ���� ���� ���Ϥ���
			// commit�� rollback ���θ� �����Ѵ�.
			if( cnt == 1 && cnt1 == 1) {
				flag=true;
			}//end if
		} finally {
		//6. �������
		}//end finally
		
		return flag;
	}//insert
	
	public void add() {
		String inputData = JOptionPane.showInputDialog("����� �ۼ��ڸ� �Է����ּ���.\n����-�ۼ���");
		
		String[] data = inputData.split("-");
		
		if(data.length != 2) {
			JOptionPane.showMessageDialog(null, "�Է� ������ Ȯ���� �ּ���.");
			return;
		}//end if
		TransactionVO tv = new TransactionVO(data[0], data[1]);
		
		try {
			//DB������ ����ϴ� ������ ���� ����� �޾�
			boolean flag = insert(tv);
			if(flag) {
				//Ŀ���ϰų�
				con.commit();
				System.out.println("Ŀ��!!!");
			}else { 																// update�� delete�� transaction�� ��
				//�ѹ��Ѵ�.
				con.rollback();
				System.out.println("update�� delete �ѹ�!!!");
			}//end else
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.println("insert �ѹ�!!!");
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
