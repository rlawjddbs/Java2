package day0111;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class BtnLogo extends JFrame implements ActionListener {

	// JTable 컴포넌트 생성
	private JTable jtab;
	private DefaultTableModel dtm;

	// 이미지 아이콘을 배열로 관리
	private ImageIcon[] ii;

	// 컬럼명
	private String[] columnNames = { "로고", "URL", "특징", "입력" };
	// 이미지 아이콘 경로
	private String[] imgDir = { "C:/dev/workspace/javase_prj2/src/day0111/images/daum.png",
			"C:/dev/workspace/javase_prj2/src/day0111//images/naver.png",
			"C:/dev/workspace/javase_prj2/src/day0111/images/google.png" };
	// 주소
	private String[] url = { "https://www.daum.net", "https://naver.com", "https://google.com" };
	// 브랜드 특징
	private String[] brandCharacter = { "카카오", "네이버", "검색의 왕" };
	private JButton[] jbtns;

	public BtnLogo() {

		ii = new ImageIcon[3];
		jbtns = new JButton[3];
		for (int i = 0; i < ii.length; i++) {
			ii[i] = new ImageIcon(imgDir[i]);
			jbtns[i] = new JButton("입력");
		} // end for

		Object[][] rowData = new Object[3][4];

		for (int i = 0; i < rowData.length; i++) {
			rowData[i][0] = ii[i];
			rowData[i][1] = url[i];
			rowData[i][2] = brandCharacter[i];
			rowData[i][3] = jbtns[i];
		} // end for

		dtm = new DefaultTableModel(rowData, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCell
		};
		// 입력된 클래스가 그대로 Cell(Columns)에 표현되도록 method Override
		// getColumnClass(int column)
		jtab = new JTable(dtm) {
			@Override
			public Class getColumnClass(int column) {
				// row - JTable에 입력된 이차원 배열의 행에 속한다면
				// 모든 컬럼의 값을 입력된 형으로 반환한다.
					return getValueAt(0, column).getClass();
			}// class
		};
		jtab.getTableHeader().setReorderingAllowed(false); // 컬럼의 이동 막기
		jtab.getTableHeader().setResizingAllowed(false); // 컬럼의 크기 변경 막기

		jtab.setRowHeight(70);
		JScrollPane jsp = new JScrollPane(jtab);

		add("Center", jsp);
		setBounds(100, 100, 500, 275);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}// BtnLogo

	@Override
	public void actionPerformed(ActionEvent ae) {

	}// actionPerformed

	public static void main(String[] args) {
		new BtnLogo();
	}// main

}// class
