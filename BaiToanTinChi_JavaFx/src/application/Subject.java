package application;

public class Subject {
	private int subjectCode;
	private String subjectName;
	private int credit;
	private float attendanceExamMark;
	private float examMark1;
	private float examMark2;
	private float examMark3;
	private float finalExamMark;
	
	public Subject() {
		super();
	}

	public Subject(String subjectName, int credit, float attendanceExamMark, float examMark1, float examMark2,
			float examMark3, float finalExamMark) {
		super();
		this.subjectName = subjectName;
		this.credit = credit;
		this.attendanceExamMark = attendanceExamMark;
		this.examMark1 = examMark1;
		this.examMark2 = examMark2;
		this.examMark3 = examMark3;
		this.finalExamMark = finalExamMark;
	}

	public Subject(int subjectCode, String subjectName, int credit, float attendanceExamMark, float examMark1,
			float examMark2, float examMark3, float finalExamMark) {
		super();
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.credit = credit;
		this.attendanceExamMark = attendanceExamMark;
		this.examMark1 = examMark1;
		this.examMark2 = examMark2;
		this.examMark3 = examMark3;
		this.finalExamMark = finalExamMark;
	}

	public int getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(int subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
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
