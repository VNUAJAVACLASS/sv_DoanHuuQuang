package org.o7planning.HelloMaven.models;

public class LichHoc {
	private String maMon;
	private String tenMon;
	private String nhomTo;
	private int soTinChi;
	private String lop;
	private int tietBatDau;
	private int soTiet;
	private String phong;
	private String giangVien;

	public LichHoc() {
		super();
	}

	public LichHoc(String maMon, String tenMon, String nhomTo, int soTinChi, String lop, int tietBatDau, int soTiet,
			String phong, String giangVien) {
		super();
		this.maMon = maMon;
		this.tenMon = tenMon;
		this.nhomTo = nhomTo;
		this.soTinChi = soTinChi;
		this.lop = lop;
		this.tietBatDau = tietBatDau;
		this.soTiet = soTiet;
		this.phong = phong;
		this.giangVien = giangVien;
	}

	public String getMaMon() {
		return maMon;
	}

	public void setMaMon(String maMon) {
		this.maMon = maMon;
	}

	public String getTenMon() {
		return tenMon;
	}

	public void setTenMon(String tenMon) {
		this.tenMon = tenMon;
	}

	public String getNhomTo() {
		return nhomTo;
	}

	public void setNhomTo(String nhomTo) {
		this.nhomTo = nhomTo;
	}

	public int getSoTinChi() {
		return soTinChi;
	}

	public void setSoTinChi(int soTinChi) {
		this.soTinChi = soTinChi;
	}

	public String getLop() {
		return lop;
	}

	public void setLop(String lop) {
		this.lop = lop;
	}

	public int getTietBatDau() {
		return tietBatDau;
	}

	public void setTietBatDau(int tietBatDau) {
		this.tietBatDau = tietBatDau;
	}

	public int getSoTiet() {
		return soTiet;
	}

	public void setSoTiet(int soTiet) {
		this.soTiet = soTiet;
	}

	public String getPhong() {
		return phong;
	}

	public void setPhong(String phong) {
		this.phong = phong;
	}

	public String getGiangVien() {
		return giangVien;
	}

	public void setGiangVien(String giangVien) {
		this.giangVien = giangVien;
	}

	@Override
	public String toString() {
		String green = "\u001B[32m";
		String reset = "\u001B[0m";
		return green + "Mã môn học: " + maMon + reset + 
				"| Tên môn học: " + tenMon + 
				"| Nhóm tổ: " + nhomTo + 
				"| Số tín chỉ: " + soTinChi + 
				"| Lớp: " + lop + 
				"| Tiết bắt đầu: " + tietBatDau + 
				"| Số tiết: " + soTiet + 
				"| Phòng: " + phong +
				"| Giảng viên: " + giangVien;
	}
	
	
}
