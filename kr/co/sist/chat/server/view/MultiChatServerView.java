package kr.co.sist.chat.server.view;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import kr.co.sist.chat.server.evt.MultiChatServerEvt;

/**
 *	채팅방 관리자 화면
 * @author owner
 */
@SuppressWarnings("serial")
public class MultiChatServerView extends JFrame{
	private JList<String> jlChatList;
	private JScrollPane jspList;
	private JButton jbtOpenServer, jbtCloseServer;
	private DefaultListModel<String> dlmChatList; //접속자의 정보를 담을 list
	
	public MultiChatServerView() {
		super(":::::::::::::::::::::::: 채팅방 관리자 ::::::::::::::::::::::::");
		
		dlmChatList = new DefaultListModel<String>(); // 리스트에 추가, 삭제될 목록을 관리
		jlChatList = new JList<String>(dlmChatList); // DefaultListModel 이 관리하는 목록을 시각화
		jspList = new JScrollPane(jlChatList); // 시각화된 리스트에 스크롤바를 달아줌
		
		jbtOpenServer = new JButton("서버시작");
		jbtCloseServer = new JButton("서버중지");
		
		JPanel btnPanel = new JPanel(); // 서버 시작, 중지 버튼을 담을 패널 생성
		btnPanel.add(jbtOpenServer);
		btnPanel.add(jbtCloseServer);
		
		jspList.setBorder(new TitledBorder("접속자 정보")); // 스크롤바에 제목을 달아놓음
		
		add("Center", jspList); // JFrame 중앙에 JList Model 이 담긴 JScrollPane 삽입
		add("South", btnPanel); // JFrame 하단에 버튼 두 개가 담긴 패널 삽입
		
		//이벤트 등록
		MultiChatServerEvt mcse =new MultiChatServerEvt(this);  // has a 관계로 이벤트 클래스 생성 
		jbtOpenServer.addActionListener( mcse ); // 서버 열기 버튼을 눌렀을 시 이벤트 등록
		jbtCloseServer.addActionListener( mcse ); // 서버 닫기 버튼을 눌렀을 시 이벤트 등록
		
		
		addWindowListener( mcse ); 
		
		setBounds(100, 100, 300, 500);
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}//MultiChatServerView

	public JList<String> getJlChatList() {
		return jlChatList;
	}//getJlChatList

	public JScrollPane getJspList() {
		return jspList;
	}//getJspList

	public JButton getJbtOpenServer() {
		return jbtOpenServer;
	}//getJbtOpenServer

	public JButton getJbtCloseServer() {
		return jbtCloseServer;
	}//getJbtCloseServer

	public DefaultListModel<String> getDlmChatList() {
		return dlmChatList;
	}//getDlmChatList
		
}//class
