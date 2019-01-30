package day1226;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class SimpleChatClient extends JFrame implements ActionListener {

	private JTextArea jta;
	private JTextField jtf;

	private Socket client; // ������ �����ϱ� ����
	private DataInputStream readStream; // ������ �����͸� �б����� ��Ʈ��
	private DataOutputStream writeStream; // ������ �����͸� ���������� ��Ʈ��

	public SimpleChatClient() {
		super(":::::::::::::::::::::::::::::::::::::::::::::::::::ä�ü���:::::::::::::::::::::::::::::::::::::::::::::::::::");

		jta = new JTextArea();
		jta.setBorder(new TitledBorder("��ȭ����"));
		jta.setEditable(false);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);

		JScrollPane jsp = new JScrollPane(jta);

		jtf = new JTextField();
		jtf.setBorder(new TitledBorder("��ȭ�Է�"));

		add("Center", jsp);
		add("South", jtf);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}// windowClosing
			
			@Override
			public void windowClosed(WindowEvent we) {
//				System.out.println("��ȥ~~~~");
				try {
					close(); // Ŭ���̾�Ʈ�� �����ϰ� �ִ� ��Ʈ��, ����, ���������� ���� �Ѵ�.
				} catch (IOException e) {
					e.printStackTrace();
				}
			}// windowClosed
		});

		setBounds(100, 100, 300, 400);
		setVisible(true);
		jtf.requestFocus(); // Ŀ���� jtf�� ��ġ��Ų��.
		
		jtf.addActionListener(this);
		
//		Ȯ�� �� �ּ� ó��
//		try {
//			connectToServer();
//			readMsg();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} //end catch
		
		// readMsg() method�� ���� ���� �̹Ƿ� �� �Ʒ��� �̺�Ʈ�� ����Ϸ� �� ���
		// ���۵��� �ʴ´�. ���� addWindowListener()�� readMsg() method ������ ��ġ�� ���
		// ���� ó�� ���� ���� �ʰ� �ȴ�.
//		jtf.addActionListener(this); // Ȯ�� �� �ּ�ó��
		
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
			if (readStream != null) {	readStream.close(); }//end if
			if (writeStream != null) {	writeStream.close(); }//end if
		}finally {
			if (client != null) {	client.close(); }//end if
		}//end finally
	}// close

	/**
	 * 2. ������ �����Ͽ� ������ �����ϰ� ��ȭ�� �аų� ������ ����<br>
	 * 4. ��Ʈ���� �����Ѵ�.
	 * @throws IOException
	 */
	public void connectToServer() throws IOException{
		//2.
		client = new Socket("211.63.89.153", 65531);
		//4. ��Ʈ�� ����
		readStream = new DataInputStream(client.getInputStream());
		writeStream = new DataOutputStream(client.getOutputStream());
		
	}//connectToServer
	
	/**
	 * �������� �������� �޼����� ���ѷ����� �о� ���δ�.
	 */
	public void readMsg() throws IOException{
		String revMsg = "";
		while( true ) {
			revMsg = readStream.readUTF();
			//��ȭâ�� �о� ���� �޼����� ���
			jta.append("[ server �޼��� ]"+revMsg+"\n");
		}//end while
	}//readMsg
	
	/**
	 * �ۼ��� �޼����� ������ ������ ��
	 */
	public void sendMsg() throws IOException{
		//�ۼ��� �޼����� �����ͼ�
		String sendMsg = jtf.getText().trim();
		//��Ʈ���� ����ϰ�
		writeStream.writeUTF( sendMsg );
		// ��Ʈ���� ������ �������� ����
		writeStream.flush();
		//�ۼ��� �޼����� ä��â�� ����Ѵ�.
		jta.append("[ client �޼��� ]"+sendMsg+"\n");
		// T.F�� ������ �����Ѵ�.
		jtf.setText("");
	}//sendMsg
	
	
	public static void main(String[] args) {
//		new SimpleChatClient();
		SimpleChatClient scc = new SimpleChatClient();
		try {
			scc.connectToServer();
			scc.readMsg();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// main

}// class
