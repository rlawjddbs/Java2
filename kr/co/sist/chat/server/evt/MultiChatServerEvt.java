package kr.co.sist.chat.server.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import kr.co.sist.chat.client.view.MultiChatClientView;
import kr.co.sist.chat.server.helper.MultiChatServerHelper;
import kr.co.sist.chat.server.view.MultiChatServerView;

/**
 * 이벤트 처리를 위해 서버 이벤트 전용 클래스를 따로 생성
 * @author owner
 */
public class MultiChatServerEvt extends WindowAdapter implements ActionListener, Runnable {

	private MultiChatServerView mcsv;
	private Thread threadServer; // 접속자에 대한 처리를 하기 위한 Thread
	private ServerSocket server; // PORT 열기
	private List<MultiChatServerHelper> listClient; // 모든 접속자를 관리할 List

	public MultiChatServerEvt(MultiChatServerView mcsv) {
		this.mcsv = mcsv;
		listClient = new ArrayList<MultiChatServerHelper>(); 
		
		// List는 검색의 기능이 있으며 중복데이터를 허용한다. 
		// set은 중복데이터를 허용하지 않고 검색의 기능이 없다.
		// map은 키와 값의 쌍이다.
		
	}// MultiChatServerEvt

	@Override
	public void run() { // start()에 의해서 run()은 불려진다.
		// 서버소켓을 열고 접속자 소켓을 받는다.
		try {
			server = new ServerSocket(35000);
			DefaultListModel<String> dlmTemp = mcsv.getDlmChatList();
			mcsv.getDlmChatList().addElement("서버가 35000 PORT를 열고 가동 중...");
			// 넘겨 받은 MCSV 인스턴스의 JList 에 
			// 서버가 열렸다는 내용을 추가하여 관리자에게 알려준다. 

			Socket someClient = null; // 접속자 소켓을 저장할 객체
			InetAddress ia = null; // 접속자의 IP(Internet Protocol) address

			// 접속자 소켓을 받아 스트림을 연결하고,
			// 대화를 읽거나 모든 접속자에게 전송하는 일.
			MultiChatServerHelper mcsh = null; 

			
			for (int cnt = 0; ; cnt++) { //조건식이 빠져있으므로 무한 루프
				
//				String nick = mccv.getJtfNick().getText().trim();
				someClient = server.accept(); // 접속을 시도하는 방문자에게 소켓을 열어준다.
				
				ia = someClient.getInetAddress(); // 방문자의 IP 주소를 참조변수에 담는다.
				
//				dlmTemp.addElement(/*"["+nick+*/"] ["+ ia + "] 접속자 접속"); // obj 객체를 Vector에 저장한다.

				// 소켓, 서버의 화면, 접속순서를 할당하여 Helper class를 생성한다.
				mcsh = new MultiChatServerHelper(someClient, dlmTemp, cnt, mcsv, listClient, mcsv.getJspList());
				
				// 생성된 같은 이름의 소켓객체를 여러개 관리하기위해 List에 추가
				listClient.add(mcsh);

//				//Helper에서 리스트를 사용할 수 있도록 할당
//				mcsh.setConnectList( listClient ); 

				// 접속자의 메세지를 읽어들기위한 Thread 시작
				mcsh.start();

			} // end for

		} catch (IOException e) {
			e.printStackTrace();
		} // 0 ~ 65535개의 PORT가 열릴 수 있다.

	}// run

	@Override
	public void windowClosing(WindowEvent e) { // MCSV에서 윈도우 이벤트 발생시 실행 됨
		// 윈도우 닫기를 누르면 실행 될 내용들
		mcsv.dispose(); // MCSV 객체를 죽인다.
	}// windowClosing

	@Override
	public void windowClosed(WindowEvent e) { // MCSV에서 윈도우 이벤트 발생시 실행 됨
		// 윈도우가 닫힌 뒤 실행 될 내용들
		try {
				// 윈도우가 닫힌 뒤 서버가 열려있다면 서버를 닫는다.
				if (server != null) {	server.close();} //end if
			} catch (IOException e1) {
				e1.printStackTrace();
			}//end catch
	}// windowClosed

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == mcsv.getJbtOpenServer()) { // 열기 버튼을 눌렀을 경우 발생하는 이벤트 처리
			
			if (threadServer == null) { // 쓰레드가 비어있다면
				threadServer = new Thread(this); // 내 클래스의 인스턴스를 가리키는 키워드(this) 입력
				threadServer.start(); // start() -> run() 자동 실행
				
				/* Open 버튼이 클릭되면 Thread 타입의 참조변수 threadServer가
				 * 가리키는 대상이 있는지 확인하며 가리키는 대상이 없을 경우 
				 * 새로운 Thread 인스턴스를 생성하여 참조변수에 할당한다. */
				
			} else {
				JOptionPane.showMessageDialog(mcsv, "채팅서버가 이미 가동중입니다.");
				
				/* 이미 threadServer가 참조하고 있는 대상(Thread 인스턴스)이 있다면 
				 * 이미 채팅서버가 가동중이라는 메세지를 띄운다. */
				
			} // end else
			
		} // end if
		if (ae.getSource() == mcsv.getJbtCloseServer()) { // 닫기 버튼을 눌렀을 경우 발생하는 이벤트 처리
			switch (JOptionPane.showConfirmDialog(mcsv, "채팅서버를 종료하시겠습니까?\n종료하시면 모든 접속자의 연결이 끊어집니다.")) {
			case JOptionPane.OK_OPTION:
				mcsv.dispose();
			}// end switch
		} // end if

	}// actionPerformed

}// MultiChatServerEvt
