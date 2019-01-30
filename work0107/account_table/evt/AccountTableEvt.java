package work0107.account_table.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import work0107.account_table.view.AccountTableView;
import work0107.account_table.vo.AccountTableVO;

public class AccountTableEvt extends WindowAdapter implements ActionListener, ItemListener {

	private AccountTableView atv;

	public AccountTableEvt() {

	}// AccountTableEvt

	public AccountTableEvt(AccountTableView atv) {
		this.atv = atv;
	}// AccountTableEvt

	public Connection getConn() throws SQLException {
		Connection con = null;

		// 1. 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 2.
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "scott";
		String pass = "tiger";

		con = DriverManager.getConnection(url, id, pass);
		return con;
	}// getConn

	public List<AccountTableVO> searchAllTable() throws SQLException {
		List<AccountTableVO> list = new ArrayList<AccountTableVO>();
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1. 드라이버 로딩
			// 2. Connection 얻기
			con = getConn();

			// 3. 쿼리문 생성객체 얻기 (PreparedStatement)
			String selectTableName = "select tname from tab";
			pstmt = con.prepareStatement(selectTableName);
//			stmt = con.createStatement();
			// 4. 쿼리문 수행 후 결과 얻기
//			String selectTname = "select tname from tab";
//			rs = stmt.executeQuery(selectTname);
			rs = pstmt.executeQuery();

			AccountTableVO atvo = null;

			while (rs.next()) {
				atvo = new AccountTableVO(rs.getString("TNAME"));
				list.add(atvo);
			} // end while

		} finally {
			// 6. 연결끊기
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
	} // searchAllTable

	@Override
	public void windowClosing(WindowEvent we) {

	}// windowClosing

	@Override
	public void windowClosed(WindowEvent we) {

	}// windowClosed

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == atv.getJbtChoice()) {
			if (atv.getJcTableName() != null) {
				String selectedItem = atv.getJcTableName().getSelectedItem().toString();
				try {
					getTableInfo(selectedItem);
				} catch (SQLException e) {
					e.printStackTrace();
				} // end catch
			} // end if
		} // end if
	}// actionPerformed

	@Override
	public void itemStateChanged(ItemEvent ie) {
		if (ie.getSource() == atv.getJbtChoice()) {
			
		} // end if
	}// itemStateChanged
	
	public void getTableInfo(String selectedItem) throws SQLException {
		List<AccountTableVO> list = new ArrayList<AccountTableVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		ResultSetMetaData rsmd = null;

		try {
			con = getConn();
			StringBuilder query = new StringBuilder();
			query
			.append("select utc.column_name, utc.data_type, utc.data_length, ucc.constraint_name")
			.append(" from user_tab_columns utc, user_cons_columns ucc")
			.append(" where (utc.table_name = ucc.table_name (+))")
			.append(" and (utc.column_name  = ucc.column_name (+))")
			.append(" and utc.table_name = ?");
			
			pstmt = con.prepareStatement(query.toString());
			
			pstmt.setString(1, selectedItem);

			rs = pstmt.executeQuery();


//			String[] columnName = { "컬럼명", "데이터형", "크기", "제약사항" };
			String[] rowData = new String[4];
			atv.getDtmTableInfo().setRowCount(0);
			while(rs.next()) {
				rowData[0] = rs.getString("column_name");
				rowData[1] = rs.getString("data_type");
				rowData[2] = rs.getString("data_length");
				rowData[3] = rs.getString("constraint_name");
				atv.getDtmTableInfo().addRow(rowData);
			}//end
			
		} finally {
			if (rs != null) {
				rs.close();
			} // end if
			if (pstmt != null) {
				pstmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // end finally
		
	}// getTableInfo

//	public static void main(String[] args) {
//		AccountTableEvt ate = new AccountTableEvt();
//		try {
//			ate.getTableInfo("EMP");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
}// class
