package day1219;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *	����ڰ� �Է��� Ű������ ���� ó���ϴ� System.in
 * @author owner
 */
public class UseKeyboardInput {
	
	public UseKeyboardInput() {
		
		System.out.println("�ƹ�Ű�� ������ ����");
		
		//8bit stream�� 16bit stream�� ����
		
		BufferedReader br = new BufferedReader(new InputStreamReader( System.in ));
		try {
			
//			//�Է°� �� ���� �Է� ���� �ϳ� �ޱ�.
//			int input = System.in.read(); //read()�� block method�� ��.
//			System.out.println("�Է°� : "+input); /*������� �Էµ� key�� code�� ��µ� (OS => �Էµ� Ű������ Ű�ڵ带 �а� �ڽ��� �������ִ� ĳ���ͼ� ���̺��� �ش� Ű �ڵ忡 �����ϴ� ���ڸ� ������ִ� ����)*/
//			
//			//�Է°� �� �Էµ� ��� ���ڿ� ��ü �ޱ� : �ѱ��� ���� �� ����.
//			int input=0;
//			while(true) {
//				input = System.in.read();
//				System.out.println( /*(char)*/input );
//				
//				if(input==13) {
//					break;
//				}//end if
//				
//			}//end while
			
			//16��Ʈ stream�� ����Ͽ� �Էµ����͸� �ٴ����� �о� ���δ�.
			String str = br.readLine();
			
			System.out.println( str );
			
			// ��Ʈ�� ����� �������� ��Ʈ���� ������ ���´�.
			br.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		

		
		
	}//UseKeyboardInput
	
	
	public static void main(String[] args) {
		new UseKeyboardInput();
	}//main

}//class
