import java.util.Scanner;

public class Subject {
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

	public float calConversionMark(String grade) {
		switch (grade) {
		case "F":
			return 0;
		case "D":
			return 1;
		case "D+":
			return 1.5f;
		case "C":
			return 2;
		case "C+":
			return 2.5f;
		case "B":
			return 3;
		case "B+":
			return 3.5f;
		case "A":
			return 4;
		default:
			return -1;
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

	public float calSubjectMark() {
		return attendanceMark * 0.1f + midExamMark * 0.3f + finalExamMark * 0.6f;
	}

	public int getCredit() {
		return this.credit;
	}

	public void setAttendanceMark(float attendanceMark) {
		this.attendanceMark = attendanceMark;
	}

	public void setFinalExamMark(float finalExamMark) {
		this.finalExamMark = finalExamMark;
	}

	public void setMidExamMark(float midExamMark) {
		this.midExamMark = midExamMark;
	}
	
	public void enterInfor (Scanner sc) {
		System.out.print("---Ma mon hoc: ");
		this.subjectCode = sc.nextLine();
		System.out.print("---Ten mon hoc: ");
		this.subjectName = sc.nextLine();
		
		System.out.print("---So tin chi: ");
		int soTinChi = sc.nextInt();
		while (soTinChi <= 0 || soTinChi > 4) {
			System.out.print("------*So tin chi khong hop le, nhap lai: ");
			soTinChi = sc.nextInt();
			sc.nextLine();
		}
		this.credit = soTinChi;	
		
		
		System.out.print("---Diem chuyen can: ");
		float diemChuyenCan = sc.nextFloat();
		while (diemChuyenCan < 0 || diemChuyenCan > 10) {
			System.out.print("------*Diem chuyen can khong hop le, nhap lai: ");
			diemChuyenCan = sc.nextFloat();
			sc.nextLine();
		}
		this.attendanceMark = diemChuyenCan;
		
		System.out.print("---Diem giua ky: ");
		float diemGiuaKy = sc.nextFloat();
		while (diemGiuaKy < 0 || diemGiuaKy > 10) {
			System.out.print("------*Diem giua ky khong hop le, nhap lai: ");
			diemGiuaKy = sc.nextFloat();
			sc.nextLine();
		}
		this.midExamMark = diemGiuaKy;
		
		System.out.print("---Diem cuoi ky: ");
		float diemCuoiKy = sc.nextFloat();
		while (diemCuoiKy < 0 || diemCuoiKy > 10) {
			System.out.print("------*Diem cuoi ky khong hop le, nhap lai: ");
			diemCuoiKy = sc.nextFloat();
			sc.nextLine();
		}
		this.finalExamMark = diemCuoiKy;
	}

	@Override
	public String toString() {
		return "Subject [subjectCode=" + subjectCode + ", subjectName=" + subjectName + ", credit=" + credit
				+ ", attendanceMark=" + attendanceMark + ", midExamMark=" + midExamMark + ", finalExamMark="
				+ finalExamMark + "]";
	}
}
