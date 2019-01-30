package kr.co.sist.chat.client.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import kr.co.sist.chat.client.view.MultiChatClientView;

public class MultiChatClientEvt extends WindowAdapter implements ActionListener, Runnable {

	private MultiChatClientView mccv;
	private Socket client;
	private DataInputStream readStream;
	private DataOutputStream writeStream;
	private Thread threadMsg;
	private String nick;

	public MultiChatClientEvt(MultiChatClientView mccv) {
		this.mccv = mccv;
	}// MultiChatClientView

	@Override
	public void actionPerformed(ActionEvent ae) {
		// ���ӹ�ư Ŭ���� �߻��ϴ� �̺�Ʈ ó��
		if (ae.getSource() == mccv.getJbtConnect()) {
			try {
				connectToServer();
			} catch (UnknownHostException uhe) {
				JOptionPane.showMessageDialog(mccv, "������ �� �� �����ϴ�.");
				uhe.printStackTrace();
			} catch (IOException ie) {
				JOptionPane.showMessageDialog(mccv, "���� ����.");
				ie.printStackTrace();
			}
		} // end if
		
		// �ݱ��ư Ŭ���� �߻��ϴ� �̺�Ʈ ó��
		if (ae.getSource() == mccv.getJbtClose()) {
			mccv.dispose();
		} // end if
		
		// ��������ư Ŭ���� �߻��ϴ� �̺�Ʈ ó��
		if (ae.getSource() == mccv.getJbtCapture()) {
			try {
				if (!mccv.getJtaTalkDisplay().getText().equals("")) {
					capture();
				}else {
					JOptionPane.showMessageDialog(mccv, "��ȭ������ �����ϴ�.");
				}//end else
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mccv, "���Ϸ� �����ϴ� �� ������ �߻��Ͽ����ϴ�.");
				e.printStackTrace();
			}
		} // end if

		// �ؽ�Ʈ�ʵ忡�� ����ڰ� ���� �Է� �� ���͸� ġ�� �߻��ϴ� �̺�Ʈ ó��
		if (ae.getSource() == mccv.getJtfTalk()) {
			try {
				sendMsg();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mccv, "������ ����Ǿ� �޼����� ������ �� �����ϴ�.");
				e.printStackTrace();
			} // end catch
		} // end if

		//
		
	}// actionPerformed
	
	
	public void connectToServer() throws UnknownHostException, IOException {
		if (client == null) {
			// ��ȭ�� ������ ������
			nick = mccv.getJtfNick().getText().trim();
			if( nick.equals("") ) {
				JOptionPane.showMessageDialog(mccv, "��ȭ���� �Է��� �ּ���.");
				mccv.getJtfNick().requestFocus();
				return;
			}//end if
			
			String serverIp = mccv.getJtfServerIp().getText().trim();
			client = new Socket(serverIp, 30000); // �Է��� ip address�� ��ǻ�Ϳ� ����.
			// ��Ʈ��
			readStream = new DataInputStream(client.getInputStream());
			writeStream = new DataOutputStream(client.getOutputStream());

			//��ȭ���� ������ ������.
			writeStream.writeUTF( nick );
			writeStream.flush();
			
			
			
			mccv.getJtaTalkDisplay().setText(mccv.getJtfNick().getText().trim()+"���� ������ �����Ͽ����ϴ�.\n��ſ� ä�õǼ���~\n");
			// �޼��� �б�
			threadMsg = new Thread(this);

			threadMsg.start();

		} else {
			JOptionPane.showMessageDialog(mccv, client.getInetAddress().getHostAddress() + "�̹� ������ ���� �� �Դϴ�.");
		} // end else
	}// connectToServer
	
	
	@Override
	public void run() {
		if (readStream != null) {
			try {
				String revMsg = "";

				while (true) { // �������� �������� �޼����� �о� �鿩
					revMsg = readStream.readUTF();

					JScrollPane jsp = mccv.getJspTalkDisplay();

					// ä��â�� �Ѹ���.
					mccv.getJtaTalkDisplay().append(revMsg + "\n");

					jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
				} // end while
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mccv, "������ ����Ǿ����ϴ�.");
				e.printStackTrace();
			} // end catch
		} // end if
	}// run

	@Override
	public void windowClosing(WindowEvent e) {
		mccv.dispose();
	}// windowClosing

	@Override
	public void windowClosed(WindowEvent e) {
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
		} catch (IOException ie) {
			ie.printStackTrace();
		} finally {
			System.exit(0);
		} // end finally
	}// windowClosed

	

	/**
	 * ������ �޼����� ������ ��
	 */
	public void sendMsg() throws IOException {
		if (writeStream != null) {
			JTextField jtf = mccv.getJtfTalk();
			// JTF�� �Է��� �޼����� �о� ���δ�.
			String msg = jtf.getText().trim();
//			String nick = mccv.getJtfNick().getText().trim();
			if (!msg.isEmpty()) {
				// ��Ʈ���� ����
				writeStream.writeUTF("[ " + nick + " ] " + msg);
				// �������� ����
				writeStream.flush();
			} // end if
			jtf.setText("");
		} // end if
	}// sendMsg

	public void capture() throws IOException {
		switch (JOptionPane.showConfirmDialog(mccv, "��ȭ ������ �����Ͻðڽ��ϱ�?")) {
		case JOptionPane.OK_OPTION:
			// ��Ʈ���� ����Ͽ� ����
			File saveDir = new File("c:/dev/chat");
			saveDir.mkdirs();
			File saveFile = new File(saveDir.getAbsolutePath() + "/java_chat[" + System.currentTimeMillis() + "].dat");

			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(saveFile));
				bw.write(mccv.getJtaTalkDisplay().getText()); // ��Ʈ���� ��ȭ���� ���
				bw.flush(); // ��Ʈ���� ��ϵ� ������ ����
				JOptionPane.showMessageDialog(mccv, saveDir + "�� ��ȭ ������ ��ϵǾ����ϴ�.");
			} finally {
				if (bw != null) {
					bw.close();
				} // end if
			} // end finally
		}// end switch
	} // capture

	

}// class
