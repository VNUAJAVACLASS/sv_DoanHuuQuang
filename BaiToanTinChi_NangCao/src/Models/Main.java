package Models;

import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		Student student = new Student();
		student.enterInfor(sc);
		
		System.out.println("=======================================");
		System.out.println("Danh sách môn học: ");
		System.out.println("=======================================");
		for(Map.Entry<String, Subject> entry :student.getSubjectList().entrySet()) {
			Subject subject = entry.getValue();
			System.out.println(subject.toString());
		}
		
		
		while(true)
		{
			int luaChon = 0;
			
			System.out.println("Chọn tính năng:");
			System.out.println("1: Xóa môn học theo mã môn học \n"
								+ "2: Tìm môn học theo mã môn học \n"
								+ "3: Tìm môn học theo tên môn học \n"
								+ "4: Tính điểm trung bình học kỳ \n"
								+ "5: Thoát \n");
			
			luaChon = sc.nextInt();
			sc.nextLine();
			
			if (luaChon == 1) {
				System.out.println("Nhập mã môn học cần xóa: ");
				student.deleteSubjectByKey(sc.nextLine());
			} else if (luaChon == 2) {
				System.out.println("Nhập mã môn học cần tìm: ");
				student.searchSubjectById(sc.nextLine());
			} else if (luaChon == 3) {
				student.searchSubjectByName(sc.nextLine());
			} else if (luaChon == 4) {
				System.out.println("=======================================");
				System.out.println("Điểm trung bình học kỳ: " + student.calGPA());
				System.out.println("=======================================");
			} else if (luaChon == 5) {
				return;
			} else {
				System.out.println("Lựa chọn không hợp lệ!");
			}
		}
	}
}
