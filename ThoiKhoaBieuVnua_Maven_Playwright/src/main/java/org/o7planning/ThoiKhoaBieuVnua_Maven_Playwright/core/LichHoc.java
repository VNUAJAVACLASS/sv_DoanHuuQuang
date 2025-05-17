package org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.core;

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
		return  "#-------Mã môn học: " + maMon + 
				"\n\tTên môn học: " + tenMon + 
				"\n\tNhóm tổ: " + nhomTo + 
				"\n\tSố tín chỉ: " + soTinChi + 
				"\n\tLớp: " + lop + 
				"\n\tTiết bắt đầu: " + tietBatDau + 
				"\n\tSố tiết: " + soTiet + 
				"\n\tPhòng: " + phong +
				"\n\tGiảng viên: " + giangVien;
	}
	
	
}
