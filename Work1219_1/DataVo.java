package Work1219_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DataVo extends JFrame{

	public DataVo() {
		System.out.print("디렉토리명 입력 : ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			
			String inputStr = br.readLine();
			if (inputStr.equals("c:/dev")) {
				br.close();
				
				if (dirCheck(inputStr)) {
					
					File file = new File(inputStr);
					File[] fileList = file.listFiles();
					JTextArea jta = new JTextArea(40, 80);
					jta.append("이름\t\t\t유형\t크기\t마지막으로 수정한 날짜\n");
					JScrollPane jsp = new JScrollPane(jta);
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					
					for (int i = 0; i < fileList.length; i++) {
						jta.append(fileList[i].getName()
								+"\t\t\t"
								+(fileList[i].isDirectory()?"폴더":"파일")
								+"\t"
								+fileList[i].length()
								+"byte\t"
								+sdf.format(fileList[i].lastModified())
								+"\n");
					} // end for
					
					JOptionPane.showMessageDialog(null, jsp);
				} else {
					System.err.println("파일은 취급하지 않습니다.");
				} // end else
				
				
			} else {
				System.out.println("해당안됨");
			}//end else
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch

	}// Datavo

	public boolean dirCheck(String inputStr) {
		File file = new File(inputStr);
		return file.isDirectory() ? true : false;
	}// dirCheck

	public void printData(String inputStr) {
		
	}//printData
	
	
}// class
