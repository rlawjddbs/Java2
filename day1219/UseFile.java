package day1219;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *	FileŬ����<br>
 *	- ������ ���� ���<br>
 *	- ���丮 ����<br>
 *	- ���� ����
 * @author owner
 */
public class UseFile {

	public UseFile() {
		String path = "c:/dev/temp/java_read.txt";
		//1. ����
		File file = new File(path);
		System.out.println( file ); //toString method�� �������̵��Ͽ� file ��ü�� �ּҰ� �ƴ� ��ü�� ���� ��µȴ�.
//		System.out.println(file.exists()); // ������ �����ϴ��� Ȯ�� (�����ϸ� true, ������ false)
		
		if(file.exists()) { //������ ������ ��
			System.out.println("���� ��� : " + file.getAbsolutePath());
			try {
				System.out.println("�Թ� ��� : " + file.getCanonicalPath()); //�����ϰ� ������ �� �ִ� ��θ� ��ȯ(�빮�ڷθ� ���۵ȴ�.)
			} catch (IOException e) {
				e.printStackTrace();
			}//end catch
			System.out.println("��� : "+file.getPath());
			System.out.println("������ : "+file.getParent()); //c:/dev/temp			 =>		 �θ� ��ζ�� ��
			System.out.println("���ϸ� : "+file.getName()); // java_read.txt
			
			System.out.println(file.isFile()?"����":"���丮"); //�����̸� ����, �ܼ��� ���丮������ ���丮�� ��µǴ� ���׿�����
			System.out.println(file.isDirectory()?"���丮":"����"); //���丮�� ���丮, �����̸� ������ ��µǴ� ���׿�����
			
			System.out.println("���� ���� : "+file.length()+"byte"); 
			//Ž���⿡�� 1kb �� �������� ���� ũ��� 168byte
			// �̷��� ���������� NTFS file systems ��� �Ѵ�. (1kb�� �ȵǴ� ũ�⸦ ���� ���ϵ� 1kb�� ��������.)
			
			System.out.println("���� : " + (file.canExecute()?"����":"�Ұ���"));
			System.out.println("�б� : " + (file.canRead()?"����":"�Ұ���"));
			
			System.out.println("���� : " + (file.canWrite()?"����":"�Ұ���")); 
			//���ϼӼ��� "�б� ����"�� üũ�Ǿ����� ��� false ��ȯ
			//�ش� �޸��� ������ ������ �����ϳ� ����� �ٸ��̸����� ����Ǹ� ���� ���Ͽ� �����⵵ �� ��. (������ ������� ������ �ȵ���)
			
			System.out.println(file.isHidden()?"��������":"�Ϲ�����");
			//���ϼӼ��� "����"�� üũ�Ǿ����� ��� false��ȯ
			
			Date d = new Date(file.lastModified());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd a HH:mm:ss");
			System.out.println("������ ������ : "+sdf.format(d));
			
			
		} else {
			System.out.println("��θ� Ȯ���� �ּ���.");
		}//end else
		
	}//UseFile
	
	
	public static void main(String[] args) {
		new UseFile();
	}//main

}//class
