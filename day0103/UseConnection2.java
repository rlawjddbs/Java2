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
		//	�򵥽ø� �ڷ������� ������ ������ 123.123�� ���ͷ� ������µ� ���� �ƴ϶�� �ȵ��µ� ���ɳֳ�..
		new UseConnection2();
	}//main
	
	public void setD(BigDecimal d) {
		bd = d;
	}//setD
	
	public double getD() {
		return d;
	}//getD
	
}//class


