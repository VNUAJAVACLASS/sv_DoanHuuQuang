import java.util.Scanner;

public class Human {
	private String fullname;
	private String address;
	private String code;

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
	
	public void enterInfor (Scanner sc) {
		System.out.print("Ho ten: ");
		this.fullname = sc.nextLine();
		System.out.print("Ma: ");
		this.code = sc.nextLine();
		System.out.print("Dia chi: ");
		this.address = sc.nextLine();
	}

	@Override
	public String toString() {
		return fullname + " - " + address + " - " + code;
	}
}
