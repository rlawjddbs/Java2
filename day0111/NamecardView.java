package day0111;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class NamecardView extends JFrame {

	private JButton jbtImg, jbtAdd;
	private DefaultTableModel dtmNamecard;
	private JLabel jlPreview;
	private JTextField jtfName, jtfAddr;

	public NamecardView() {
		super("���԰���");
		JLabel jlName = new JLabel("�̸�");
		JLabel jlAddr = new JLabel("�ּ�");
		JLabel jlImg = new JLabel("�̹���");
		
		jbtImg = new JButton("�̹�������");
		jbtAdd = new JButton("�����߰�");
		
		String[] columnNames = {"��ȣ", "�̹���", "����", "�ּ�"};
		Object[][] data = new Object[1][4];
		
		data[0][0] = "";
		data[0][1] = "";
		data[0][2] = "";
		data[0][3] = "";
		
		dtmNamecard = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
		};
		
		jlPreview = new JLabel(new ImageIcon("C:/dev/workspace/javase_prj2/src/day0111/upload/no_image.png"));
		jtfName = new JTextField();
		jtfAddr = new JTextField();
		
		
		
		JTable jtaNamecardList = new JTable(dtmNamecard) {
			@Override
			public Class getColumnClass(int column) {
			return getValueAt(0, column).getClass();
			}
		};
		JScrollPane jsp = new JScrollPane(jtaNamecardList);
		jsp.setBorder(new TitledBorder("���Ը���Ʈ"));
		
		jtaNamecardList.getColumnModel().getColumn(0).setPreferredWidth(30);
		jtaNamecardList.getColumnModel().getColumn(1).setPreferredWidth(170);
		jtaNamecardList.getColumnModel().getColumn(2).setPreferredWidth(120);
		jtaNamecardList.getColumnModel().getColumn(3).setPreferredWidth(280);
		
		jtaNamecardList.setRowHeight(200);
		jtaNamecardList.getTableHeader().setReorderingAllowed(false);
//		jtaNamecardList.getTableHeader().setResizingAllowed(false);
		
		setLayout(null); //���� ��ġ
		
		jlName.setBounds(10, 20, 80, 25);
		jtfName.setBounds(80, 20, 170, 25);
		jlAddr.setBounds(10, 50, 80, 25);
		jtfAddr.setBounds(80, 50, 170, 25);
		jlImg.setBounds(10, 80, 80, 25);
		jlPreview.setBounds(80, 85, 167, 199);
		
		jbtImg.setBounds(80, 295, 167, 25);
		jbtAdd.setBounds(80, 325, 167, 25);
		jsp.setBounds(270, 10, 600, 342);
		
		
		add(jlName);
		add(jtfName);
		add(jlAddr);
		add(jtfAddr);
		add(jlImg);
		add(jlPreview);
		add(jbtAdd);
		add(jsp);
		
		add(jbtImg);
		
		
		
		// �̺�Ʈ ��� :
		NamecardViewController nvc = new NamecardViewController(this);
		addWindowListener( nvc );
		jbtAdd.addActionListener(nvc);
		jbtImg.addActionListener(nvc);
		
		setBounds(100, 100, 900, 400);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
	}// NamecardView

	public JButton getJbtImg() {
		return jbtImg;
	}

	public JButton getJbtAdd() {
		return jbtAdd;
	}

	public DefaultTableModel getDtmNamecard() {
		return dtmNamecard;
	}

	public JLabel getJlPreview() {
		return jlPreview;
	}

	public JTextField getJtfName() {
		return jtfName;
	}

	public JTextField getJtfAddr() {
		return jtfAddr;
	}

	public static void main(String[] args) {
		new NamecardView();
	}// main
}// class
