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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ZipcodeViewEvt extends WindowAdapter implements ActionListener {

	private ZipcodeView zv;

	public ZipcodeViewEvt(ZipcodeView zv) {
		this.zv = zv;
	}// ZipcodeViewEvt

	@Override
	public void windowClosing(WindowEvent we) {
		zv.dispose();
	}// windowClosing

	@Override
	public void windowClosed(WindowEvent we) {

	}// windowClosed

	/**
	 * PreparedStatement를 사용하여 SQLInjection 방어
	 * 
	 * @param dong
	 * @return
	 * @throws SQLException
	 */
	public List<ZipcodeVO> selectZipcode(String dong) throws SQLException {
		List<ZipcodeVO> list = new ArrayList<ZipcodeVO>();

		// 1. 드라이버로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 2. Connection 얻기
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "scott";
			String pass = "tiger";
			con = DriverManager.getConnection(url, id, pass);
			// 3. 쿼리문 생성객체 얻기
			
			StringBuilder selectZipcode = new StringBuilder();
			selectZipcode.append(" select zipcode,sido,gugun,dong,nvl(bunji, ' ') as bunji ").append(" from zipcode ")
					.append(" where dong like ?||'%'");

//			System.out.println( selectZipcode );
			pstmt = con.prepareStatement(selectZipcode.toString());
			// 4. 바인드 변수에 값 넣기
			pstmt.setString(1,  dong);
			
			// 5. 쿼리 수행 후 결과 얻기

			rs = pstmt.executeQuery();

			ZipcodeVO zv = null;

			while (rs.next()) { // 조회된 레코드가 존재한다면
				zv = new ZipcodeVO(rs.getString("zipcode"), rs.getString("sido"), rs.getString("gugun"),
						rs.getString("dong"), rs.getString("bunji"));
				list.add(zv);
			} // end while

		} finally {
			// 6. 연결끊기 (끊는건 역순으로 끊어줘야 한다.)
			if (rs != null) { rs.close(); } // end if
			if (pstmt != null) { pstmt.close();	} // end if
			if (con != null) { con.close(); } // end if
		} // end finally

		return list;
	}// selectZipcode

	public void searchZipcode(String dong) {

		try {
			// DB에서 조회한 결과를 받아서
			List<ZipcodeVO> listZip = selectZipcode(dong);
			// DefaultTableModel에 추가 => JTable 반영된다.
			DefaultTableModel dtm = zv.getDtmZipcode();
			// D.T.M 초기화
			dtm.setRowCount(0);
			// D.T.M에 행(row)추가

			String[] rowData = null;
			for (ZipcodeVO zv : listZip) {
				rowData = new String[2];
				rowData[0] = zv.getZipcode();
				rowData[1] = zv.getSido() + " " + zv.getGugun() + " " + zv.getDong() + " " + zv.getBunji();
				// D.T.M에 행(Row : 우편번호, 주소) 추가
				dtm.addRow(rowData);

			} // end for

			if (listZip.isEmpty()) {
				rowData = new String[2];
				rowData[0] = "";
				rowData[1] = "해당 동은 존재하지 않습니다.";

				dtm.addRow(rowData);
			} // end if

		} catch (SQLException se) {
			JOptionPane.showMessageDialog(zv, "DB에서 문제 발생");
			se.printStackTrace();
		} // end catch

	}// searchZipcode

	@Override
	public void actionPerformed(ActionEvent ae) {
		String dong = zv.getJtfDong().getText().trim();
		if (!dong.equals("")) {
			searchZipcode(dong);
		} // end if

	}// actionPerformed

}// ZipcodeViewEvt
