package application;

public final class UserSession {
	private static UserSession instance;
	
	private String email;
	private String fullname;
	
	public UserSession(String email, String fullname) {
		super();
		this.email = email;
		this.fullname = fullname;
	}
	
	public static void createInstance(String email, String fullname) {
		if(instance == null) {
			instance = new UserSession(email, fullname);
		}
	}
	
	public static UserSession getInstance() {
		if(instance == null) {
			return null;
		}
		
		return instance;
	}

	public String getEmail() {
		return email;
	}

	public String getFullname() {
		return fullname;
	}
	
	public void cleanUserSession() {
		email = null;
		fullname = null;
		instance = null;
	}
}
