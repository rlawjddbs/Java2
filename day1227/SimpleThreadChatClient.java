package day1227;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * Thread를 사용하여 무한프로 메세지를 읽어들이는 코드를 화면디자인과, 메세지를 보내는 코드와 동시에 실행시킨다.
 * 
 * @author owner
 */
@SuppressWarnings("serial")
public class SimpleThreadChatClient extends JFrame implements ActionListener, Runnable {

	private JTextArea jta;
	private JTextField jtf;
	private JScrollPane jsp;

	private Socket client; // 서버와 연결하기 위해
	private DataInputStream readStream; // 서버의 데이터를 읽기위한 스트림
	private DataOutputStream writeStream; // 서버로 데이터를 보내기위한 스트림
	
	private String serverNick; //서버의 대화명
	private String clientNick; //클라이언트의 대화명

	public SimpleThreadChatClient() {
		super(":::::::::::::::::::::::::::::::::::::::::::::::::::채팅서버:::::::::::::::::::::::::::::::::::::::::::::::::::");

		jta = new JTextArea();
		jta.setBorder(new TitledBorder("대화내용"));
		jta.setEditable(false);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);

		jsp = new JScrollPane(jta);

		jtf = new JTextField();
		jtf.setBorder(new TitledBorder("대화입력"));

		add("Center", jsp);
		add("South", jtf);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}// windowClosing
			@Override
			public void windowClosed(WindowEvent we) {
				try {
					close(); // 클라이언트와 연결하고 있는 스트림, 소켓, 서버소켓을 종료 한다.
//					System.exit(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}// windowClosed
		});

		setBounds(100, 100, 300, 400);
		setVisible(true);
		jtf.requestFocus(); // 커서를 jtf에 위치시킨다.

//		try {
//			connectToServer();
//			//Thread와 현재 클래스를 has a 관계로 설정
//			Thread t = new Thread(this);
//			//Thread 시작
//			t.start();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} // end catch

//		 readMsg() method는 무한 루프 이므로 그 아래에 이벤트를 등록하려 할 경우
//		 동작되지 않는다. 만약 addWindowListener()가 readMsg() method 하위에 위치할 경우
//		 종료 처리 또한 되지 않게 된다.
		
		
		jtf.addActionListener(this); // 확인 후 주석처리

	}// SimpleChatServer

	@Override
	public void actionPerformed(ActionEvent ae) {
		try {
			sendMsg();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// actionPerformed

	public void close() throws IOException {
		try {
			if (readStream != null) {
				readStream.close();
			} // end if
			if (writeStream != null) {
				writeStream.close();
			} // end if
		} finally {
			if (client != null) {
				client.close();
			} // end if
		} // end finally
	}// close

	/**
	 * 2. 소켓을 생성하여 서버에 연결하고 대화를 읽거나 보내기 위해<br>
	 * 4. 스트림을 연결한다.
	 * 
	 * @throws IOException
	 */
	public void connectToServer() throws IOException {
		clientNick = JOptionPane.showInputDialog("대화명 입력");
		// 2.
//		client = new Socket("211.63.89.153", 65535);
		client = new Socket("211.63.89.153", 35000);
		
		// 4. 스트림 연결
		readStream = new DataInputStream(client.getInputStream());
		writeStream = new DataOutputStream(client.getOutputStream());

		//서버로 대화명 전달
		writeStream.writeUTF(clientNick);
		jta.setText("대화방에 \""+clientNick+"\"으로 입장하셨습니다.\n");
		// 서버의 대화명을 저장
		serverNick = readStream.readUTF();
	}// connectToServer

	/**
	 * 서버에서 보내오는 메세지를 무한루프로 읽어 들인다.
	 */
	@Override
	public void run() {
		String revMsg = "";
		if (readStream != null) {
			try {
				while (true) {
					revMsg = readStream.readUTF();
					// 대화창에 읽어 들인 메세지를 출력
					jta.append("[ server 메세지 ]" + revMsg + "\n");
					jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
				} // end while
			} catch (IOException ie) {
				JOptionPane.showMessageDialog(this, serverNick+"님께서 퇴실하였습니다.");
				ie.printStackTrace();
			} // end catch
		} // end if
	}// run

	/**
	 * 작성된 메세지를 서버로 보내는 일
	 */
	public void sendMsg() throws IOException {
		// 작성된 메세지를 가져와서
		String sendMsg = jtf.getText().trim();
		// 스트림에 기록하고
		writeStream.writeUTF(sendMsg);
		// 스트림의 내용을 목적지로 분출
		writeStream.flush();
		// 작성된 메세지를 채팅창에 출력한다.
		jta.append("[ "+clientNick+" ]" + sendMsg + "\n");
		// T.F의 내용을 삭제한다.
		jtf.setText("");
		// 스크롤바 갱신
		jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
	}// sendMsg

	public static void main(String[] args) {
//		new SimpleThreadChatClient();
		
		SimpleThreadChatClient stcc = new SimpleThreadChatClient();
		try {
			stcc.connectToServer();
			//메세지를 동시에 읽어야하기 때문에 Thread로 처리
			Thread t = new Thread(stcc);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// main

}// class
