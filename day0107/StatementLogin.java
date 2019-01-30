package day0107;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class StatementLogin extends JFrame{

	private JTextField jtfId;
	private JPasswordField jpfPass;
	private JButton jbtLogin;
	private JButton jbtCancel;
	
	public StatementLogin() {
		super("Statement 객체를 사용한 로그인");
		jtfId = new JTextField();
		jpfPass = new JPasswordField();
		jbtLogin = new JButton("로그인");
		jbtCancel = new JButton("취소");
		
		setLayout(new GridLayout(3, 1));
		JPanel panel = new JPanel();
		panel.add(jbtLogin);
		panel.add(jbtCancel);
		
		jtfId.setBorder(new TitledBorder("아이디"));
		jpfPass.setBorder(new TitledBorder("비밀번호"));
		
		add(jtfId);
		add(jpfPass);
		add(panel);
		
		
		StatementLoginEvt sle = new StatementLoginEvt(this);
		jtfId.addActionListener(sle);
		jpfPass.addActionListener(sle);
		jbtLogin.addActionListener(sle);
		jbtCancel.addActionListener(sle);
		addWindowListener(sle);
		
		setBounds(100, 100, 300, 180);
		setVisible(true);
		setResizable(false);
		
	}//StatementLogin

	public static void main(String[] args) {
		new StatementLogin();
	}//main

	public JTextField getJtfId() {
		return jtfId;
	}//getJtfId

	public JPasswordField getJpfPass() {
		return jpfPass;
	}//getJpfPass

	public JButton getJbtLogin() {
		return jbtLogin;
	}//getJbtLogin

	public JButton getJbtCancel() {
		return jbtCancel;
	}//getJbtCancel
	
	
}//class
