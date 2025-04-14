import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student extends Human {
	private String className;
	private List<Subject> subjectList;
	
	public Student() {
	}
	
	public Student(String className) {
		this.className = className;
	}
	
	public Student(String className, List<Subject> subjectList) {
		this.className = className;
		this.subjectList = subjectList;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public List<Subject> getSubjectList() {
		return subjectList;
	}
	
	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}
	
	public void enterInfor (Scanner sc) {
		super.enterInfor(sc);
		System.out.print("Ten Lop: ");
		this.className = sc.nextLine();
		
		subjectList = new ArrayList<Subject>();
		System.out.println("Nhap danh sach mon hoc: ");
		
		Subject subject;
		while (true) {
			System.out.print("1: Nhap tiep mon hoc | 2: Hoan thanh: ");
			int luaChon = sc.nextInt();
			sc.nextLine();
			
			if (luaChon != 1) break;
			
			subject = new Subject();
			subject.enterInfor(sc);
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + " - " + className;
	}
}
