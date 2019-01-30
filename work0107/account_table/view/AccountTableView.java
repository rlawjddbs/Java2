package work0107.account_table.view;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import work0107.account_table.evt.AccountTableEvt;
import work0107.account_table.vo.AccountTableVO;

@SuppressWarnings("serial")
public class AccountTableView extends JFrame {

	private JLabel jlbSelect;
	private JComboBox<String> jcTableName;
	private JButton jbtChoice;
	private DefaultTableModel dtmTableInfo; 
	
	
	public AccountTableView() {
		AccountTableEvt ate = new AccountTableEvt(this);
		
		jlbSelect = new JLabel("테이블 선택");
		jcTableName = new JComboBox<String>();
		try {
			List<AccountTableVO> tempVO = new ArrayList<AccountTableVO>(ate.searchAllTable()); 
			for(int i=0; i < tempVO.size(); i++) {
				jcTableName.addItem(tempVO.get(i).getTableName());
			}//end for
		} catch (SQLException e) {
			e.printStackTrace();
		}//end for
		jbtChoice = new JButton("선택");
		
		String[] columnNames = {"컬럼명","데이터형","크기","제약사항"};
		dtmTableInfo = new DefaultTableModel(columnNames, 0);
		JTable tabTableInfo = new JTable(dtmTableInfo);
		
		//컬럼 이동 막기
		tabTableInfo.getTableHeader().setReorderingAllowed(false);
		tabTableInfo.setRowHeight(17);
		
		tabTableInfo.getColumnModel().getColumn(0).setPreferredWidth(60);
		tabTableInfo.getColumnModel().getColumn(1).setPreferredWidth(60);
		tabTableInfo.getColumnModel().getColumn(2).setPreferredWidth(20);
		tabTableInfo.getColumnModel().getColumn(3).setPreferredWidth(100);
		
		JScrollPane jsp = new JScrollPane(tabTableInfo);
		
		JPanel panel = new JPanel();
		panel.add(jlbSelect);
		panel.add(jcTableName);
		panel.add(jbtChoice);
		
		add("North", panel);
		add("Center", jsp);
		
//		jcTableName.addActionListener(ate);
		jbtChoice.addActionListener(ate);
		
		setBounds(100, 100, 350, 400);
		setResizable(false);
		setVisible(true);
		
		
	}//AcountTableView


	public JComboBox<String> getJcTableName() {
		return jcTableName;
	}


	public JButton getJbtChoice() {
		return jbtChoice;
	}


	public DefaultTableModel getDtmTableInfo() {
		return dtmTableInfo;
	}

	
	
}//class
