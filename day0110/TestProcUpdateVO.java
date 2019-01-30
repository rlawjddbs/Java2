package day0110;

public class TestProcUpdateVO {

	private int empno, sal;
	private String job;
	
	
	
	public TestProcUpdateVO() {
	}//TestProcUpdateVO 기본생성자



	public TestProcUpdateVO(int empno, int sal, String job) {
		this.empno = empno;
		this.sal = sal;
		this.job = job;
	}//TestProcUpdateVO 인자가 있는 생성자



	public int getEmpno() {
		return empno;
	}//getEmpno



	public int getSal() {
		return sal;
	}//getSal



	public String getJob() {
		return job;
	}//getJob

	
}//class
