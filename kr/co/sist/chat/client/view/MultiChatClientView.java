package kr.co.sist.chat.client.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.chat.client.evt.MultiChatClientEvt;

@SuppressWarnings("serial")
public class MultiChatClientView extends JFrame {

	private JTextArea jtaTalkDisplay;
	private JTextField jtfServerIp, jtfNick, jtfTalk;
	private JButton jbtConnect, jbtCapture, jbtClose;
	private JScrollPane jspTalkDisplay;
	
	public MultiChatClientView(){
		super("채팅 클라이언트");
		
		jtaTalkDisplay = new JTextArea();

		jtfServerIp = new JTextField("211.63.89.",10);
		jtfNick = new JTextField(10);
		jtfTalk = new JTextField();
		jtfTalk.setBorder(new TitledBorder("대화"));
		
		jbtConnect = new JButton("접속");
		jbtCapture = new JButton("저장");
		jbtClose = new JButton("종료");
		
		jspTalkDisplay = new JScrollPane(jtaTalkDisplay);
		jspTalkDisplay.setBorder(new TitledBorder("대화내용"));
		
		jtaTalkDisplay.setEditable(false);
		
		JPanel jpNorth = new JPanel();
		jpNorth.add(new JLabel("서버주소", JLabel.CENTER));
		jpNorth.add(jtfServerIp);
		jpNorth.add(new JLabel("대화명", JLabel.CENTER));
		jpNorth.add(jtfNick);
		
		jpNorth.add(jbtConnect);
		jpNorth.add(jbtCapture);
		jpNorth.add(jbtClose);
		
		add("North", jpNorth);
		add("Center", jspTalkDisplay);
		add("South", jtfTalk);
		
		MultiChatClientEvt mcce = new MultiChatClientEvt(this);
		
		jbtConnect.addActionListener(mcce);
		jbtClose.addActionListener(mcce);
		jbtCapture.addActionListener(mcce);
		jtfTalk.addActionListener(mcce);
		
		addWindowListener(mcce);

		setBounds(100, 100, 600, 350);
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}//MultiChatClientView

	public JTextArea getJtaTalkDisplay() {
		return jtaTalkDisplay;
	}//getJtaTalkDisplay

	public JTextField getJtfServerIp() {
		return jtfServerIp;
	}//getJtfServerIp

	public JTextField getJtfNick() {
		return jtfNick;
	}//getJtfNick

	public JTextField getJtfTalk() {
		return jtfTalk;
	}//getJtfTalk

	public JButton getJbtConnect() {
		return jbtConnect;
	}//getJbtConnect

	public JButton getJbtCapture() {
		return jbtCapture;
	}//getJbtCapture

	public JButton getJbtClose() {
		return jbtClose;
	}//getJbtClose

	public JScrollPane getJspTalkDisplay() {
		return jspTalkDisplay;
	}//getJspTalkDisplay
	
}//class
