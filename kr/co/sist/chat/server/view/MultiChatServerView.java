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
 *	ä�ù� ������ ȭ��
 * @author owner
 */
@SuppressWarnings("serial")
public class MultiChatServerView extends JFrame{
	private JList<String> jlChatList;
	private JScrollPane jspList;
	private JButton jbtOpenServer, jbtCloseServer;
	private DefaultListModel<String> dlmChatList; //�������� ������ ���� list
	
	public MultiChatServerView() {
		super(":::::::::::::::::::::::: ä�ù� ������ ::::::::::::::::::::::::");
		
		dlmChatList = new DefaultListModel<String>(); // ����Ʈ�� �߰�, ������ ����� ����
		jlChatList = new JList<String>(dlmChatList); // DefaultListModel �� �����ϴ� ����� �ð�ȭ
		jspList = new JScrollPane(jlChatList); // �ð�ȭ�� ����Ʈ�� ��ũ�ѹٸ� �޾���
		
		jbtOpenServer = new JButton("��������");
		jbtCloseServer = new JButton("��������");
		
		JPanel btnPanel = new JPanel(); // ���� ����, ���� ��ư�� ���� �г� ����
		btnPanel.add(jbtOpenServer);
		btnPanel.add(jbtCloseServer);
		
		jspList.setBorder(new TitledBorder("������ ����")); // ��ũ�ѹٿ� ������ �޾Ƴ���
		
		add("Center", jspList); // JFrame �߾ӿ� JList Model �� ��� JScrollPane ����
		add("South", btnPanel); // JFrame �ϴܿ� ��ư �� ���� ��� �г� ����
		
		//�̺�Ʈ ���
		MultiChatServerEvt mcse =new MultiChatServerEvt(this);  // has a ����� �̺�Ʈ Ŭ���� ���� 
		jbtOpenServer.addActionListener( mcse ); // ���� ���� ��ư�� ������ �� �̺�Ʈ ���
		jbtCloseServer.addActionListener( mcse ); // ���� �ݱ� ��ư�� ������ �� �̺�Ʈ ���
		
		
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
