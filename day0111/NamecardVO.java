package day0111;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.connection.GetConnection;

public class NamecardVO {

	private String name, addr, img;

	
	
	public NamecardVO() {
	} // NamecardVO

	public NamecardVO(String name, String addr, String img) {
		this.name = name;
		this.addr = addr;
		this.img = img;
	} // NamecardVO

	public String getName() {
		return name;
	} // getName

	public String getAddr() {
		return addr;
	} // getAddr

	public String getImg() {
		return img;
	} // getImg

	@Override
	public String toString() {
		return "NamecardVO [name=" + name + ", addr=" + addr + ", img=" + img + "]";
	} // toString
	
	
	
	
}//class
