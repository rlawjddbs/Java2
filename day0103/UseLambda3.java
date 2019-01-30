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
		
		
		// 멀티 스레드로 활용 가능할 듯
		// 이걸로 서버를 여러개 돌릴 수 있을까?
	}//main

}//class
