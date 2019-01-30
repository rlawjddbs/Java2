package day0102;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UseURLEncoder {

	public static void main(String[] args) {
		String msg = "abc가나다def123"; //MS949
		try {
			// 입력된 문자열을 웹에서 사용하는 형태의 코드값으로 변경
			String encode = URLEncoder.encode(msg, "UTF-8"); 
			// MS949 -> 웹에서 사용하는 UTF-8 형식의 코드값으로 변경
			System.out.println(msg + "를 인코딩 하면 : "+encode);
			
			String decode = URLDecoder.decode(encode, "UTF-8");
			System.out.println(encode + "를 디코딩 하면 : "+decode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}//end catch
	}//main

}//class
