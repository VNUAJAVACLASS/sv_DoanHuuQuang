package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserSubjectDAO {
	private Connection connection;

	public UserSubjectDAO() throws SQLException {
		String dbURL = "jdbc:ucanaccess://lib/QLSV.accdb";
		connection = DriverManager.getConnection(dbURL);
	}

	// ========================================================================================================================
	public List<UserSubject> getAllUserSubject() throws SQLException {
		List<UserSubject> list = new ArrayList<>();
		String query = "SELECT * FROM tbl_user_subject";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			UserSubject us = new UserSubject(rs.getInt("user_subject_code"), rs.getInt("user_code"),
					rs.getInt("subject_code"), rs.getFloat("attendance_exam_mark"), rs.getFloat("exam_mark_1"),
					rs.getFloat("exam_mark_2"), rs.getFloat("exam_mark_3"), rs.getFloat("final_exam_mark"));
			list.add(us);
		}

		return list;
	}

	// ========================================================================================================================
	public boolean addUserSubject(UserSubject us) throws SQLException {
		String query = "INSERT INTO tbl_user_subject (user_code, subject_code, attendance_exam_mark, exam_mark_1, exam_mark_2, exam_mark_3, final_exam_mark)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, us.getUserCode());
		statement.setInt(2, us.getSubjectCode());
		statement.setFloat(3, us.getAttendanceExamMark());
		statement.setFloat(4, us.getExamMark1());
		statement.setFloat(5, us.getExamMark2());
		statement.setFloat(6, us.getExamMark3());
		statement.setFloat(7, us.getFinalExamMark());

		int rowsInserted = statement.executeUpdate();
		return rowsInserted > 0;
	}

	// ========================================================================================================================
	public UserSubject getUserSubjectById(int id) throws SQLException {
		UserSubject us = null;
		String query = "SELECT * FROM tbl_user_subject WHERE user_subject_code = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			us = new UserSubject(rs.getInt("user_subject_code"), rs.getInt("user_code"), rs.getInt("subject_code"),
					rs.getFloat("attendance_exam_mark"), rs.getFloat("exam_mark_1"), rs.getFloat("exam_mark_2"),
					rs.getFloat("exam_mark_3"), rs.getFloat("final_exam_mark"));
		}

		return us;
	}

	// ========================================================================================================================
	public boolean updateUserSubject(UserSubject us) throws SQLException {
		String query = "UPDATE tbl_user_subject SET user_code = ?, subject_code = ?, attendance_exam_mark = ?, exam_mark_1 = ?, exam_mark_2 = ?, exam_mark_3 = ?, final_exam_mark = ?"
				+ " WHERE user_subject_code = ?";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, us.getUserCode());
		statement.setInt(2, us.getSubjectCode());
		statement.setFloat(3, us.getAttendanceExamMark());
		statement.setFloat(4, us.getExamMark1());
		statement.setFloat(5, us.getExamMark2());
		statement.setFloat(6, us.getExamMark3());
		statement.setFloat(7, us.getFinalExamMark());
		statement.setInt(8, us.getUserSubjectCode());

		int rowsUpdated = statement.executeUpdate();
		return rowsUpdated > 0;
	}

	// ========================================================================================================================
	public boolean deleteUserSubject(int userSubjectCode) throws SQLException {
		String query = "DELETE FROM tbl_user_subject WHERE user_subject_code = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, userSubjectCode);

		int rowsDeleted = statement.executeUpdate();
		return rowsDeleted > 0;
	}
}
