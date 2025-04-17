package application;

import java.time.LocalDate;

public class User {
	private int userID;
	private String email;
	private String fullName;
	private boolean gender;
	private LocalDate birthday;
	private String course;
	private String password;
	
	public User () {}
	
	public User(String email, String fullName, boolean gender, LocalDate birthday, String course, String password) {
		super();
		this.email = email;
		this.fullName = fullName;
		this.gender = gender;
		this.birthday = birthday;
		this.course = course;
		this.password = password;
	}

	public User(int userID, String email, String fullName, boolean gender, LocalDate birthday, String course,
			String password) {
		super();
		this.userID = userID;
		this.email = email;
		this.fullName = fullName;
		this.gender = gender;
		this.birthday = birthday;
		this.course = course;
		this.password = password;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
