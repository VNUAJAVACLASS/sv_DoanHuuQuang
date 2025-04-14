package Models;

import java.util.Scanner;

public abstract class Human {
	protected String fullname;
	protected String address;
	protected String code;
	
	public Human() {

	}

	public Human(String fullname) {
		this.fullname = fullname;
	}

	public Human(String fullname, String address) {
		this.fullname = fullname;
		this.address = address;
	}

	public Human(String fullname, String address, String code) {
		this.fullname = fullname;
		this.address = address;
		this.code = code;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public abstract void enterInfor (Scanner sc);
}
