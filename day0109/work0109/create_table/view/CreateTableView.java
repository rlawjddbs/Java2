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
		super("테이블 만들기");
		
		//라벨 컴포넌트 생성
		jlTableName = new JLabel("테이블명");
		jlTableName.setPreferredSize(new Dimension(60, 25));
		jlColumnName = new JLabel("컬럼명");
		jlColumnName.setPreferredSize(new Dimension(50, 25));
		jlDataType = new JLabel("데이터형");
		jlColumnSize = new JLabel("크기");
		jlConstraint = new JLabel("제약사항");
		jlConstraintName = new JLabel("제약사항명");
		
		//텍스트 필드 컴포넌트 생성
		jtfTableName = new JTextField();
		jtfTableName.setPreferredSize(new Dimension(80, 25));
		jtfColumnName = new JTextField();
		jtfColumnName.setPreferredSize(new Dimension(80, 25));
		jtfColumnSize = new JTextField();
		jtfColumnSize.setPreferredSize(new Dimension(80, 25));
		jtfConstraintName = new JTextField();
		jtfConstraintName.setPreferredSize(new Dimension(80, 25));
		jtfConstraintName.setEditable(false);
		
		//버튼 컴포넌트 생성
		jbtAddTable = new JButton("추가");
		jbtAddTable.setPreferredSize(new Dimension(60, 25));
		jbtAddColumn = new JButton("추가");
		jbtAddColumn.setPreferredSize(new Dimension(60, 25));
		jbtCreateTable = new JButton("테이블생성");
		jbtCreateTable.setPreferredSize(new Dimension(60, 25));
		jbtReset = new JButton("초기화");
		
		//콤보박스 컴포넌트 생성
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
		
		//JTA 컴포넌트 생성
		jtaQueryZone = new JTextArea(10, 10);
		jtaQueryZone.setEditable(false);
		//JScrollPane 컴포넌트 생성 및 JTA 담기
		jsp = new JScrollPane(jtaQueryZone);
		jsp.setBorder(new TitledBorder("쿼리문 확인"));
		
		//패널생성 및 컴포넌트 담기
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
		
		// 프레임에 담기
		add("North", north);
		add("Center", jsp);
		add("South", row4);
		
		// 이벤트 등록
		CreateTableEvt cte = new CreateTableEvt(this);
		jbtAddTable.addActionListener(cte);
		jbtAddColumn.addActionListener(cte);
		jbtReset.addActionListener(cte);
		jbtCreateTable.addActionListener(cte);
		jcConstraint.addActionListener(cte);
		
		// 배치 및 가시화 및 종료설정
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
