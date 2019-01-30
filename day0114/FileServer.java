package day0114;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

/**
 * 접속자가 보내오는 파일을 저장하는 서버
 * 
 * @author owner
 */
@SuppressWarnings("serial")
public class FileServer extends JFrame implements Runnable, ActionListener {
	private JButton btnStartServer;
	private DefaultListModel<String> dlmFileList;
	private Thread threadServer;
	private ServerSocket socketServerFile;

	public FileServer() {
		super("파일 서버");
		
		btnStartServer = new JButton("서버가동");
		
		dlmFileList = new DefaultListModel<String>();
		
		JList<String> listFile = new JList<String>(dlmFileList);

		JScrollPane jsp = new JScrollPane(listFile);
		jsp.setBorder(new TitledBorder("파일목록"));

		JPanel panel = new JPanel();
		panel.add(btnStartServer);

		add("Center", jsp);
		add("South", panel);

		btnStartServer.addActionListener(this);

		setBounds(100, 100, 400, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}// FileServer

	@Override
	public void run() {
		try {
		Socket socketClient = null;
		DataInputStream dis = null;
		
		File file = null;
		FileOutputStream fos = null;

		StringBuilder fileName = new StringBuilder();
		int dataCnt = 0; 
		int dataLen = 0;

		byte[] readData = null;
		
		try {
			while (true) {
				// 접속자 소켓이 존재함.
				socketClient = socketServerFile.accept();

				System.out.println("클라이언트가 이미지 업로드!");
				
				// 2. 클라이언트가 전송하는 파일명 저장
				dis = new DataInputStream(socketClient.getInputStream());
				
				fileName.delete(0, fileName.length()); // 처음부터 끝까지 지움
				fileName.append(dis.readUTF());
				
				//파일명을 겹치지 않도록 변경
				fileName.insert(fileName.lastIndexOf("."), 
						"_".concat(String.valueOf(System.currentTimeMillis())));
				
//				dlmFileList.addElement( fileName.toString()+"/"+String.valueOf(dataCnt) );
				dlmFileList.addElement( fileName.toString()+" "+socketClient.getInetAddress());
				
				//4. 클라이언트가 전송하는 배열의 개수(읽어들일 횟수)를 받는다.
				dataCnt = dis.readInt(); 
				
//				dlmFileList.addElement(String.valueOf(dataCnt));
				
				//6. 전송 횟수만큼 읽어 들여 파일로 출력한다.
				file = new File("C:/dev/workspace/javase_prj2/src/day0114/images/"+fileName);

				fos = new FileOutputStream( file );
				
				readData = new byte[512];
				
				while(dataCnt > 0) {
					//클라이언트가 전송하는 배열의 크기만큼을 받아서
					dataLen = dis.read(readData);
					
					//파일에 기록
					fos.write(readData, 0, dataLen);
					fos.flush();
					
					dataCnt--;
				}//end while
			} // end while
			
			} finally {
				if (fos != null) {fos.close();}
				if (dis != null) {	dis.close();}
				if (socketClient != null) {	socketClient.close();}
			} // end finally
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}//end catch
		
	}// run


	@Override
	public void actionPerformed(ActionEvent ae) {
		if (threadServer == null) {
			try {
				socketServerFile = new ServerSocket(10000);
				dlmFileList.addElement("서버가 10000포트로 실행되었습니다.");
				threadServer = new Thread(this);
				threadServer.start();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "포트가 이미 사용 중 입니다.");
				e.printStackTrace();
			} // end catch

		} else {
			JOptionPane.showMessageDialog(null, "서버가 동작중입니다.");
		} // end else
	}// actionPerformed

	public static void main(String[] args) {
		new FileServer();
	}// main
}// class
