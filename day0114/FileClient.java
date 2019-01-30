package day0114;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

/**
 * ������ ������ ������ �����ϴ� ���� Ŭ���̾�Ʈ
 * 
 * @author owner
 */
@SuppressWarnings("serial")
public class FileClient extends JFrame implements ActionListener {
	private DefaultListModel<String> dlmFileList;
	private JButton btnSelectFile;

	public FileClient() {
		super("��������");
		
		btnSelectFile = new JButton("���ϼ���");

		dlmFileList = new DefaultListModel<String>();
		
		JList<String> listFile = new JList<String>(dlmFileList);
		
		JScrollPane jspFileList = new JScrollPane(listFile);
		jspFileList.setBorder(new TitledBorder("������ ����"));

		JPanel panel = new JPanel();
		panel.add(btnSelectFile);

		add("Center", jspFileList);
		add("South", panel);

		btnSelectFile.addActionListener(this);

		setBounds(100, 100, 400, 300);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}// FileClient

	@Override
	public void actionPerformed(ActionEvent ae) {
		selectFile();
	}// actionPerforemd

	public static void main(String[] args) {
		new FileClient();
	}// main
	
	private void selectFile() {
		FileDialog fdOpen = new FileDialog(this, "���� ����", FileDialog.LOAD);
		fdOpen.setVisible(true);
		
		String fPath = fdOpen.getDirectory();
		String fName = fdOpen.getFile();

		// �̹��� ����(jpg, jpeg, gif, png, bmp)�� ����
		// ������ ������ ���� ������ �ƴ� ��� ���â
//		if(name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png") || name.endsWith(".bmp")) {
//			JOptionPane.showMessageDialog(this, "good");
//		}else {
//			JOptionPane.showMessageDialog(this, "�̹����� jpg, jpeg, gif, png, bmp ���ĸ� �����˴ϴ�.");
//		}//end else
		
		if (fName != null) {
			String ext = fName.substring(fName.lastIndexOf(".") + 1).toLowerCase(); // �ҹ��ڷ� ġȯ
			String[] arrCompare = { "jpg", "jpeg", "gif", "png", "bmp" };

			boolean extFlag = false;

			for (String tempExt : arrCompare) {
				if (tempExt.equals(ext)) {
					extFlag = true;
				} // end if
			} // end for

			if (!extFlag) {
				JOptionPane.showMessageDialog(this, fName + "�� ���� ������ ������ �ƴմϴ�.\n�̹����� ���� �����մϴ�.");
				return;
			} // end if

			// ���� ����!!!!
			try {
				sendFile(new File(fPath + "/" + fName));
				
				dlmFileList.addElement(fName);
				
				JOptionPane.showMessageDialog(this, fName + "������ �����Ͽ����ϴ�.");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} // end catch

		} // end if
	}// selectFile

	private void sendFile(File file) throws UnknownHostException, IOException {
		// ������ ����
		Socket socketClient = null;
		DataOutputStream dos = null;
		FileInputStream fis = null;
		
		try {
			String ip = JOptionPane.showInputDialog("���� ip : ");
			
			socketClient = new Socket("211.63.89."+ip, 10000);

			String fName = file.getName();
			
			// 1. ���ϸ��� ����
			dos = new DataOutputStream(socketClient.getOutputStream());
			dos.writeUTF(fName);
			dos.flush();
			
			//3. ������ ������ byte[] ���� ���� ����
			int dataCnt = 0;
			byte[] tempData = new byte[512];
			
			int dataLen = 0;
			
			fis = new FileInputStream(file);
			
			while((dataLen=fis.read(tempData)) != -1) {
				dataCnt++;
			}//end while
			
			System.out.println("������ �迭�� ���� : "+dataCnt);
			
			fis.close();
			
			//������ �о���� �迭�� Ƚ��(����)�� �����Ѵ�.
			dos.writeInt(dataCnt); 
			dos.flush();
			
			//5.���Ͽ��� �о���� ������ �����Ѵ�.
			dataLen = 0;
			
			// ������ ũ�⸦ ���� �� ������ ������ ������ �翬���Ѵ�.
			fis = new FileInputStream(file);  
			
			while( (dataLen = fis.read(tempData)) != -1 ) {
				dos.write(tempData,0,dataLen);
				dos.flush();
				
				dataCnt--;
			}//end while
			
			System.out.println("���� Ƚ�� "+dataCnt);
			
		} finally {
			if (fis != null) {fis.close();} // end if
			if (dos != null) {dos.close();} // end if
			if (socketClient != null) {socketClient.close();} // end if
		} // end finally
	}// sendFile

	
}// class
