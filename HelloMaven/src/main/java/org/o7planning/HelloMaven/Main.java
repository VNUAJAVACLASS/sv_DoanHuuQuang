package org.o7planning.HelloMaven;

import java.io.IOException;
import java.util.Scanner;

import org.jsoup.nodes.Element;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		QLTuanHoc cTrinhChinh = new QLTuanHoc();

		Element table = cTrinhChinh.getBangThoiKhoaBieu("src/main/resources/tkb_doanhuuquang.html");

		cTrinhChinh.tuanMap = cTrinhChinh.getThoiKhoaBieu(table);
		cTrinhChinh.xemThoiKhoaBieuNgayHienTai();

		do {
			System.out.println();
			System.out.println("===* CHỌN CHỨC NĂNG *===");
			System.out.println("1. Xem thời khóa biểu ngày hiện tại");
			System.out.println("2. Xem thời khóa biểu theo tuần (Nhập vào số tuần)");
			System.out.println("3. Xem thời khóa biểu theo tuần và thứ (Nhập vào số tuần và thứ)");
			System.out.println("4. Xem thời khóa biểu theo ngày (Nhập ngày/tháng/năm)\n");

			int luaChon = 0;
			luaChon = scanner.nextInt();

			if (luaChon == 1) {
				cTrinhChinh.xemThoiKhoaBieuNgayHienTai();
			} else if (luaChon == 2) {
				System.out.print("Nhập số tuần: ");
				int tuan = scanner.nextInt();
				cTrinhChinh.xemThoiKhoaBieuTheoTuan(tuan);
			} else if (luaChon == 3) {
				System.out.print("Nhập số tuần (Số tuần phải lớn hơn 0): ");
				int tuan = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Nhập thứ (Định dạng thứ từ thứ 2 - thứ 7, CN): ");
				String thu = scanner.nextLine();

				cTrinhChinh.xemThoiKhoaBieuTheoTuanVaThu(tuan, thu);
			} else if (luaChon == 4) {
				System.out.print("Nhập ngày cần xem (yyyy-MM-dd): ");
				String ngayNhap = scanner.nextLine();
				
				cTrinhChinh.xemThoiKhoaBieuTheoNgayNhap(ngayNhap);
			} else {
				System.out.println("Lựa chọn không hợp lệ");
			}
		} while (true);
	}
}
