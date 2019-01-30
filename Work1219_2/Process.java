package Work1219_2;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Process extends JFrame{
	
	private String path;					// 사용자가 경로를 입력할 InputDialog 를 담을 String타입 변수 path 선언
	private File file;							// 사용자가 InputDialog에 입력한 값을 경로로 설정할 File타입 변수 file 선언
	ArrayList<File> javaFileList;			// 파일명이 .java 로 끝나는 File형만 담아놓는 ArrayList javaFileList 선언
	JTextArea jta;
	
	public Process() {
		path = JOptionPane.showInputDialog(null, "", "경로를 입력하세요.", JOptionPane.DEFAULT_OPTION);
		// 사용자가 입력한 경로를 InputDialog를 통해 path변수에 할당한다. 
		file = new File(path);
		// 참조변수 file은 새롭게 생성된 File객체의 주소를 가리키는데, 그 객체는 변수 path에 저장된 문자열을 경로값으로 갖는다.  
		javaFileList = new ArrayList<File>();
		// 자바파일 정보만 저장할 ArrayList 초기화
		
		if(file.isDirectory()) {
			
			jta = new JTextArea(20, 30);

			for(int i=0; i < file.listFiles().length; i++) {
				if(file.listFiles()[i].getName().endsWith(".java")) {
					javaFileList.add(file.listFiles()[i]);
					jta.append(file.listFiles()[i].getName()+"\n");
				}//end if
			}//end for
			
			
			jta.append("\n"+path+"경로에 ");
			jta.append("\njava 파일이 ");
			jta.append(String.valueOf( javaFileList.size() )); // javaFileList(ArrayList)의 크기 표시
			jta.append("개 존재합니다. 삭제하시겠습니까?\n");
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
		JOptionPane.showMessageDialog(null, path +"는 경로가 아닙니다.", "Error", JOptionPane.ERROR_MESSAGE);
	}//notPath
	
	
	public void deleteJava() {
		
	}//deleteJava
	
}//class
