package application;

public class UserSubject {
	private int userSubjectCode;
	private int userCode;
	private int subjectCode;
	private float attendanceExamMark;
	private float examMark1;
	private float examMark2;
	private float examMark3;
	private float finalExamMark;

	public UserSubject() {
		super();
	}

	public UserSubject(int userCode, int subjectCode, float attendanceExamMark, float examMark1, float examMark2,
			float examMark3, float finalExamMark) {
		super();
		this.userCode = userCode;
		this.subjectCode = subjectCode;
		this.attendanceExamMark = attendanceExamMark;
		this.examMark1 = examMark1;
		this.examMark2 = examMark2;
		this.examMark3 = examMark3;
		this.finalExamMark = finalExamMark;
	}

	public UserSubject(int userSubjectCode, int userCode, int subjectCode, float attendanceExamMark, float examMark1,
			float examMark2, float examMark3, float finalExamMark) {
		super();
		this.userSubjectCode = userSubjectCode;
		this.userCode = userCode;
		this.subjectCode = subjectCode;
		this.attendanceExamMark = attendanceExamMark;
		this.examMark1 = examMark1;
		this.examMark2 = examMark2;
		this.examMark3 = examMark3;
		this.finalExamMark = finalExamMark;
	}

	public int getUserSubjectCode() {
		return userSubjectCode;
	}

	public void setUserSubjectCode(int userSubjectCode) {
		this.userSubjectCode = userSubjectCode;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public int getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(int subjectCode) {
		this.subjectCode = subjectCode;
	}

	public float getAttendanceExamMark() {
		return attendanceExamMark;
	}

	public void setAttendanceExamMark(float attendanceExamMark) {
		this.attendanceExamMark = attendanceExamMark;
	}

	public float getExamMark1() {
		return examMark1;
	}

	public void setExamMark1(float examMark1) {
		this.examMark1 = examMark1;
	}

	public float getExamMark2() {
		return examMark2;
	}

	public void setExamMark2(float examMark2) {
		this.examMark2 = examMark2;
	}

	public float getExamMark3() {
		return examMark3;
	}

	public void setExamMark3(float examMark3) {
		this.examMark3 = examMark3;
	}

	public float getFinalExamMark() {
		return finalExamMark;
	}

	public void setFinalExamMark(float finalExamMark) {
		this.finalExamMark = finalExamMark;
	}
}
