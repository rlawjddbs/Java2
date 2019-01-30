package day1226;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class SimpleChatServer extends JFrame implements ActionListener {

	private JTextArea jta;
	private JTextField jtf;

	private ServerSocket server;
	private Socket client;
	private DataInputStream readStream;
	private DataOutputStream writeStream;

	public SimpleChatServer() {
		super(":::::::::채팅서버::::::::");

		jta = new JTextArea();
		jta.setBorder(new TitledBorder("대화내용"));
		jta.setEditable(false);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);

		JScrollPane jsp = new JScrollPane(jta);

		jtf = new JTextField();
		jtf.setBorder(new TitledBorder("대화입력"));

		add("Center", jsp);
		add("South", jtf);

		

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.out.println("종료!!!!!!!!!!");
				//dispose : 화면이 종료된 후 -> windowClosed를 호출하게 된다.
				dispose(); // dipose()는 윈도우만 죽이는 method 이므로 프로그램 종료후에도
				// 계속해서 SimpleChatServer의 ServerSocket은 가동중인 상태가 된다.
				// 껍데기만 죽은 상태
//				System.exit(0); //관련있는 모든객체를 죽이는 방법
			}// windowClosing

			@Override
			public void windowClosed(WindowEvent we) {
//				System.out.println("영혼~~~~");
				try {
					close(); // 클라이언트와 연결하고 있는 스트림, 소켓, 서버소켓을 종료 한다.
				} catch (IOException e) {
					e.printStackTrace();
				}
			}// windowClosed
		});

		setBounds(100, 100, 300, 400);
		setVisible(true);
		jtf.requestFocus(); // 커서를 jtf에 위치시킨다.

		jtf.addActionListener(this);
//		확인 후 주석 처리
//		try {
//			openServer();
//			revMsg();
//		} catch (SocketException se) {
//			System.err.println("접속자가 들어오기 전 종료 "+se.getMessage());
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(this, "서버가동 실패!! 메세지를 읽어들일 수 없습니다.");
//			e.printStackTrace();
//		} //end catch
		
		// readMsg() method는 무한 루프 이므로 그 아래에 이벤트를 등록하려 할 경우
		// 동작되지 않는다. 만약 addWindowListener()가 readMsg() method 하위에 위치할 경우
		// 종료 처리 또한 되지 않게 된다.
//		jtf.addActionListener(this); // 확인 후 주석처리
	}// SimpleChatServer

	public void close() throws IOException {
		try {
			if (readStream != null) {readStream.close();} // end if
			if (writeStream != null) {writeStream.close();} // end if
			if (client != null) {client.close();
			} // end if
		} finally {
			if (server != null) {	server.close();} // end if
		} // end finally
	}// close

	@Override
	public void actionPerformed(ActionEvent ae) {
		try {
			if (writeStream != null) {
				sendMsg();
			} else {
				JOptionPane.showMessageDialog(this, "대화 상대가 없습니다.");
				jtf.setText("");
			} // end else
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}// actionPerformed

	/**
	 * 1. ServerSocket을 생성하고(PORT열림) 접속자 소켓이 들어오면(accept();) 대화를 주고 받을 수 있도록 3.
	 * Stream을 연결(DIS, DOS)
	 */
	public void openServer() throws IOException, SocketException {
		// 1.
		server = new ServerSocket(65531);
		jta.setText("서버 가동 중... 접속자를 기다립니다.\n");
		// 3.
		client = server.accept();
		jta.append("client 접속\n");
		// 4. 스트림 연결 (누가 먼저 되든 상관없다. Stream만 연결되면 되므로)
		readStream = new DataInputStream(client.getInputStream());
		writeStream = new DataOutputStream(client.getOutputStream());

	}// openServer

	/**
	 * 접속자에게 메세지를 보내는 일.
	 * 
	 * @throws IOException
	 */
	public void sendMsg() throws IOException {
		// T.F의 값을 가져와서
		String msg = jtf.getText().trim();
		// 스트림에 기록하고
		writeStream.writeUTF(msg);
		// 스트림의 내용을 목적지로 분출
		writeStream.flush();
		// 내가 쓴 내용을 내 화면에 출력하고
		jta.append("[ server 메세지 ] " + msg + "\n");
		// 입력이 편하도록 jtf를 초기화
		jtf.setText("");

	}// sendMsg

	/**
	 * 접속자가 보내오는 메세지를 계속 읽어들여야 한다.
	 * 
	 * @throws IOException
	 */
	public void revMsg() throws IOException {
		String msg = "";
		while (true) {
			// 메세지를 읽어들여
			msg = readStream.readUTF();
			// 대화창에 출력
			jta.append("[ client 메세지 ] " + msg + "\n");
		} // end while
	}// revMsg

	public static void main(String[] args) {
//		new SimpleChatServer();
		SimpleChatServer scs = new SimpleChatServer();
		try {
			scs.openServer();
			scs.revMsg();
		} catch (SocketException se) {
			System.err.println("접속자가 들어오기 전 종료 "+se.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(scs, "서버가동 실패!! 메세지를 읽어들일 수 없습니다.");
			e.printStackTrace();
		} //end catch
		
	}// main

}// class
