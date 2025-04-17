package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	private Connection connection;

	public UserDAO() throws SQLException {
		String dbURL = "jdbc:ucanaccess://lib/QLKH.accdb";
		connection = DriverManager.getConnection(dbURL);
	}

	// ========================================================================================================================
	public List<User> getAllUsers() throws SQLException {
		List<User> userList = new ArrayList<>();
		String queryString = "SELECT * FROM tblUser";
		User user = null;

		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(queryString);

		while (result.next()) {
			user = new User();

			user.setUserID(result.getInt("UserID"));
			user.setEmail(result.getString("Email"));
			user.setFullName(result.getString("Fullname"));
			user.setGender(result.getBoolean("Gender"));
			user.setBirthday(result.getDate("Birthday").toLocalDate());
			user.setCourse(result.getString("Course"));
			user.setPassword(result.getString("Password"));

			userList.add(user);
		}

		return userList;
	}

	// ========================================================================================================================
	public User getUserById(int userId) throws SQLException {
		User user = null;
		String queryString = "SELECT * FROM tblUser WHRER UserID = ?";

		PreparedStatement statement = connection.prepareStatement(queryString);
		statement.setInt(1, userId);
		ResultSet result = statement.executeQuery();

		if (result.next()) {
			user = new User();

			user.setUserID(result.getInt("UserID"));
			user.setEmail(result.getString("Email"));
			user.setFullName(result.getString("Fullname"));
			user.setGender(result.getBoolean("Gender"));
			user.setBirthday(result.getDate("Birthday").toLocalDate());
			user.setCourse(result.getString("Course"));
			user.setPassword(result.getString("Password"));
		}

		return user;
	}

	// ========================================================================================================================
	public User getUserByEmail(String email) throws SQLException {
		User user = null;
		String queryString = "SELECT * FROM tblUser WHERE Email = ?";

		PreparedStatement statement = connection.prepareStatement(queryString);
		statement.setString(1, email);
		ResultSet result = statement.executeQuery();

		if (result.next()) {
			user = new User();

			user.setUserID(result.getInt("UserID"));
			user.setEmail(result.getString("Email"));
			user.setFullName(result.getString("Fullname"));
			user.setGender(result.getBoolean("Gender"));
			user.setBirthday(result.getDate("Birthday").toLocalDate());
			user.setCourse(result.getString("Course"));
			user.setPassword(result.getString("Password"));
		}

		return user;
	}

	// ========================================================================================================================
	public boolean addUser(User user) throws SQLException {
		String queryString = "INSERT" + " INTO tblUser (Email, Fullname, Gender, Birthday, Course, Password)"
				+ " VALUES (? ,? , ?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(queryString);
		statement.setString(1, user.getEmail());
		statement.setString(2, user.getFullName());
		statement.setBoolean(3, user.isGender());
		statement.setDate(4, Date.valueOf(user.getBirthday()));
		statement.setString(5, user.getCourse());
		statement.setString(6, user.getPassword());
		int rowInserted = statement.executeUpdate();

		return rowInserted > 0;
	}

	// ========================================================================================================================
	public boolean updateUser(User user) throws SQLException {
		String queryString = "UPDATE tblUser "
				+ "SET Email = ?, Fullname = ?, Gender = ?, Birthday = ?, Course = ?, Password = ? "
				+ "WHERE UserID = ?";

		PreparedStatement statement = connection.prepareStatement(queryString);
		statement.setString(1, user.getEmail());
		statement.setString(2, user.getFullName());
		statement.setBoolean(3, user.isGender());
		statement.setDate(4, Date.valueOf(user.getBirthday()));
		statement.setString(5, user.getCourse());
		statement.setString(6, user.getPassword());
		statement.setInt(7, user.getUserID());

		int rowsUpdated = statement.executeUpdate();
		return rowsUpdated > 0;
	}

	// ========================================================================================================================
	public boolean deleteUser(int userId) throws SQLException {
		String queryString = "DELETE FROM tblUser WHERE UserID = ?";

		PreparedStatement statement = connection.prepareStatement(queryString);
		statement.setInt(1, userId);

		int rowsDeleted = statement.executeUpdate();
		return rowsDeleted > 0;
	}
}
