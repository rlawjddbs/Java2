package day0109.work0109.create_table.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import day0109.work0109.create_table.view.CreateTableView;
import kr.co.sist.connection.GetConnection;

public class CreateTableEvt extends WindowAdapter implements ActionListener {
	private CreateTableView ctv;
	private boolean tableQueryCheck;
	private boolean newLine;
	
	public CreateTableEvt(CreateTableView ctv) {
		this.ctv = ctv;
	}// CreateTableEvt

	@Override
	public void actionPerformed(ActionEvent ae) {
		//���̺����� �߰���ư
		if (ae.getSource() == ctv.getJbtAddTable()) {
			if (checkTableName()) {
				addTableQuery();
			} else {
				JOptionPane.showMessageDialog(null, "���̺���� �Է��ϼ���.");
			} // end else
		} // end if (���̺����� �߰���ư)

		//�÷����� �߰���ư
		if (ae.getSource() == ctv.getJbtAddColumn()) {
			if (checkTableName()) {
				if (checkInt() && tableQueryCheck == true) {
					addColumnQuery();
				} // end if
			} else {
				JOptionPane.showMessageDialog(null, "���� ���̺���� �Է����ּ���.");
			} // end else
		} // end if (�÷����� �߰���ư)

		//������ �ʱ�ȭ ��ư
		if (ae.getSource() == ctv.getJbtReset()) {
			jtaReset();
		} // end if (������ �ʱ�ȭ ��ư)

		if (ae.getSource() == ctv.getJbtCreateTable()) {
			try {
				createTable();
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
		}//end if
		
		//������� �޺��ڽ�
		if(ae.getSource() == ctv.getJcConstraint()) {
			jcConstraintSelectEvt();
		}//end if (������� �޺��ڽ�)
		
		
		
	}// actionPerformed

	public boolean checkTableName() {
		return !ctv.getJtfTableName().getText().equals("");
	}// checkTableName

	public boolean checkInt() {
		try {
			Integer.parseInt(ctv.getJtfColumnSize().getText());
			return true;
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "�÷��� ũ��� ���ڸ� �Է��ϼ���.");
			return false;
		}
	}// checkItNumber

	public void addTableQuery() {
		String tableName = ctv.getJtfTableName().getText().trim();
		StringBuilder query = new StringBuilder();
		query.append("create table ").append(tableName).append(" (\n\n").append(");");

		ctv.getJtaQueryZone().setText("");
		ctv.getJtaQueryZone().setText(query.toString().toUpperCase());
		tableQueryCheck = true;
	}// checkJtfTableName

	public void addColumnQuery() {
		String columnName = ctv.getJtfColumnName().getText().trim();
		String columnSize = ctv.getJtfColumnSize().getText().trim();
		String constraintName = ctv.getJtfConstraintName().getText().trim();

		if (!columnName.equals("") && !columnSize.equals("") /*&& !constraintName.equals("")*/) {
			StringBuilder temp = new StringBuilder(ctv.getJtaQueryZone().getText());
			StringBuilder columnQuery = new StringBuilder();
			StringBuilder resultQuery = new StringBuilder();

			if(newLine) {
				columnQuery.append(",").append("\n");	
			}//end if
			
			columnQuery.append("\t")
			.append(ctv.getJtfColumnName().getText()).append(" ")
			.append(ctv.getJcDataType().getSelectedItem().toString()).append("(")
			.append(ctv.getJtfColumnSize().getText()).append(") ");
			
			if(ctv.getJcConstraint().getSelectedItem().toString().equals("primary key")) {
				columnQuery.append("constraint ").append(ctv.getJtfConstraintName().getText()).append(" ");
			}//end if
			
			columnQuery.append(ctv.getJcConstraint().getSelectedItem().toString());
			
			
			String[] tempSplit = new String[3];
			tempSplit[0] = temp.toString().substring(0, (temp.toString().length())-3);
			tempSplit[1] = columnQuery.toString();
 			tempSplit[2] = temp.toString().substring((temp.toString().length())-3);
 			
 			resultQuery.append(tempSplit[0]).append(tempSplit[1]).append(tempSplit[2]);

 			ctv.getJtaQueryZone().setText(resultQuery.toString());
 			newLine = true;
		} else {
			JOptionPane.showMessageDialog(null, "�÷���, ũ��, ������׸��� ��� �Է����ּ���.");
		} // end else
	}// addColumnQuery

	public void jtaReset() {
		ctv.getJtaQueryZone().setText("");
		tableQueryCheck = false;
		newLine = false;
	}// jtaReset

	public void createTable() throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		//1. ����̹��ε�
		//2. Connection ���
		try {
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String id="scott";
		String pass="tiger";
		
			con = GetConnection.getInstance().getConn(url, id, pass);
			StringBuilder jtaText = new StringBuilder(ctv.getJtaQueryZone().getText());
			
			pstmt = con.prepareStatement(jtaText.toString().substring(0, jtaText.toString().length()-1));
			pstmt.execute();
		} finally {
			if(pstmt != null) {pstmt.close();}
			if(con != null) {con.close();}
		}//end finally
	}//createTable
	
	public void jcConstraintSelectEvt() {
		String select = ctv.getJcConstraint().getSelectedItem().toString();

		if(!select.equals("primary key")) {
			ctv.getJtfConstraintName().setEditable(false);
			return;
		}//end
		
		ctv.getJtfConstraintName().setEditable(true);
	}//selectJcConstraint
	
}// class
