package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
	private Connection connection;

	public SubjectDAO() throws SQLException {
		String dbURL = "jdbc:ucanaccess://lib/QLSV.accdb";
		connection = DriverManager.getConnection(dbURL);
	}
	
	// ========================================================================================================================
	public List<Subject> getAllSubject() throws SQLException {
		List<Subject> subjectList = new ArrayList<>();
		String queryString = "SELECT * FROM tbl_subjects";
		Subject subject = null;

		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(queryString);

		while (result.next()) {
			subject = new Subject();

			subject.setSubjectCode(result.getInt("subject_code"));
			subject.setSubjectName(result.getString("subject_name"));
			subject.setCredit(result.getInt("credit"));
			subject.setAttendanceExamMark(result.getFloat("attendance_exam_mark"));
			subject.setExamMark1(result.getFloat("exam_mark_1"));
			subject.setExamMark2(result.getFloat("exam_mark_2"));
			subject.setExamMark3(result.getFloat("exam_mark_3"));
			subject.setFinalExamMark(result.getFloat("final_exam_mark"));

			subjectList.add(subject);
		}

		return subjectList;
	}

	// ========================================================================================================================
	public Subject getSubjectById(int subjectId) throws SQLException {
		Subject subject = null;
		String queryString = "SELECT * FROM tbl_subjects WHERE subject_code = ?";

		PreparedStatement statement = connection.prepareStatement(queryString);
		statement.setInt(1, subjectId);
		ResultSet result = statement.executeQuery();

		if (result.next()) {
			subject = new Subject();

			subject.setSubjectCode(result.getInt("subject_code"));
			subject.setSubjectName(result.getString("subject_name"));
			subject.setCredit(result.getInt("credit"));
			subject.setAttendanceExamMark(result.getFloat("attendance_exam_mark"));
			subject.setExamMark1(result.getFloat("exam_mark_1"));
			subject.setExamMark2(result.getFloat("exam_mark_2"));
			subject.setExamMark3(result.getFloat("exam_mark_3"));
			subject.setFinalExamMark(result.getFloat("final_exam_mark"));
		}

		return subject;
	}
	
	// ========================================================================================================================
	public boolean addSubject(Subject subject) throws SQLException {
		String queryString = "INSERT" 
						  + " INTO tbl_subjects (subject_name, credit, attendance_exam_mark, exam_mark_1, exam_mark_2, exam_mark_3, final_exam_mark)"
						  + " VALUES (? ,? , ?, ?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(queryString);
		statement.setString(1, subject.getSubjectName());
		statement.setInt(2, subject.getCredit());
		statement.setFloat(3, subject.getAttendanceExamMark());
		statement.setFloat(4, subject.getExamMark1());
		statement.setFloat(5, subject.getExamMark2());
		statement.setFloat(6, subject.getExamMark3());
		statement.setFloat(7, subject.getFinalExamMark());
		int rowInserted = statement.executeUpdate();

		return rowInserted > 0;
	}
	
	// ========================================================================================================================
	public boolean updateSubject(Subject subject) throws SQLException {
		String queryString = "UPDATE tbl_subjects "
				+ "SET subject_name = ?, credit = ?, attendance_exam_mark = ?, exam_mark_1 = ?, exam_mark_2 = ?, exam_mark_3 = ?, final_exam_mark = ? "
				+ "WHERE subject_code = ?";

		PreparedStatement statement = connection.prepareStatement(queryString);
		statement.setString(1, subject.getSubjectName());
		statement.setInt(2, subject.getCredit());
		statement.setFloat(3, subject.getAttendanceExamMark());
		statement.setFloat(4, subject.getExamMark1());
		statement.setFloat(5, subject.getExamMark2());
		statement.setFloat(6, subject.getExamMark3());
		statement.setFloat(7, subject.getFinalExamMark());

		int rowsUpdated = statement.executeUpdate();
		return rowsUpdated > 0;
	}
	
	// ========================================================================================================================
	public boolean deleteSubject(int subjectCode) throws SQLException {
		String queryString = "DELETE FROM tbl_subjects WHERE subject_code = ?";

		PreparedStatement statement = connection.prepareStatement(queryString);
		statement.setInt(1, subjectCode);

		int rowsDeleted = statement.executeUpdate();
		return rowsDeleted > 0;
	}
}
