package day0110;

public class TestProcOneVO {

	private int sal;
	private String ename, job, hiredate;
	
	public TestProcOneVO() {
	}//TestProcOneVO

	public TestProcOneVO(int sal, String ename, String job, String hiredate) {
		this.sal = sal;
		this.ename = ename;
		this.job = job;
		this.hiredate = hiredate;
	}//TestProcOneVO

	public int getSal() {
		return sal;
	}//getSal

	public String getEname() {
		return ename;
	}//getEname

	public String getJob() {
		return job;
	}//getJob

	public String getHiredate() {
		return hiredate;
	}//getHiredate

	@Override
	public String toString() {
		return "TestProcOneVO [sal=" + sal + ", ename=" + ename + ", job=" + job + ", hiredate=" + hiredate + "]";
	}//toString
	
	
	
}//class
