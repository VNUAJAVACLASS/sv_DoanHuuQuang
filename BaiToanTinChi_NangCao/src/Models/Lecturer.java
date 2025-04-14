package Models;

import java.util.Scanner;

public class Lecturer extends Human {
	private String password;
	
	public Lecturer () {
		
	}

	public Lecturer(String password) {
		super();
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void enterInfor (Scanner sc) {
		System.out.print("Mat khau: ");
		this.password = sc.nextLine();
	}

	@Override
	public String toString() {
		return super.toString() + " - " + password;
	}
}
