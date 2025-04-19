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
		String dbURL = "jdbc:ucanaccess://lib/QLSV.accdb";
		connection = DriverManager.getConnection(dbURL);
	}

	// ========================================================================================================================
	public List<User> getAllUsers() throws SQLException {
		List<User> userList = new ArrayList<>();
		String queryString = "SELECT * FROM tbl_users";
		User user = null;

		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(queryString);

		while (result.next()) {
			user = new User();

			user.setUserCode(result.getInt("user_code"));
			user.setFullname(result.getString("fullname"));
			user.setAddress(result.getString("address"));
			user.setClassName(result.getString("class"));
			user.setPassword(result.getString("password"));
			user.setUserType(result.getBoolean("user_type"));

			userList.add(user);
		}

		return userList;
	}

	// ========================================================================================================================
	public User getUserById(int userId) throws SQLException {
		User user = null;
		String queryString = "SELECT * FROM tbl_users WHERE user_code = ?";

		PreparedStatement statement = connection.prepareStatement(queryString);
		statement.setInt(1, userId);
		ResultSet result = statement.executeQuery();

		if (result.next()) {
			user = new User();

			user.setUserCode(result.getInt("user_code"));
			user.setFullname(result.getString("fullname"));
			user.setAddress(result.getString("address"));
			user.setClassName(result.getString("class"));
			user.setPassword(result.getString("password"));
			user.setUserType(result.getBoolean("user_type"));
		}

		return user;
	}

	// ========================================================================================================================
	public boolean addUser(User user) throws SQLException {
		String queryString = "INSERT" + " INTO tbl_users (fullname, address, class, password, user_type)"
				+ " VALUES (? ,? , ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(queryString);
		statement.setString(1, user.getFullname());
		statement.setString(2, user.getAddress());
		statement.setString(3, user.getClassName());
		statement.setString(4, user.getPassword());
		statement.setBoolean(5, user.isUserType());
		int rowInserted = statement.executeUpdate();

		return rowInserted > 0;
	}

	// ========================================================================================================================
	public boolean updateUser(User user) throws SQLException {
		String queryString = "UPDATE tbl_users "
				+ "SET fullname = ?, address = ?, class = ?, password = ?, user_type = ? "
				+ "WHERE user_code = ?";

		PreparedStatement statement = connection.prepareStatement(queryString);
		statement.setString(1, user.getFullname());
		statement.setString(2, user.getAddress());
		statement.setString(3, user.getClassName());
		statement.setString(4, user.getPassword());
		statement.setBoolean(5, user.isUserType());
		statement.setInt(6, user.getUserCode());

		int rowsUpdated = statement.executeUpdate();
		return rowsUpdated > 0;
	}

	// ========================================================================================================================
	public boolean deleteUser(int userCode) throws SQLException {
		String queryString = "DELETE FROM tbl_users WHERE user_code = ?";

		PreparedStatement statement = connection.prepareStatement(queryString);
		statement.setInt(1, userCode);

		int rowsDeleted = statement.executeUpdate();
		return rowsDeleted > 0;
	}
}
