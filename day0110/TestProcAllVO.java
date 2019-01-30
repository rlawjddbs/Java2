package day0110;

public class TestProcAllVO {
	private int empno, sal;
	private String ename, hiredate, job;

	public TestProcAllVO() {
		
	}//TestProcAllVO
	
	public TestProcAllVO(int empno, int sal, String ename, String hiredate, String job) {
		this.empno = empno;
		this.sal = sal;
		this.ename = ename;
		this.hiredate = hiredate;
		this.job = job;
	}//TestProcAllVO

	public int getEmpno() {
		return empno;
	}//getEmpno

	public int getSal() {
		return sal;
	}//getSal

	public String getEname() {
		return ename;
	}//getEname

	public String getHiredate() {
		return hiredate;
	}//getHiredate

	public String getJob() {
		return job;
	}//getJob

	@Override
	public String toString() {
		return "TestProcAllVO [empno=" + empno + ", sal=" + sal + ", ename=" + ename + ", hiredate=" + hiredate
				+ ", job=" + job + "]";
	}//toString
	
	
}//class