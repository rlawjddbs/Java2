package day1219;

import java.io.File;

/**
 *	폴더생성, 파일 삭제
 * @author owner
 */
public class UseFile2 {

	public void createDirectory() {
		File file = new File("c:/jeongyun/kim");
		if(file.mkdirs()) { // 똑같이 만들어지는데 mkdir()은 과거에는 최상위(C:\)경로에서부터 폴더생성이 불가했으나 현재는 가능함
			System.out.println("폴더생성성공");
		}else {
			System.out.println("같은 이름의 폴더가 존재");
		}//end else
	}//createDirectory
	
	public void removeFile() {
		File file = new File("c:/dev/temp/a.txt");
		boolean flag = file.delete();
		if(flag) {
			System.out.println("파일 삭제 성공"); // 휴지통으로 가는게 아니라 완전히 삭제됨
		}else {
			System.out.println("파일 삭제 실패");
		}//end else
	}//removeFile
	
	public UseFile2(){
		
	}//UseFile2()
	
	public static void main(String[] args) {
		UseFile2 uf2 = new UseFile2();
		uf2.createDirectory();
		System.out.println("----------------------------------------------------------");
		uf2.removeFile();
	}//main

}//class
