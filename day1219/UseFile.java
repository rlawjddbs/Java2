package day1219;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *	File클래스<br>
 *	- 파일의 정보 얻기<br>
 *	- 디렉토리 생성<br>
 *	- 파일 삭제
 * @author owner
 */
public class UseFile {

	public UseFile() {
		String path = "c:/dev/temp/java_read.txt";
		//1. 생성
		File file = new File(path);
		System.out.println( file ); //toString method를 오버라이딩하여 file 객체의 주소가 아닌 객체의 값이 출력된다.
//		System.out.println(file.exists()); // 파일이 존재하는지 확인 (존재하면 true, 없으면 false)
		
		if(file.exists()) { //파일이 존재할 때
			System.out.println("절대 경로 : " + file.getAbsolutePath());
			try {
				System.out.println("규범 경로 : " + file.getCanonicalPath()); //유일하게 접근할 수 있는 경로를 반환(대문자로만 시작된다.)
			} catch (IOException e) {
				e.printStackTrace();
			}//end catch
			System.out.println("경로 : "+file.getPath());
			System.out.println("폴더명 : "+file.getParent()); //c:/dev/temp			 =>		 부모 경로라고도 함
			System.out.println("파일명 : "+file.getName()); // java_read.txt
			
			System.out.println(file.isFile()?"파일":"디렉토리"); //파일이면 파일, 단순히 디렉토리정보면 디렉토리가 출력되는 삼항연산자
			System.out.println(file.isDirectory()?"디렉토리":"파일"); //디렉토리면 디렉토리, 파일이면 파일이 출력되는 삼항연산자
			
			System.out.println("파일 길이 : "+file.length()+"byte"); 
			//탐색기에서 1kb 로 나오지만 실제 크기는 168byte
			// 이러한 파일포맷을 NTFS file systems 라고 한다. (1kb가 안되는 크기를 가진 파일도 1kb로 나오게함.)
			
			System.out.println("실행 : " + (file.canExecute()?"가능":"불가능"));
			System.out.println("읽기 : " + (file.canRead()?"가능":"불가능"));
			
			System.out.println("쓰기 : " + (file.canWrite()?"가능":"불가능")); 
			//파일속성의 "읽기 전용"이 체크되어있을 경우 false 반환
			//해당 메모장 파일은 편집은 가능하나 저장시 다른이름으로 저장되며 원본 파일에 덮어씌우기도 안 됨. (과거의 윈도우는 편집도 안됐음)
			
			System.out.println(file.isHidden()?"숨김파일":"일반파일");
			//파일속성의 "숨김"이 체크되어있을 경우 false반환
			
			Date d = new Date(file.lastModified());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd a HH:mm:ss");
			System.out.println("마지막 수정일 : "+sdf.format(d));
			
			
		} else {
			System.out.println("경로를 확인해 주세요.");
		}//end else
		
	}//UseFile
	
	
	public static void main(String[] args) {
		new UseFile();
	}//main

}//class
