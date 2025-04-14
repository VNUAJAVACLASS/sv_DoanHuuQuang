package Models;

import java.util.Scanner;

public abstract class Subject {
	private String subjectCode;
	private String subjectName;
	private int credit;
	private float attendanceMark;
	private float midExamMark;
	private float finalExamMark;
	
	public Subject() {
	}

	public Subject(String subjectCode, String subjectName, int credit) {
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.credit = credit;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
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

	public float getAttendanceMark() {
		return attendanceMark;
	}

	public void setAttendanceMark(float attendanceMark) {
		this.attendanceMark = attendanceMark;
	}

	public float getMidExamMark() {
		return midExamMark;
	}

	public void setMidExamMark(float midExamMark) {
		this.midExamMark = midExamMark;
	}

	public float getFinalExamMark() {
		return finalExamMark;
	}

	public void setFinalExamMark(float finalExamMark) {
		this.finalExamMark = finalExamMark;
	}
	
	public abstract void enterInfor(Scanner sc);
	
	public abstract float calSubjectMark();
	
	public float calConversionMark() {
		float tongDiem = calSubjectMark();

		if (tongDiem <= 3.9) {
			return 0;
		} else if (tongDiem <= 4.9) {
			return 1;
		} else if (tongDiem <= 5.4) {
			return 1.5f;
		} else if (tongDiem <= 6.4) {
			return 2;
		} else if (tongDiem <= 6.9) {
			return 2.5f;
		} else if (tongDiem <= 7.4) {
			return 3;
		} else if (tongDiem <= 8.4) {
			return 3.5f;
		} else {
			return 4;
		}
	}
	
	public String calGrade() {
		float tongDiem = calSubjectMark();

		if (tongDiem <= 3.9) {
			return "F";
		} else if (tongDiem <= 4.9) {
			return "D";
		} else if (tongDiem <= 5.4) {
			return "D+";
		} else if (tongDiem <= 6.4) {
			return "C";
		} else if (tongDiem <= 6.9) {
			return "C+";
		} else if (tongDiem <= 7.4) {
			return "B";
		} else if (tongDiem <= 8.4) {
			return "B+";
		} else {
			return "A";
		}
	}
	
	@Override
	public String toString() {
		return "Điểm chuyên cần: " + attendanceMark + "\n"
				+ "Điểm giữa kỳ: " + midExamMark + "\n"
				+ "Điểm cuối kỳ" + finalExamMark + "\n"
				+ "Điểm tổng kết (Thang 10): " + calSubjectMark() + "\n"
				+ "Điểm tổng kết (Thang 4): " + calConversionMark() + "\n"
				+ "Điểm dạng chữ: " + calGrade() + "\n";
	}
}
