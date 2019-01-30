package kr.co.sist.chat.server.helper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 * 서버에서 생성하며 접속자 소켓에 스트림을 얻어와서 관리하는 클래스.<br>
 * 접속자가 존재하면 접속자 소켓에 얻어낸 스트림에서 메세지를 무한루프로 읽어들이고 모든 접속자에게 메세지 출력.
 * 
 * @author owner
 *
 */
public class MultiChatServerHelper extends Thread {
	private Socket someClient;
	private DataInputStream readStream;
	private DataOutputStream writeStream;
	private DefaultListModel<String> dlm;
	private int cnt;
	private List<MultiChatServerHelper> connectList;
	private JFrame jf;
	private JScrollPane jsp;
	private String nick;

	/**
	 * 접속자 소켓을 받아서 스트림을 연결하고 스트림을 얻어 메세지를 읽거나 보낼 수 있는 상태로 만든다.
	 * 
	 * @param socket	접속자소켓
	 * @param dlm		접속자 관리창을 사용하기 위한 Model 객체
	 * @param cnt		접속 순서
	 * @param jf		View 로 사용중인 MCSV 클래스의 인스턴스
	 * @param list		소켓 객체들의 목록(접속자 명단)
	 * @param jsp		MCSV의 JScroll Pane
	 */
	public MultiChatServerHelper(Socket socket, DefaultListModel<String> dlm, int cnt, JFrame jf,
			List<MultiChatServerHelper> list, JScrollPane jsp) {
		someClient = socket;	// 매개변수로 건네받은 소켓 객체의 주소를 Helper 클래스의 멤버 변수에 저장
		this.dlm = dlm;			// 매개변수로 건네받은 dlm 모델 객체의 주소를 클래스의 멤버변수 dlm에 저장
		this.cnt = cnt;			// 매개변수로 건네받은 접속자의 접속순번을 클래스의 멤버변수 cnt에 저장
		this.jf = jf;				// 매개변수로 건네받은 MCSV 클래스의 인스턴스 주소를 클래스의 멤버변수 jf에 저장 
		connectList = list;		// 매개변수로 건네받은 소켓객체들의 목록을 클래스의 멤버변수 connectList에 저장
		this.jsp = jsp;			// 매개변수로 건네받은 MCSV의 인스턴스에 포함된 JScrollPane을 클래스의 멤버변수 jsp에 저장
		
		// 스트림 얻기
		try {
			readStream = new DataInputStream(someClient.getInputStream());
			writeStream = new DataOutputStream(someClient.getOutputStream());
			
			//접속자가 보내오는 nick을 받는다.
			nick = readStream.readUTF();
			
			//서버창에 접속 메세지 출력
			dlm.addElement("["+ someClient.getInetAddress()+" : "+ someClient.getLocalPort()+" / "+nick+"] 님이 접속하셨습니다.");
			
			// 서버에 접속한 접속자에게 메세지를 출력
			broadcast("[" + cnt + "] 번째 접속자가 [ " + nick + " ]으로 채팅방에 접속 하였습니다.");
			jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
		} catch (IOException ie) {
			JOptionPane.showMessageDialog(jf, "접속자 연결 중 문제 발생....");
			ie.printStackTrace();
		} // end catch

	}// MultiChatServerHelper 인자가 있는 생성자 Overloading

//	public void setConnectList(List<MultiChatServerHelper> list) {
//		connectList = list;
//	}// setConnectList

	@Override
	public void run() {

		if (readStream != null) {
			try {
				String revMsg = "";
				while (true) { // 서버에서 보내오는 모든 메세지를 읽어서, 모든 접속자에게 뿌린다.
					revMsg = readStream.readUTF();
					broadcast(revMsg);
				} // end while
			} catch (IOException ie) {
				// 접속자가 퇴실하면 해당 접속자를 리스트에서 삭제한다.
				connectList.remove( this ); // 지금 나가고 있는 나를 지워야 함.
				//메세지를  읽어들이지 못하는 상태라면 접속자가 연결을 종료한 상태
				dlm.addElement(cnt+" / "+nick+" 접속자 퇴실");
				broadcast("["+nick+"] 님이 퇴실하였습니다.");
				
				jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
				
				ie.printStackTrace();
			} // end if

		} // end catch

	}// run

	/**
	 * 모든 접속자에게 메세지를 뿌려주는 일. synchronized : MultiThread에서 동시호출 막기 (동기화 처리)
	 * 
	 * @param msg
	 */
	public synchronized void broadcast(String msg) {

		MultiChatServerHelper mcsh = null;
		try {
			for (int i = 0; i < connectList.size(); i++) {
				mcsh = connectList.get(i); // list에서 Helper 객체를 얻고
				mcsh.writeStream.writeUTF(msg); // 출력 스트림에 출력
				mcsh.writeStream.flush(); // 목적지로 분출
			} // end for
		} catch (IOException e) {
			JOptionPane.showMessageDialog(jf, "[" + cnt + "] 번째 접속자에게 메세지를 보낼 수 없습니다.");
			e.printStackTrace();
		} // end catch

	}// broadcast

}// class
