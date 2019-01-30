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

	// JTable ������Ʈ ����
	private JTable jtab;
	private DefaultTableModel dtm;

	// �̹��� �������� �迭�� ����
	private ImageIcon[] ii;

	// �÷���
	private String[] columnNames = { "�ΰ�", "URL", "Ư¡", "�Է�" };
	// �̹��� ������ ���
	private String[] imgDir = { "C:/dev/workspace/javase_prj2/src/day0111/images/daum.png",
			"C:/dev/workspace/javase_prj2/src/day0111//images/naver.png",
			"C:/dev/workspace/javase_prj2/src/day0111/images/google.png" };
	// �ּ�
	private String[] url = { "https://www.daum.net", "https://naver.com", "https://google.com" };
	// �귣�� Ư¡
	private String[] brandCharacter = { "īī��", "���̹�", "�˻��� ��" };
	private JButton[] jbtns;

	public BtnLogo() {

		ii = new ImageIcon[3];
		jbtns = new JButton[3];
		for (int i = 0; i < ii.length; i++) {
			ii[i] = new ImageIcon(imgDir[i]);
			jbtns[i] = new JButton("�Է�");
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
		// �Էµ� Ŭ������ �״�� Cell(Columns)�� ǥ���ǵ��� method Override
		// getColumnClass(int column)
		jtab = new JTable(dtm) {
			@Override
			public Class getColumnClass(int column) {
				// row - JTable�� �Էµ� ������ �迭�� �࿡ ���Ѵٸ�
				// ��� �÷��� ���� �Էµ� ������ ��ȯ�Ѵ�.
					return getValueAt(0, column).getClass();
			}// class
		};
		jtab.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� ����
		jtab.getTableHeader().setResizingAllowed(false); // �÷��� ũ�� ���� ����

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
