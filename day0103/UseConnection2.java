package day0103;

import java.math.BigDecimal;

public class UseConnection2 {
	private double d;
	private BigDecimal bd;
	
	public UseConnection2() {
		 d = 123.123;
		setD(new BigDecimal(123.123));
		
		System.out.println(bd);
		System.out.println(bd.toString());
		
	}//UseConnection
	
	public static void main(String[] args) {
		//	빅데시멀 자료형으로 정의한 변수에 123.123을 세터로 넣을라는데 더블 아니라고 안들어가는데 어케넣노..
		new UseConnection2();
	}//main
	
	public void setD(BigDecimal d) {
		bd = d;
	}//setD
	
	public double getD() {
		return d;
	}//getD
	
}//class


