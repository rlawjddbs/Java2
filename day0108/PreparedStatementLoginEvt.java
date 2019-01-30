package day0108;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class PreparedStatementLoginEvt extends WindowAdapter implements ActionListener {

	private PreparedStatementLogin sl;

	public PreparedStatementLoginEvt(PreparedStatementLogin sl) {
		this.sl = sl;
	}// StatementLoginEvt

	public boolean chkNull(String id, String pass) {
		boolean flag = false;

		try {
			if (id.equals("")) {
				JOptionPane.showMessageDialog(sl, "���̵�� �ʼ��Է�");
				sl.getJtfId().requestFocus();
				flag = true;
				// ���ܸ� ������ �߻� : throw new ����ó��Ŭ������();
				throw new LoginException();
			} // end if
			if (pass.equals("")) {
				JOptionPane.showMessageDialog(sl, " ��й�ȣ�� �ʼ��Է�");
				sl.getJpfPass().requestFocus();
				flag = true;
			} // end if
		} catch (LoginException le) {
			le.printStackTrace();
		} // end catch

		return !flag;
	}// chkNull

	public String login(String id, String pass) throws SQLException{
		String name = "";
		//1. ����̹��ε�
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}//end catch
		
		Connection con = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		
		try {
			//2. Connection ���
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String dbo_id = "scott";
			String dbo_pass = "tiger";
			
			con = DriverManager.getConnection(url, dbo_id, dbo_pass);
			//3. ������ ������ü ���
			StringBuilder selectName = new StringBuilder();
			selectName.append("select name from test_login where id=? and pass=?");

			pstmt = con.prepareStatement(selectName.toString());
			//.4 ���ε� ������ �� �ֱ�
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			
			System.out.println(selectName);
			//5. ������ ���� �� ��� ���
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {//�Էµ� ���̵�� ��й�ȣ�� �´� �̸��� ���� => �α��� ����
				name = rs.getString("name");
			}//end if
			
		} finally {
			//6. ���� ����
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return name;
	}//login
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == sl.getJtfId() || ae.getSource() == sl.getJpfPass()
				|| ae.getSource() == sl.getJbtLogin()) {
			String id = sl.getJtfId().getText().trim();
			String pass = new String(sl.getJpfPass().getPassword()).trim();
			if (chkNull(id, pass)) {
				
				try {
					String name = login(id, pass);
					
					if( name.isEmpty()) {
						JOptionPane.showMessageDialog(sl, "���̵� ��й�ȣ�� Ȯ�����ּ���.");
						sl.getJtfId().setText("");
						sl.getJpfPass().setText("");
						sl.getJtfId().requestFocus();
						return;
					}//end if
					
					JDialog jd = new JDialog(sl, "�α��� ����",true);
					jd.add(new JLabel(name+"�� �α��� �ϼ̽��ϴ�."));
					jd.setBounds(sl.getX()+100, sl.getY()+100, 300, 200);
					jd.setVisible(true);
					jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					
//					JOptionPane.showMessageDialog(sl, name+"�� �α��� �ϼ̽��ϴ�.");
				}catch(SQLException se) {
					JOptionPane.showMessageDialog(sl, "DB �۾� �� ������ �߻��Ͽ����ϴ�.");
					se.printStackTrace();
				}//end catc
				
			} // end if
		} // end if

		if (ae.getSource() == sl.getJbtCancel()) {
			sl.getJtfId().setText("");
			sl.getJpfPass().setText("");
			sl.getJtfId().requestFocus();
		} // end if

	}// actionPerformed

	@Override
	public void windowClosing(WindowEvent we) {
		sl.dispose();
	}// windowClosing

	@Override
	public void windowClosed(WindowEvent we) {

	}// windowClosed

}// class
