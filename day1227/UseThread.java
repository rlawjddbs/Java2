package day1227;

/**
 *	Thread class를 상속받아서 Thread 사용.
 * @author owner
 */
//1. Thread 상속
public class UseThread extends Thread{
	//2. run method Override
	@Override
	public void run(){
		//3. Thread로 동작되어야할 코드.(동시에 실행되어야 할 코드)
		for(int i = 0; i < 1000; i++) {
			System.out.println("run---------------------------------> "+i);
		}//end for
	}//run
	
	public void test() {
		for(int i = 0; i < 1000; i++) {
			System.out.println("main========================= i = "+i);
		}//end for
	}//test
	
	public static void main(String[] args) {
		//4. 객체 생성
		UseThread ut = new UseThread();
		//5. 부모 클래스인 Thread가 가지고 있는 start()호출
		ut.start(); // start() 호출하면 start() 안에서 -> run()호출
		ut.test();
		
		
	}//main

}//class
