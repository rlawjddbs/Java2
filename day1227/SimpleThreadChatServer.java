package day1227;

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

/**
 * Thread�� �����Ͽ� �޼����� �д� �ڵ带 ȭ��, �޼����� ������ �ڵ��<br>
 * ���ÿ� ó���Ѵ�.
 * 
 * @author owner
 */
@SuppressWarnings("serial")
public class SimpleThreadChatServer extends JFrame implements ActionListener, Runnable {

	private JTextArea jta;
	private JTextField jtf;
	private JScrollPane jsp;

	private ServerSocket server;
	private Socket client;
	private DataInputStream readStream;//������ �����͸� �б����� ��Ʈ��
	private DataOutputStream writeStream;//������ �����͸� ������ ���� ��Ʈ��
	
	private String serverNick; //������ ��ȭ��
	private String clientNick; //Ŭ���̾�Ʈ�� ��ȭ��

	
	
	public SimpleThreadChatServer() {
		super(":::::::::ä�ü���::::::::");

		jta = new JTextArea();
		jta.setBorder(new TitledBorder("��ȭ����"));
		jta.setEditable(false);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);

		jsp = new JScrollPane(jta);

		jtf = new JTextField();
		jtf.setBorder(new TitledBorder("��ȭ�Է�"));

		add("Center", jsp);
		add("South", jtf);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.out.println("����!!!!!!!!!!");
				// dispose : ȭ���� ����� �� -> windowClosed�� ȣ���ϰ� �ȴ�.
				dispose(); // dipose()�� �����츸 ���̴� method �̹Ƿ� ���α׷� �����Ŀ���
				// ����ؼ� SimpleChatServer�� ServerSocket�� �������� ���°� �ȴ�.
				// �����⸸ ���� ����
//				System.exit(0); //�����ִ� ��簴ü�� ���̴� ���
			}// windowClosing

			@Override
			public void windowClosed(WindowEvent we) {
//				System.out.println("��ȥ~~~~");
				try {
					close(); // Ŭ���̾�Ʈ�� �����ϰ� �ִ� ��Ʈ��, ����, ���������� ���� �Ѵ�.
				} catch (IOException e) {
					e.printStackTrace();
				}//end catch
			}// windowClosed
		});

		setBounds(100, 100, 300, 400);
		setVisible(true);
		jtf.requestFocus(); // Ŀ���� jtf�� ��ġ��Ų��.

//		try {
//			openServer();
//			//has a : �޼����� �д� �ڵ带 Thread�� ó���ϸ� ��� �ֵ� ������ �ϰԵȴ�.
//			Thread t = new Thread(this);
//			t.start();
//		} catch (SocketException se) {
//			System.err.println("�����ڰ� ������ �� ���� " + se.getMessage());
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(this, "�������� ����!! �޼����� �о���� �� �����ϴ�.");
//			e.printStackTrace();
//		} // end catch

		// readMsg() method�� ���� ���� �̹Ƿ� �� �Ʒ��� �̺�Ʈ�� ����Ϸ� �� ���
		// ���۵��� �ʴ´�. ���� addWindowListener()�� readMsg() method ������ ��ġ�� ���
		// ���� ó�� ���� ���� �ʰ� �ȴ�.
		jtf.addActionListener(this); // Ȯ�� �� �ּ�ó��

	}// SimpleChatServer

	public void close() throws IOException {
		try {
			if (readStream != null) {
				readStream.close();
			} // end if
			if (writeStream != null) {
				writeStream.close();
			} // end if
			if (client != null) {
				client.close();
			} // end if
		} finally {
			if (server != null) {
				server.close();
			} // end if
		} // end finally
	}// close

	@Override
	public void actionPerformed(ActionEvent ae) {
		try {
			if (writeStream != null) {
				sendMsg();
			} else {
				JOptionPane.showMessageDialog(this, "��ȭ ��밡 �����ϴ�.");
				jtf.setText("");
			} // end else
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}// actionPerformed

	/**
	 * 1. ServerSocket�� �����ϰ�(PORT����) ������ ������ ������(accept();) ��ȭ�� �ְ� ���� �� �ֵ��� 3.
	 * Stream�� ����(DIS, DOS)
	 */
	public void openServer() throws IOException, SocketException {
		serverNick = JOptionPane.showInputDialog(this, "��ȭ�� �Է�");
		// 1.
		server = new ServerSocket(65535);
		jta.setText("���� ���� ��... �����ڸ� ��ٸ��ϴ�.\n");
		// 3.
		client = server.accept();
		jta.append("client ����\n");
		// 4. ��Ʈ�� ���� (���� ���� �ǵ� �������. Stream�� ����Ǹ� �ǹǷ�)
		readStream = new DataInputStream(client.getInputStream());
		writeStream = new DataOutputStream(client.getOutputStream());
		//�������� �г����� �޴´�.
		clientNick = readStream.readUTF();
		//�� ���� �����ش�.
		writeStream.writeUTF(serverNick);
//		writeStream.write;

	}// openServer

	/**
	 * �����ڿ��� �޼����� ������ ��.
	 * 
	 * @throws IOException
	 */
	public void sendMsg() throws IOException {
		// T.F�� ���� �����ͼ�
		String msg = jtf.getText().trim();
		// ��Ʈ���� ����ϰ�
		writeStream.writeUTF(msg);
		// ��Ʈ���� ������ �������� ����
		writeStream.flush();
		// ���� �� ������ �� ȭ�鿡 ����ϰ�
		jta.append("[ "+serverNick+" ] " + msg + "\n");
		// �Է��� ���ϵ��� jtf�� �ʱ�ȭ
		jtf.setText("");
		//��ũ�ѹ� ����
		jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());

	}// sendMsg

	/**
	 * �����ڰ� �������� �޼����� ��� �о�鿩�� �Ѵ�.
	 * 
	 * @throws IOException
	 */
	@Override
	public void run() {
		String msg = "";
		if (readStream != null) {
			try {
				while (true) {
					// �޼����� �о�鿩
					msg = readStream.readUTF();
					// ��ȭâ�� ���
					jta.append("[ "+clientNick+" ] " + msg + "\n");
					// �޼����� T.A�� �߰��Ǹ� JScrollPane�� ��ũ�ѹ��� ����
					// J.S.P�� �ְ����� �������ش�.
					jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
				} // end while
			} catch (IOException ie) {
				JOptionPane.showMessageDialog(this, clientNick+"�Բ��� ����Ͽ����ϴ�.");
				ie.printStackTrace();
			} // end catch
		} // end if

	}// revMsg

	public static void main(String[] args) {
//		new SimpleThreadChatServer();
		SimpleThreadChatServer stcs = new SimpleThreadChatServer();
		try {
			stcs.openServer();
			//Thread�� stcs ��ü�� has a ����� ����
			Thread t = new Thread(stcs);
			// �޼����� �д� �ڵ带 Thread�� ó��
			t.start();
			
			
		} catch (SocketException se) {
			System.err.println("�����ڰ� ������ �� ���� " + se.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(stcs, "�������� ����!! �޼����� �о���� �� �����ϴ�.");
			e.printStackTrace();
		}//end catch
	}//main

}//class
