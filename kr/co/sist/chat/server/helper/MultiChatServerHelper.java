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
 * �������� �����ϸ� ������ ���Ͽ� ��Ʈ���� ���ͼ� �����ϴ� Ŭ����.<br>
 * �����ڰ� �����ϸ� ������ ���Ͽ� �� ��Ʈ������ �޼����� ���ѷ����� �о���̰� ��� �����ڿ��� �޼��� ���.
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
	 * ������ ������ �޾Ƽ� ��Ʈ���� �����ϰ� ��Ʈ���� ��� �޼����� �аų� ���� �� �ִ� ���·� �����.
	 * 
	 * @param socket	�����ڼ���
	 * @param dlm		������ ����â�� ����ϱ� ���� Model ��ü
	 * @param cnt		���� ����
	 * @param jf		View �� ������� MCSV Ŭ������ �ν��Ͻ�
	 * @param list		���� ��ü���� ���(������ ���)
	 * @param jsp		MCSV�� JScroll Pane
	 */
	public MultiChatServerHelper(Socket socket, DefaultListModel<String> dlm, int cnt, JFrame jf,
			List<MultiChatServerHelper> list, JScrollPane jsp) {
		someClient = socket;	// �Ű������� �ǳ׹��� ���� ��ü�� �ּҸ� Helper Ŭ������ ��� ������ ����
		this.dlm = dlm;			// �Ű������� �ǳ׹��� dlm �� ��ü�� �ּҸ� Ŭ������ ������� dlm�� ����
		this.cnt = cnt;			// �Ű������� �ǳ׹��� �������� ���Ӽ����� Ŭ������ ������� cnt�� ����
		this.jf = jf;				// �Ű������� �ǳ׹��� MCSV Ŭ������ �ν��Ͻ� �ּҸ� Ŭ������ ������� jf�� ���� 
		connectList = list;		// �Ű������� �ǳ׹��� ���ϰ�ü���� ����� Ŭ������ ������� connectList�� ����
		this.jsp = jsp;			// �Ű������� �ǳ׹��� MCSV�� �ν��Ͻ��� ���Ե� JScrollPane�� Ŭ������ ������� jsp�� ����
		
		// ��Ʈ�� ���
		try {
			readStream = new DataInputStream(someClient.getInputStream());
			writeStream = new DataOutputStream(someClient.getOutputStream());
			
			//�����ڰ� �������� nick�� �޴´�.
			nick = readStream.readUTF();
			
			//����â�� ���� �޼��� ���
			dlm.addElement("["+ someClient.getInetAddress()+" : "+ someClient.getLocalPort()+" / "+nick+"] ���� �����ϼ̽��ϴ�.");
			
			// ������ ������ �����ڿ��� �޼����� ���
			broadcast("[" + cnt + "] ��° �����ڰ� [ " + nick + " ]���� ä�ù濡 ���� �Ͽ����ϴ�.");
			jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
		} catch (IOException ie) {
			JOptionPane.showMessageDialog(jf, "������ ���� �� ���� �߻�....");
			ie.printStackTrace();
		} // end catch

	}// MultiChatServerHelper ���ڰ� �ִ� ������ Overloading

//	public void setConnectList(List<MultiChatServerHelper> list) {
//		connectList = list;
//	}// setConnectList

	@Override
	public void run() {

		if (readStream != null) {
			try {
				String revMsg = "";
				while (true) { // �������� �������� ��� �޼����� �о, ��� �����ڿ��� �Ѹ���.
					revMsg = readStream.readUTF();
					broadcast(revMsg);
				} // end while
			} catch (IOException ie) {
				// �����ڰ� ����ϸ� �ش� �����ڸ� ����Ʈ���� �����Ѵ�.
				connectList.remove( this ); // ���� ������ �ִ� ���� ������ ��.
				//�޼�����  �о������ ���ϴ� ���¶�� �����ڰ� ������ ������ ����
				dlm.addElement(cnt+" / "+nick+" ������ ���");
				broadcast("["+nick+"] ���� ����Ͽ����ϴ�.");
				
				jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
				
				ie.printStackTrace();
			} // end if

		} // end catch

	}// run

	/**
	 * ��� �����ڿ��� �޼����� �ѷ��ִ� ��. synchronized : MultiThread���� ����ȣ�� ���� (����ȭ ó��)
	 * 
	 * @param msg
	 */
	public synchronized void broadcast(String msg) {

		MultiChatServerHelper mcsh = null;
		try {
			for (int i = 0; i < connectList.size(); i++) {
				mcsh = connectList.get(i); // list���� Helper ��ü�� ���
				mcsh.writeStream.writeUTF(msg); // ��� ��Ʈ���� ���
				mcsh.writeStream.flush(); // �������� ����
			} // end for
		} catch (IOException e) {
			JOptionPane.showMessageDialog(jf, "[" + cnt + "] ��° �����ڿ��� �޼����� ���� �� �����ϴ�.");
			e.printStackTrace();
		} // end catch

	}// broadcast

}// class
