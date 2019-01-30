package day1219;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *	사용자가 입력한 키보드의 값을 처리하는 System.in
 * @author owner
 */
public class UseKeyboardInput {
	
	public UseKeyboardInput() {
		
		System.out.println("아무키나 누르고 엔터");
		
		//8bit stream과 16bit stream을 연결
		
		BufferedReader br = new BufferedReader(new InputStreamReader( System.in ));
		try {
			
//			//입력값 중 최초 입력 문자 하나 받기.
//			int input = System.in.read(); //read()를 block method라 함.
//			System.out.println("입력값 : "+input); /*가장먼저 입력된 key의 code가 출력됨 (OS => 입력된 키보드의 키코드를 읽고 자신이 가지고있는 캐릭터셋 테이블에서 해당 키 코드에 대응하는 문자를 출력해주는 원리)*/
//			
//			//입력값 중 입력된 모든 문자열 전체 받기 : 한글은 받을 수 없다.
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
			
			//16비트 stream을 사용하여 입력데이터를 줄단위로 읽어 들인다.
			String str = br.readLine();
			
			System.out.println( str );
			
			// 스트림 사용이 끝났으면 스트림의 연결을 끊는다.
			br.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		

		
		
	}//UseKeyboardInput
	
	
	public static void main(String[] args) {
		new UseKeyboardInput();
	}//main

}//class
