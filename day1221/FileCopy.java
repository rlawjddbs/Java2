package day1221;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.FileDataSource;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class FileCopy extends JFrame implements ActionListener, Runnable {
	private JButton jb;
	private JProgressBar jpb;
	private long fileLen;
	private int cnt;
	File file;

	public FileCopy() {
		super("���Ϻ���");
		jb = new JButton("���ϼ���");
		jpb = new JProgressBar(0, 100);
		jpb.setString("��ô��");
//		jpb.setValue(50);

		JPanel jp = new JPanel();
		jp.add(jb);

		add("Center", jp);
		add("South", jpb);

		jb.addActionListener(this);
		setBounds(100, 100, 500, 200);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}// FileCopy

	@Override
	public void run() {
		StringBuilder copyFileName = new StringBuilder(file.getAbsolutePath());
		copyFileName.insert(copyFileName.lastIndexOf("."), "_bak");
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {

			// �������Ͽ� ��Ʈ�� ����
			fis = new FileInputStream(file);
			fos = new FileOutputStream(copyFileName.toString());// _back�� �� �̸�
			// ���ϰ� ����� ��Ʈ������ ���� ��´�.
			int temp = 0;
			fileLen = file.length();
			cnt = 0;
			int i = 0;
			while ((temp = fis.read()) != -1) {
				fos.write(temp);
				fos.flush();
				jpb.setValue((int) ((i / (double) fileLen) * 100));
				if (jpb.getValue() == 100) {
					break;
				}
				// �о� ���γ����� _bak�� ���� ������ �����Ͽ� ��� (����)
//				System.out.print( (char)temp );
				i++;
			} // end while
			JOptionPane.showMessageDialog(this, file + "���� ����");
		} catch (IOException ie) {
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} // end if
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} // end if
		} // end finally
	}// run

	@Override
	public void actionPerformed(ActionEvent e) {
		FileDialog fd = new FileDialog(this, "���ϼ���", FileDialog.LOAD);
		fd.setVisible(true);

		String path = fd.getDirectory();
		String name = fd.getFile();
		if (path != null) {
			file = new File(path + name);
			try {
				copy(file);
			} catch (FileNotFoundException fnfe) {
				JOptionPane.showMessageDialog(this, "������ �������� �ʽ��ϴ�.");
				fnfe.printStackTrace();
			} catch (IOException ie) {
				JOptionPane.showMessageDialog(this, "����� �۾��� ���� �߻�.");
				ie.printStackTrace();
			} // end catch
		} // end if

	}// actionPerformed

	public void copy(File file) throws FileNotFoundException, IOException {

		int selectValue = JOptionPane.showConfirmDialog(this, "������ �����Ͻðڽ��ϱ�?");

		switch (selectValue) {
		case JOptionPane.OK_OPTION:

//			System.out.println( copyFileName );

			Thread t = new Thread(this);
			t.start();
//				 HDD�� �о���̴� ũ�⸦ �����ϰ� 1byte�� �о�鿩 ���

			// HDD�� �ѹ��� �о���̴� ũ�⸦ �״�� ���
//				byte[] temp=new byte[512];
//				int len=0;
//				while(( len=fis.read(temp)) != -1) {
//					fos.write(temp, 0, len);
//					fos.flush();					
//				}//end while

		}// end switch

	}// copy

	public static void main(String[] args) {
		new FileCopy();
	}// main

}// class
