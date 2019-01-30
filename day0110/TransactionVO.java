package day0110;

public class TransactionVO {
	private String subject,writer;

	public TransactionVO() {
		super();
	}//TransactionVO

	public TransactionVO(String subject, String writer) {
		this.subject = subject;
		this.writer = writer;
	}//TransactionVO

	public String getSubject() {
		return subject;
	}//getSubject

	public String getWriter() {
		return writer;
	}//getWriter()

	
	
}//class
