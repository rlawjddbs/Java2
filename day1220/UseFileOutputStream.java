package day1220;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * JVM에서 발생한 데이터가 정수, byte[]인 경우에 File로 저장하는<br>
 * FileOutputStream 사용,
 * 
 * @author owner
 */
public class UseFileOutputStream {

	public void useFileOutputStream() throws IOException {
		int i = 20;
		// 1. File class 생성
		File file = new File("c:/dev/temp/java_write.txt");

		FileOutputStream fos = null;

		try {
			
			boolean flag = false;
			
			if (file.exists()) {
				boolean[] temp = { false, true, true };
				int select = JOptionPane.showConfirmDialog(null, "덮어쓰시겠습니까?");
					flag = temp[select];
			} // end if
			
			// 2. FileOutputStream 생성
			// 파일이 없다면 생성하고, 파일이 있다면 덮어쓴다.
			if (!flag) {
				fos = new FileOutputStream(file);
				fos.write(i); //값을 스트림에 기록. (수에 대한 문자가 출력)
				fos.flush();
				System.out.println("파일기록 완료!!!");
			} // end if
			
		} finally {
//			fos.close(); //스트림에 기록(write)된 내용이 있다면, 분출하고 연결을 끊는다.
			if (fos != null) {fos.close();} // end if
		} // end finally

	}// useFileOutputStream

	public static void main(String[] args) {
		UseFileOutputStream ufos = new UseFileOutputStream();
		try {
			ufos.useFileOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch
	}// main

}// class
