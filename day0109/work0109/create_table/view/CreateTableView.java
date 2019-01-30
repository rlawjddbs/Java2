package day0109.work0109.create_table.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import day0109.work0109.create_table.evt.CreateTableEvt;

public class CreateTableView extends JFrame {

	//JLabel Component
	private JLabel jlTableName, jlColumnName, jlDataType, jlColumnSize, jlConstraint, jlConstraintName;
	//JTF Component
	private JTextField jtfTableName, jtfColumnName, jtfColumnSize, jtfConstraintName;
	//JButton Component
	private JButton jbtAddTable, jbtAddColumn, jbtCreateTable, jbtReset;
	//JComboBox Component
	private JComboBox<String> jcConstraint, jcDataType;
	//JTextArea Component
	private JTextArea jtaQueryZone;
	//JScrollPane Component
	private JScrollPane jsp;
	
	public CreateTableView() {
		super("���̺� �����");
		
		//�� ������Ʈ ����
		jlTableName = new JLabel("���̺��");
		jlTableName.setPreferredSize(new Dimension(60, 25));
		jlColumnName = new JLabel("�÷���");
		jlColumnName.setPreferredSize(new Dimension(50, 25));
		jlDataType = new JLabel("��������");
		jlColumnSize = new JLabel("ũ��");
		jlConstraint = new JLabel("�������");
		jlConstraintName = new JLabel("������׸�");
		
		//�ؽ�Ʈ �ʵ� ������Ʈ ����
		jtfTableName = new JTextField();
		jtfTableName.setPreferredSize(new Dimension(80, 25));
		jtfColumnName = new JTextField();
		jtfColumnName.setPreferredSize(new Dimension(80, 25));
		jtfColumnSize = new JTextField();
		jtfColumnSize.setPreferredSize(new Dimension(80, 25));
		jtfConstraintName = new JTextField();
		jtfConstraintName.setPreferredSize(new Dimension(80, 25));
		jtfConstraintName.setEditable(false);
		
		//��ư ������Ʈ ����
		jbtAddTable = new JButton("�߰�");
		jbtAddTable.setPreferredSize(new Dimension(60, 25));
		jbtAddColumn = new JButton("�߰�");
		jbtAddColumn.setPreferredSize(new Dimension(60, 25));
		jbtCreateTable = new JButton("���̺����");
		jbtCreateTable.setPreferredSize(new Dimension(60, 25));
		jbtReset = new JButton("�ʱ�ȭ");
		
		//�޺��ڽ� ������Ʈ ����
		jcConstraint = new JComboBox<String>();
		jcConstraint.setPreferredSize(new Dimension(100, 25));
		jcConstraint.addItem("null");
		jcConstraint.addItem("primary key");
		jcConstraint.addItem("unique");
		jcConstraint.addItem("not null");
		jcDataType = new JComboBox<String>();
		jcDataType.setPreferredSize(new Dimension(100, 25));
		jcDataType.addItem("number");
		jcDataType.addItem("char");
		jcDataType.addItem("varchar2");
		jcDataType.addItem("date");
		
		//JTA ������Ʈ ����
		jtaQueryZone = new JTextArea(10, 10);
		jtaQueryZone.setEditable(false);
		//JScrollPane ������Ʈ ���� �� JTA ���
		jsp = new JScrollPane(jtaQueryZone);
		jsp.setBorder(new TitledBorder("������ Ȯ��"));
		
		//�гλ��� �� ������Ʈ ���
		JPanel row1 = new JPanel();
		JPanel row2 = new JPanel();
		JPanel row3 = new JPanel();
		JPanel row4 = new JPanel(new GridLayout(1, 2));
		JPanel north = new JPanel(new GridLayout(3, 1));

		
		row1.add(jlTableName);
		row1.add(jtfTableName);
		row1.add(jbtAddTable);
		
		row2.add(jlColumnName);
		row2.add(jtfColumnName);
		row2.add(jlDataType);
		row2.add(jcDataType);
		row2.add(jlColumnSize);
		row2.add(jtfColumnSize);
		
		row3.add(jlConstraint);
		row3.add(jcConstraint);
		row3.add(jlConstraintName);
		row3.add(jtfConstraintName);
		row3.add(jbtAddColumn);
		
		north.add(row1);
		north.add(row2);
		north.add(row3);
		
		row4.add(jbtReset);
		row4.add(jbtCreateTable);
		
		// �����ӿ� ���
		add("North", north);
		add("Center", jsp);
		add("South", row4);
		
		// �̺�Ʈ ���
		CreateTableEvt cte = new CreateTableEvt(this);
		jbtAddTable.addActionListener(cte);
		jbtAddColumn.addActionListener(cte);
		jbtReset.addActionListener(cte);
		jbtCreateTable.addActionListener(cte);
		jcConstraint.addActionListener(cte);
		
		// ��ġ �� ����ȭ �� ���ἳ��
		setBounds(300,300,600,500);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//CreateTableView

	public JTextField getJtfTableName() {
		return jtfTableName;
	}

	public JTextField getJtfColumnName() {
		return jtfColumnName;
	}

	public JTextField getJtfColumnSize() {
		return jtfColumnSize;
	}

	public JTextField getJtfConstraintName() {
		return jtfConstraintName;
	}

	public JButton getJbtAddTable() {
		return jbtAddTable;
	}

	public JButton getJbtAddColumn() {
		return jbtAddColumn;
	}

	public JButton getJbtCreateTable() {
		return jbtCreateTable;
	}

	public JButton getJbtReset() {
		return jbtReset;
	}

	public JComboBox<String> getJcConstraint() {
		return jcConstraint;
	}

	public JComboBox<String> getJcDataType() {
		return jcDataType;
	}

	public JTextArea getJtaQueryZone() {
		return jtaQueryZone;
	}

	public JScrollPane getJsp() {
		return jsp;
	}
	
	
	
	
}//class
