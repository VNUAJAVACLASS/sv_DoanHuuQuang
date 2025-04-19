package application;

public class User {
	private int userCode;
	private String fullname;
	private String address;
	private String className;
	private String password;
	private boolean userType; // true: Sinh vien - false: Giang vien
	
	public User() {
		super();
	}

	public User(String fullname, String address, String className, String password, boolean userType) {
		super();
		this.fullname = fullname;
		this.address = address;
		this.className = className;
		this.password = password;
		this.userType = userType;
	}

	public User(int userCode, String fullname, String address, String className, String password, boolean userType) {
		super();
		this.userCode = userCode;
		this.fullname = fullname;
		this.address = address;
		this.className = className;
		this.password = password;
		this.userType = userType;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isUserType() {
		return userType;
	}

	public void setUserType(boolean userType) {
		this.userType = userType;
	}
}
