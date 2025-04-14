package Models;

import java.util.Scanner;

public class PythonSubject extends Subject {
	protected float encourageMark;

	public float getEncourageMark() {
		return encourageMark;
	}

	public void setEncourageMark(float encourageMark) {
		this.encourageMark = encourageMark;
	}
	
	@Override
	public void enterInfor(Scanner sc) {
		setSubjectCode("Language02");
		setSubjectName("Python");
		setCredit(2);
		
		System.out.print("---Diem chuyen can: ");
		float diemChuyenCan = sc.nextFloat();
		while (diemChuyenCan < 0 || diemChuyenCan > 10) {
			System.out.print("------*Diem chuyen can khong hop le, nhap lai: ");
			diemChuyenCan = sc.nextFloat();
			sc.nextLine();
		}
		setAttendanceMark(diemChuyenCan);
		
		System.out.print("---Diem giua ky: ");
		float diemGiuaKy = sc.nextFloat();
		while (diemGiuaKy < 0 || diemGiuaKy > 10) {
			System.out.print("------*Diem giua ky khong hop le, nhap lai: ");
			diemGiuaKy = sc.nextFloat();
			sc.nextLine();
		}
		setMidExamMark(diemGiuaKy);
		
		System.out.print("---Diem cuoi ky: ");
		float diemCuoiKy = sc.nextFloat();
		while (diemCuoiKy < 0 || diemCuoiKy > 10) {
			System.out.print("------*Diem cuoi ky khong hop le, nhap lai: ");
			diemCuoiKy = sc.nextFloat();
			sc.nextLine();
		}
		setFinalExamMark(diemCuoiKy);
		
		System.out.print("---Diem khuyến khích: ");
		float diemKhuyenKhich = sc.nextFloat();
		while (diemKhuyenKhich < 0 || diemKhuyenKhich > 10) {
			System.out.print("------*Diem khuyến khích khong hợp le, nhap lai: ");
			diemKhuyenKhich = sc.nextFloat();
			sc.nextLine();
		}
		setEncourageMark(diemKhuyenKhich);
	}

	@Override
	public float calSubjectMark() {
		return getAttendanceMark() * 0.1f 
				+ getMidExamMark() * 0.3f 
				+ getFinalExamMark() * 0.5f
				+ getEncourageMark() * 0.1f;
	}

	@Override
	public String toString() {
		return super.toString() 
				+ "Điểm khuyến khích: " + encourageMark;
	}
}
