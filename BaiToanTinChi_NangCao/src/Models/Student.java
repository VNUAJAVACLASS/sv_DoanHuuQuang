package Models;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Student extends Human {
	private String className;
	private HashMap<String, Subject> subjectList;
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public HashMap<String, Subject> getSubjectList() {
		return subjectList;
	}
	
	public void setSubjectList(HashMap<String, Subject> subjectList) {
		this.subjectList = subjectList;
	}
	
	public void enterInfor (Scanner sc) {
		System.out.print("Ten Lop: ");
		this.className = sc.nextLine();
		
		System.out.print("Ho ten: ");
		setFullname(sc.nextLine());
		
		System.out.print("Dia chi: ");
		setAddress(sc.nextLine());
		
		System.out.print("Ma SV: ");
		setCode(sc.nextLine());
		
		subjectList = new HashMap<String, Subject>();
		System.out.println("Nhap danh sach mon hoc: ");
		
		while (true) {
			enterSubjectInfor(sc);
			
			System.out.print("1: Nhap tiep mon hoc | 2: Hoan thanh: ");
			int luaChon = sc.nextInt();
			sc.nextLine();
			if (luaChon != 1) break;
		}
	}
	
	public void enterSubjectInfor (Scanner sc) {
		System.out.print("1: Java | 2: Python: ");
		int luaChonMonHoc = sc.nextInt();
		sc.nextLine();
		
		if (luaChonMonHoc == 1) {
			JavaSubject javaSubject = new JavaSubject();
			javaSubject.enterInfor(sc);
			subjectList.put(javaSubject.getSubjectCode(), javaSubject);
		} else if (luaChonMonHoc == 2) {
			PythonSubject pythonSubject = new PythonSubject();
			pythonSubject.enterInfor(sc);
			subjectList.put(pythonSubject.getSubjectCode(), pythonSubject);
		} else {
			System.out.print("Lựa chọn không hợp lệ!");
		}
	}
	
	public void deleteSubjectByKey (String key) {
		subjectList.remove(key);
	}	
	
	public void searchSubjectByCode(String subjectCode) {
		Subject subject = subjectList.get(subjectCode);
		System.out.print("Xóa môn học thành công!"); 
		
		if (subject == null) {
			System.out.print("=============================================");
			System.out.print("Không tìm thấy môn học có mã " + subjectCode); 
			return;
		}
		
		System.out.print("=============================================");
		System.out.println("Môn học tìm thấy: ");
		System.out.print("Tên môn học: " + subject.getSubjectName() + " - " 
						+ "Điểm chuyên cần: " + subject.getAttendanceMark() 
						+ "Điểm giữa kỳ: " + subject.getMidExamMark() 
						+ "Điểm cuối kỳ: " + subject.getFinalExamMark());
	}
	
	public float calGPA () {
		int tongSoTinChi = 0;
		float tongDiemMon = 0;
		
		for(Map.Entry<String, Subject> entry : subjectList.entrySet()) {
			Subject subject = entry.getValue();
			tongSoTinChi += subject.getCredit();
			tongDiemMon += subject.calSubjectMark();
		}
		
		return (tongDiemMon * tongSoTinChi)/tongSoTinChi;
	}
	
	public void searchSubjectById (String id) {
		Subject subject = subjectList.get(id);
		
		if(subject == null) {
			System.out.println("Không tìm thấy!");
			return;
		}
		
		System.out.println("================================");
		System.out.println(subject.toString());
		System.out.println("================================");
	}
	
	public void searchSubjectByName (String name) {
		Subject subject = null;
			
		for(Map.Entry<String, Subject> entry : subjectList.entrySet()) {
			if (entry.getValue().getSubjectName().equals(name)) {
				subject = entry.getValue();
			}
		}
			
		if(subject == null) {
			System.out.println("Không tìm thấy!");
			return;
		}
		
		System.out.println("================================");
		System.out.println(subject.toString());
		System.out.println("================================");
	}
}
