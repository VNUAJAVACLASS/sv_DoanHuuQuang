package Models;

import java.util.Scanner;

public class JavaSubject extends Subject {
	
	@Override
	public void enterInfor(Scanner sc) {
		setSubjectCode("Language01");
		setSubjectName("Java");
		setCredit(3);
		
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
	}
	
	@Override
	public float calSubjectMark() {
		return getAttendanceMark() * 0.1f 
				+ getMidExamMark() * 0.3f 
				+ getFinalExamMark() * 0.6f;
	}
}
