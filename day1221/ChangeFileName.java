package day1221;

public class ChangeFileName {
	
	String s;
	
	{
		s = "c:/dev/temp/java.read.txt";
		System.out.println(s);
		StringBuilder sh = new StringBuilder(s);
		System.out.println(sh.insert(s.lastIndexOf("."), "_bak"));
	} // instance initialize block
	
	public ChangeFileName() {
		
	}//Test
	
	public static void main(String[] args) {
		new ChangeFileName();
	}//main

}//Test
