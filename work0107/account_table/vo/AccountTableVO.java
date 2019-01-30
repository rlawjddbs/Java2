package work0107.account_table.vo;

public class AccountTableVO {

	// 테이블 목록만 조회할 경우 사용
	private String tableName;
	
	// 컬럼명, 데이터형, 크기, 제약사항을 조회할 경우 사용
	private String columnName, dataTypeName, constraint;
	private int dataTypeSize;
	
	// 테이블 목록만 조회할 경우 사용하는 생성자
	public AccountTableVO(String tableName) {
		this.tableName = tableName;
	}//AccountTableVO
	
	// 컬럼명, 데이터형, 크기, 제약사항을 조회할 경우 사용하는 생성자
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
