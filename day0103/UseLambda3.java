package day0103;

public class UseLambda3 {

	public static void main(String[] args) {

		Thread t = new Thread(()->{
			for(int i = 0; i<1000; i++) {
				System.out.println(i);
			}//end for
		}/*Lambda Thread*/); 
		t.start();
		for(int i = 0; i<1000; i++) {
			System.out.println(i);
		}//end for
		
		
		// ��Ƽ ������� Ȱ�� ������ ��
		// �̰ɷ� ������ ������ ���� �� ������?
	}//main

}//class
