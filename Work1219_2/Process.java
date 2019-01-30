package Work1219_2;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Process extends JFrame{
	
	private String path;					// ����ڰ� ��θ� �Է��� InputDialog �� ���� StringŸ�� ���� path ����
	private File file;							// ����ڰ� InputDialog�� �Է��� ���� ��η� ������ FileŸ�� ���� file ����
	ArrayList<File> javaFileList;			// ���ϸ��� .java �� ������ File���� ��Ƴ��� ArrayList javaFileList ����
	JTextArea jta;
	
	public Process() {
		path = JOptionPane.showInputDialog(null, "", "��θ� �Է��ϼ���.", JOptionPane.DEFAULT_OPTION);
		// ����ڰ� �Է��� ��θ� InputDialog�� ���� path������ �Ҵ��Ѵ�. 
		file = new File(path);
		// �������� file�� ���Ӱ� ������ File��ü�� �ּҸ� ����Ű�µ�, �� ��ü�� ���� path�� ����� ���ڿ��� ��ΰ����� ���´�.  
		javaFileList = new ArrayList<File>();
		// �ڹ����� ������ ������ ArrayList �ʱ�ȭ
		
		if(file.isDirectory()) {
			
			jta = new JTextArea(20, 30);

			for(int i=0; i < file.listFiles().length; i++) {
				if(file.listFiles()[i].getName().endsWith(".java")) {
					javaFileList.add(file.listFiles()[i]);
					jta.append(file.listFiles()[i].getName()+"\n");
				}//end if
			}//end for
			
			
			jta.append("\n"+path+"��ο� ");
			jta.append("\njava ������ ");
			jta.append(String.valueOf( javaFileList.size() )); // javaFileList(ArrayList)�� ũ�� ǥ��
			jta.append("�� �����մϴ�. �����Ͻðڽ��ϱ�?\n");
			JScrollPane jsp = new JScrollPane(jta);
			
			int selectConfirm;
			selectConfirm = JOptionPane.showConfirmDialog(null, jsp);
			switch(selectConfirm) {
			case JOptionPane.OK_OPTION:
				for(int i=0; i < javaFileList.size(); i++) {
					javaFileList.get(i).delete();
				}//end for
				break;
			case JOptionPane.NO_OPTION:
				break;
			case JOptionPane.CANCEL_OPTION:
				break;
			default : return;
			}//switch
			
		} else {
			notPath();
		}//end else
		
	}//VO

	public void inputFileInTheArr() {
		JTextArea jta = new JTextArea(20, 30);

		for(int i=0; i < file.listFiles().length; i++) {
			if(file.listFiles()[i].getName().endsWith(".java")) {
				javaFileList.add(file.listFiles()[i]);
				jta.append(file.listFiles()[i].getName()+"\n");
			}//end if
		}//end for
		
	}//inputFileInTheArr
	
	public void showConfirm() {
		
	}//showConfirm
	
	public void notPath() {
		JOptionPane.showMessageDialog(null, path +"�� ��ΰ� �ƴմϴ�.", "Error", JOptionPane.ERROR_MESSAGE);
	}//notPath
	
	
	public void deleteJava() {
		
	}//deleteJava
	
}//class
