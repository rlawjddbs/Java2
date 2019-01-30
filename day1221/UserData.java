package day1221;

import java.io.Serializable;

/**
 *	데이터를 가지고 있는 클래스로 직렬화 대상 클래스
 * @author owner
 */
public class UserData implements Serializable{
	private static final long serialVersionUID = 1433112021624942957L;
	
	private int age;
	private /*transient*/ double weight;
	private /*transient*/ String name;
	
	//transient : 직렬화 방지 키워드(값이 JVM외부로 전달 되지 않는다.)
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

	// 객체를 출력하면 주소가 아닌 값이 나오게 toString() 메소드를 오버라이딩 한다.
	@Override
	public String toString() {
		return "UserData [age=" + age + ", weight=" + weight + ", name=" + name + "]";
	}
	
}//class
