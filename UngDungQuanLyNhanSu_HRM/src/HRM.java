import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HRM extends Human {
	private List<Human> hrList;
	
	public HRM () {
		this.hrList = new ArrayList<>();
	}
	
	public void addHR (Human hm) {
		hrList.add(hm);
	}
	
	public void addHR (Scanner sc) {
		
	}
	
	public void printHRList () {
		System.out.println("--- TAT CA NHAN SU ---");
		for (Human human : hrList) {
			System.out.println(human.getFullname() + " - " + human.getCode());
		}
	}
	
	public void printLectureInfor () {
		System.out.println("--- DANH SACH GIANG VIEN ---");
		for (Human human : hrList) {
			if (human instanceof Lecturer) {
				System.out.println(human.toString());
			}
		}
	}
	
	public void printStudentInfor () {
		System.out.println("--- DANH SACH SINH VIEN ---");
		for (Human human : hrList) {
			if (human instanceof Student) {
				System.out.println(human.toString());
			}
		}
	}
	
	public String searchHuman (String code) {
		for (Human human : hrList) {
			if (human.getCode().equals(code)) {
				return human.toString();
			}
		}
		
		return "Khong tim thay";
	}
	
	public static void main (String args[]) {
		HRM hrm = new HRM();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("======================= NHAP THONG TIN NHAN SU =============================");
		System.out.println("");
		
		while (true) {
			System.out.println("CHON LOAI NHAN SU: ");
			System.out.println("1: SINH VIEN | 2: GIANG VIEN | 3: THOAT");
			int luaChon;
			
			System.out.print("Nhap lua chon: ");
			luaChon = sc.nextInt();
			sc.nextLine();
			System.out.println("");
			
			if (luaChon == 1) {
				Student student = new Student();
				student.enterInfor(sc);
				
				hrm.addHR(student);
			} else if (luaChon == 2) {
				Lecturer lecturer = new Lecturer();
				lecturer.enterInfor(sc);
				
				hrm.addHR(lecturer);
			} else {
				break;
			}
			
			System.out.println("");
		}
		
		System.out.println("======================= DANH SACH NHAN SU =============================");
		hrm.printHRList();
		System.out.println("");
		hrm.printStudentInfor();
		System.out.println("");
		hrm.printLectureInfor();
		System.out.println("");
		
		System.out.println("======================= TIM KIEM NHAN SU =============================");
		System.out.print("Nhap vao ma nhan su can tim: ");
		String code = sc.nextLine();
		
		System.out.println("Ket qua tim kiem: " + hrm.searchHuman(code));
	}
}
