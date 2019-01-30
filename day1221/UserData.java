package day1221;

import java.io.Serializable;

/**
 *	�����͸� ������ �ִ� Ŭ������ ����ȭ ��� Ŭ����
 * @author owner
 */
public class UserData implements Serializable{
	private static final long serialVersionUID = 1433112021624942957L;
	
	private int age;
	private /*transient*/ double weight;
	private /*transient*/ String name;
	
	//transient : ����ȭ ���� Ű����(���� JVM�ܺη� ���� ���� �ʴ´�.)
	public UserData() {
		
	}//UserData

	public UserData(int age, double weight, String name) {
		this.age = age;
		this.weight = weight;
		this.name = name;
	}

	public int getAge() {
		return age;
	}//getAge

	public void setAge(int age) {
		this.age = age;
	}//setAge

	public double getWeight() {
		return weight;
	}//getWeight

	public void setWeight(double weight) {
		this.weight = weight;
	}//setWeight

	public String getName() {
		return name;
	}//getName

	public void setName(String name) {
		this.name = name;
	}//setName

	// ��ü�� ����ϸ� �ּҰ� �ƴ� ���� ������ toString() �޼ҵ带 �������̵� �Ѵ�.
	@Override
	public String toString() {
		return "UserData [age=" + age + ", weight=" + weight + ", name=" + name + "]";
	}
	
}//class
