package day0103;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UseLambda {

	public static void main(String[] args) {
//		TestLambda tl = ()-> new Date().toString(); //return 생략 가능
//		TestLambda tl = ()-> { return new Date().toString(); }; //람다식의 끝은 세미콜론 ; 으로 마침표 찍어주기. return 기술시 중괄호 { } 필요.
		//abstract method 내의 코드가 복잡(여러줄)한 경우에는 { }를 반드시 기술한다.
		TestLambda tl = ()->{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(int i = 0; i < 10; i++) {
				System.out.println("i = "+ i);
			}//end for
			return sdf.format(new Date());
		};
		
		System.out.println(tl.toDay());
	}//main

}//class
