package day0107;

/**
 *	����� ���� ����ó�� Ŭ����
 *	Exception(RuntimeException)�� ��ӹ޴´�.
 * @author owner
 */
@SuppressWarnings("serial")
public class LoginException extends Exception{
	
	public LoginException() {
		this("�α��� ����");
	}//LoginException
	
	public LoginException(String msg) {
		super( msg ); // ����ó�� ��ü�� ����Ͽ� ���ܸ޼����� ����� �� �ִ�.
	}//LoginException
	
} // class
