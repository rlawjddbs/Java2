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
 * �̺�Ʈ ó���� ���� ���� �̺�Ʈ ���� Ŭ������ ���� ����
 * @author owner
 */
public class MultiChatServerEvt extends WindowAdapter implements ActionListener, Runnable {

	private MultiChatServerView mcsv;
	private Thread threadServer; // �����ڿ� ���� ó���� �ϱ� ���� Thread
	private ServerSocket server; // PORT ����
	private List<MultiChatServerHelper> listClient; // ��� �����ڸ� ������ List

	public MultiChatServerEvt(MultiChatServerView mcsv) {
		this.mcsv = mcsv;
		listClient = new ArrayList<MultiChatServerHelper>(); 
		
		// List�� �˻��� ����� ������ �ߺ������͸� ����Ѵ�. 
		// set�� �ߺ������͸� ������� �ʰ� �˻��� ����� ����.
		// map�� Ű�� ���� ���̴�.
		
	}// MultiChatServerEvt

	@Override
	public void run() { // start()�� ���ؼ� run()�� �ҷ�����.
		// ���������� ���� ������ ������ �޴´�.
		try {
			server = new ServerSocket(35000);
			DefaultListModel<String> dlmTemp = mcsv.getDlmChatList();
			mcsv.getDlmChatList().addElement("������ 35000 PORT�� ���� ���� ��...");
			// �Ѱ� ���� MCSV �ν��Ͻ��� JList �� 
			// ������ ���ȴٴ� ������ �߰��Ͽ� �����ڿ��� �˷��ش�. 

			Socket someClient = null; // ������ ������ ������ ��ü
			InetAddress ia = null; // �������� IP(Internet Protocol) address

			// ������ ������ �޾� ��Ʈ���� �����ϰ�,
			// ��ȭ�� �аų� ��� �����ڿ��� �����ϴ� ��.
			MultiChatServerHelper mcsh = null; 

			
			for (int cnt = 0; ; cnt++) { //���ǽ��� ���������Ƿ� ���� ����
				
//				String nick = mccv.getJtfNick().getText().trim();
				someClient = server.accept(); // ������ �õ��ϴ� �湮�ڿ��� ������ �����ش�.
				
				ia = someClient.getInetAddress(); // �湮���� IP �ּҸ� ���������� ��´�.
				
//				dlmTemp.addElement(/*"["+nick+*/"] ["+ ia + "] ������ ����"); // obj ��ü�� Vector�� �����Ѵ�.

				// ����, ������ ȭ��, ���Ӽ����� �Ҵ��Ͽ� Helper class�� �����Ѵ�.
				mcsh = new MultiChatServerHelper(someClient, dlmTemp, cnt, mcsv, listClient, mcsv.getJspList());
				
				// ������ ���� �̸��� ���ϰ�ü�� ������ �����ϱ����� List�� �߰�
				listClient.add(mcsh);

//				//Helper���� ����Ʈ�� ����� �� �ֵ��� �Ҵ�
//				mcsh.setConnectList( listClient ); 

				// �������� �޼����� �о������� Thread ����
				mcsh.start();

			} // end for

		} catch (IOException e) {
			e.printStackTrace();
		} // 0 ~ 65535���� PORT�� ���� �� �ִ�.

	}// run

	@Override
	public void windowClosing(WindowEvent e) { // MCSV���� ������ �̺�Ʈ �߻��� ���� ��
		// ������ �ݱ⸦ ������ ���� �� �����
		mcsv.dispose(); // MCSV ��ü�� ���δ�.
	}// windowClosing

	@Override
	public void windowClosed(WindowEvent e) { // MCSV���� ������ �̺�Ʈ �߻��� ���� ��
		// �����찡 ���� �� ���� �� �����
		try {
				// �����찡 ���� �� ������ �����ִٸ� ������ �ݴ´�.
				if (server != null) {	server.close();} //end if
			} catch (IOException e1) {
				e1.printStackTrace();
			}//end catch
	}// windowClosed

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == mcsv.getJbtOpenServer()) { // ���� ��ư�� ������ ��� �߻��ϴ� �̺�Ʈ ó��
			
			if (threadServer == null) { // �����尡 ����ִٸ�
				threadServer = new Thread(this); // �� Ŭ������ �ν��Ͻ��� ����Ű�� Ű����(this) �Է�
				threadServer.start(); // start() -> run() �ڵ� ����
				
				/* Open ��ư�� Ŭ���Ǹ� Thread Ÿ���� �������� threadServer��
				 * ����Ű�� ����� �ִ��� Ȯ���ϸ� ����Ű�� ����� ���� ��� 
				 * ���ο� Thread �ν��Ͻ��� �����Ͽ� ���������� �Ҵ��Ѵ�. */
				
			} else {
				JOptionPane.showMessageDialog(mcsv, "ä�ü����� �̹� �������Դϴ�.");
				
				/* �̹� threadServer�� �����ϰ� �ִ� ���(Thread �ν��Ͻ�)�� �ִٸ� 
				 * �̹� ä�ü����� �������̶�� �޼����� ����. */
				
			} // end else
			
		} // end if
		if (ae.getSource() == mcsv.getJbtCloseServer()) { // �ݱ� ��ư�� ������ ��� �߻��ϴ� �̺�Ʈ ó��
			switch (JOptionPane.showConfirmDialog(mcsv, "ä�ü����� �����Ͻðڽ��ϱ�?\n�����Ͻø� ��� �������� ������ �������ϴ�.")) {
			case JOptionPane.OK_OPTION:
				mcsv.dispose();
			}// end switch
		} // end if

	}// actionPerformed

}// MultiChatServerEvt