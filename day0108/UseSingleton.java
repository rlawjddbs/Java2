package day0108;

public class UseSingleton {

	public static void main(String[] args) {
		//Singleton pattern으로 만들어진 객체 사용
//		Singleton s = new Singleton(); // 생성자의 접근 제어자가 private 이므로 클래스 외부에서 객체화가 되지 않는다. (The constructor Singleton() is not visible.)
		
		Singleton s = Singleton.getInstance();
		Singleton s1 = Singleton.getInstance();
		Singleton s2 = Singleton.getInstance();
		Singleton s3 = Singleton.getInstance();
		
		System.out.println(s);
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		
	}//main

}//class
