package day0109;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.connection.GetConnection;

/**
 * �÷����� �������� ����Ǵ� ���<br>
 * �÷����� �Է¹޾� �ش� �÷������� ��ȸ<br>
 * EMP ���̺� ��ȸ �����ȣ�� �÷����� �Է¹޾� ��ȸ
 * 
 * @author owner
 */
public class DynamicColumn {

	public DynamicColumn() throws SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String[] columnName = { "ename", "job", "mgr", "hiredate", "sal", "comm", "deptno" };

		String input = JOptionPane.showInputDialog("�����ȣ�� �÷��� �ϳ��� �Է����ּ���.\n ��) �����ȣ,�÷���");
		String[] temp = input.split(","); // ���� input(InputDialog)�� �Էµ� ���� ��ǥ�� ����, �� ������ ������ temp �迭�� ��´�.

		if (temp.length != 2) { // �迭 temp�� ��䰪�� �� ������ �ƴ� ���
			JOptionPane.showMessageDialog(null, "�Է������� Ȯ���ϼ���.");
			return;
		} // end if

		try {
			int empno = Integer.parseInt(temp[0]);

			String inputColumn = temp[1].trim();

			boolean columnFlag = false;
			for (String column : columnName) {
				if (column.equals(inputColumn)) { // DB ���̺��� �÷���� ���� �÷����̶��
					columnFlag = true;
				} // end if
			} // end for

			if (!columnFlag) {
				JOptionPane.showMessageDialog(null, input + "�� EMP���̺� �������� �ʽ��ϴ�.");
				return;
			} // end if

			try {
				// 1.
				// 2.
				String url = "jdbc:oracle:thin:@localhost:1521:orcl";
				String id = "scott";
				String pass = "tiger";
				con = GetConnection.getInstance().getConn(url, id, pass);
				// 3.

				if (inputColumn.equals("hiredate")) {
					inputColumn = "to_char(hiredate, 'yyyy-mm-dd day hh24:mi:ss') hiredate";
				} // end if

				StringBuilder selectEmp = new StringBuilder();
				// �÷���, ���̺���� bind ������ ó������ �ʴ´�. �������� ���� �־� ����Ѵ�.
				// selectEmp.append("select ? from emp ")
				selectEmp.append("select ").append(inputColumn).append("from emp ").append(" where empno=?");

				pstmt = con.prepareStatement(selectEmp.toString());
				// 4.
				// pstmt.setString(1, inputColumn); // ���ε� ������ �÷���, ���̺�� �ڸ��� ���Ǹ� �ν��� �� ��.
				pstmt.setInt(1, empno);
				
				// 5.
				rs = pstmt.executeQuery();

				if (rs.next()) { // �����ȣ�� ��ȸ�� ���ڵ尡 �����Ѵٸ�

					String stringData = "";
					int intData = 0;

					if (temp[1].trim().equals("ename") || temp[1].trim().equals("job")
							|| temp[1].trim().equals("hiredate")) {
						stringData = rs.getString(temp[1].trim());
					} else {
						intData = rs.getInt(temp[1].trim());
					} // end else
					JOptionPane.showMessageDialog(null,
							temp[1] + "���� ��ȸ�� �� : " + (intData == 0 ? stringData : intData));
				} else {
					JOptionPane.showMessageDialog(null, "�Է��� �����ȣ�� �������� �ʽ��ϴ�.");
				} // end else

				// 6.
			} finally {
				if (rs != null) {rs.close();} // end if
				if (pstmt != null) {pstmt.close();} // end if
				if (con != null) {con.close();} // end if
			} // end finally

		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "�����ȣ�� ���� �̾�� �մϴ�.");
		} // end catch
	}// DynamicColumn

	public static void main(String[] args) {
		try {
			new DynamicColumn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// main

}// class
