package work0107.account_table.vo;

public class AccountTableVO {

	// ���̺� ��ϸ� ��ȸ�� ��� ���
	private String tableName;
	
	// �÷���, ��������, ũ��, ��������� ��ȸ�� ��� ���
	private String columnName, dataTypeName, constraint;
	private int dataTypeSize;
	
	// ���̺� ��ϸ� ��ȸ�� ��� ����ϴ� ������
	public AccountTableVO(String tableName) {
		this.tableName = tableName;
	}//AccountTableVO
	
	// �÷���, ��������, ũ��, ��������� ��ȸ�� ��� ����ϴ� ������
	public AccountTableVO(String columnName, String dataTypeName, String constraint, int dataTypeSize) {
		this.columnName = columnName;
		this.dataTypeName = dataTypeName;
		this.constraint = constraint;
		this.dataTypeSize = dataTypeSize;
	}//AccountTable
	
	
	public String getTableName() {
		return tableName;
	}

	
	
}
