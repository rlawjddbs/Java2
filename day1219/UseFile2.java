package day1219;

import java.io.File;

/**
 *	��������, ���� ����
 * @author owner
 */
public class UseFile2 {

	public void createDirectory() {
		File file = new File("c:/jeongyun/kim");
		if(file.mkdirs()) { // �Ȱ��� ��������µ� mkdir()�� ���ſ��� �ֻ���(C:\)��ο������� ���������� �Ұ������� ����� ������
			System.out.println("������������");
		}else {
			System.out.println("���� �̸��� ������ ����");
		}//end else
	}//createDirectory
	
	public void removeFile() {
		File file = new File("c:/dev/temp/a.txt");
		boolean flag = file.delete();
		if(flag) {
			System.out.println("���� ���� ����"); // ���������� ���°� �ƴ϶� ������ ������
		}else {
			System.out.println("���� ���� ����");
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
