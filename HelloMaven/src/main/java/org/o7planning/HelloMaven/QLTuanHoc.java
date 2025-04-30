package org.o7planning.HelloMaven;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.o7planning.HelloMaven.models.LichHoc;
import org.o7planning.HelloMaven.models.Thu;
import org.o7planning.HelloMaven.models.Tuan;

public class QLTuanHoc {
	public Map<Integer, Tuan> tuanMap = new HashMap<>();

	private static final LocalDate NGAY_BAT_DAU_HOC_KY = LocalDate.of(2025, 1, 13);

	public void xemThoiKhoaBieuNgayHienTai() {
		xemThoiKhoaBieuTheoNgay(LocalDate.now());
	}

	public void xemThoiKhoaBieuTheoNgayNhap(String ngayNhap) {
		try {
			LocalDate ngay = LocalDate.parse(ngayNhap, DateTimeFormatter.ISO_LOCAL_DATE);
			xemThoiKhoaBieuTheoNgay(ngay);
		} catch (Exception e) {
			System.out.println("Định dạng ngày không hợp lệ! Vui lòng nhập theo định dạng yyyy-MM-dd.");
		}
	}

	private void xemThoiKhoaBieuTheoNgay(LocalDate ngay) {
		int soTuan = (int) ChronoUnit.WEEKS.between(NGAY_BAT_DAU_HOC_KY, ngay) + 1;
		Tuan tuan = tuanMap.get(soTuan);

		if (tuan == null) {
			System.out.println("Không có lịch học cho ngày " + ngay);
			return;
		}

		DayOfWeek thu = ngay.getDayOfWeek();
		Thu thuTrongTuan = tuan.thuMap.get(thu);

		if (thuTrongTuan == null || thuTrongTuan.getLichHocList().isEmpty()) {
			System.out.println("Không có lịch học cho ngày " + ngay);
			return;
		}

		System.out.println("Lịch học ngày " + ngay + ":");
		thuTrongTuan.getLichHocList().forEach(lich -> System.out.println("- " + lich));
	}

	public void xemThoiKhoaBieuTheoTuan(int soTuan) {
		Tuan tuan = tuanMap.get(soTuan);
		if (tuan == null) {
			System.out.println("Tuần này không có lịch học nào");
			return;
		}

		System.out.println("LỊCH HỌC TUẦN " + soTuan);
		for (DayOfWeek day : DayOfWeek.values()) {
			Thu thu = tuan.thuMap.get(day);
			System.out.println("|_" + day);
			if (thu == null || thu.getLichHocList().isEmpty()) {
				System.out.println("|\t|_" + "\u001B[31mKhông có lịch học\u001B[0m");
			} else {
				thu.getLichHocList().forEach(lich -> System.out.println("|\t|_" + lich));
			}
		}
	}

	public void xemThoiKhoaBieuTheoTuanVaThu(int soTuan, String thuStr) {
		DayOfWeek thu = layDayOfWeekTuChuoi(thuStr);
		if (thu == null) {
			System.out.println("Thứ không hợp lệ: " + thuStr);
			return;
		}

		Tuan tuan = tuanMap.get(soTuan);
		if (tuan == null) {
			System.out.println("Không có lịch học cho tuần " + soTuan);
			return;
		}

		Thu thuTrongTuan = tuan.thuMap.get(thu);
		if (thuTrongTuan == null || thuTrongTuan.getLichHocList().isEmpty()) {
			System.out.println("Không có lịch học cho thứ " + thuStr + " tuần " + soTuan);
			return;
		}

		System.out.println("Lịch học thứ " + thuStr + " tuần " + soTuan + ":");
		thuTrongTuan.getLichHocList().forEach(lich -> System.out.println("- " + lich));
	}

	private DayOfWeek layDayOfWeekTuChuoi(String thuStr) {
		return switch (thuStr.toUpperCase()) {
		case "2" -> DayOfWeek.MONDAY;
		case "3" -> DayOfWeek.TUESDAY;
		case "4" -> DayOfWeek.WEDNESDAY;
		case "5" -> DayOfWeek.THURSDAY;
		case "6" -> DayOfWeek.FRIDAY;
		case "7" -> DayOfWeek.SATURDAY;
		case "CN" -> DayOfWeek.SUNDAY;
		default -> null;
		};
	}

	public Element getBangThoiKhoaBieu(String htmlPath) throws IOException {
		File html = new File(htmlPath);
		Document doc = Jsoup.parse(html);
		Element table = doc.selectFirst("table");

		return table;
	}

	public Map<Integer, Tuan> getThoiKhoaBieu(Element table) throws IOException {
		tuanMap = new HashMap<>();
		Elements rows = table.select("tr");

		// Kiểm tra xem coi thời khóa biểu có đang bị rỗng hay không
		if (rows.isEmpty()) {
			System.out.println("Thời khóa biểu không có lịch học!");
			return tuanMap;
		}

		// Duyệt qua từng dòng trong bảng
		for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
			Elements cols = rows.get(rowIndex).select("td");

			// Xóa các cột ẩn (d-none)
			cols.removeIf(col -> col.hasClass("d-none"));

			// Lấy lịch học dòng hiện tại
			LichHoc lichHoc = getLichHoc(table, rowIndex);

			// Lấy chuỗi tuần ở cột cuối cùng của dòng
			String weekString = cols.get(cols.size() - 1).text();

			// Lấy thứ của lịch học ở dòng hiện tại đang xét
			DayOfWeek day = getThu(table, rowIndex);

			// Đếm số tuần theo số ký tự trong weekString
			int weekCount = 1;
			for (char c : weekString.toCharArray()) {
				if (Character.isDigit(c)) {

					Tuan tuan = tuanMap.get(weekCount);
					if (tuan == null) {
						tuan = new Tuan();
						tuanMap.put(weekCount, tuan);
					}

					Thu thu = tuan.thuMap.get(day);
					if (thu == null) {
						thu = new Thu();
						tuan.thuMap.put(day, thu);
					}

					// Thêm lịch học vào thứ
					thu.lichHocList.add(lichHoc);
				}

				weekCount++;
			}
		}
		return tuanMap;
	}

	public DayOfWeek getThu(Element table, int rowIndex) {
		DayOfWeek day = null;

		Element row = table.select("tr").get(rowIndex);
		Elements cols = row.select("td");

		// Xóa các cột ẩn (d-none)
		cols.removeIf(col -> col.hasClass("d-none"));

		String thu;
		// Lấy giá trị thứ ở cột thứ 6 đối với dòng cha, cột 0 đối với dòng con
		if (cols.size() < 7)
			thu = cols.get(0).text();
		else
			thu = cols.get(5).text();

		switch (thu) {
		case "2":
			day = DayOfWeek.MONDAY;
			break;
		case "3":
			day = DayOfWeek.TUESDAY;
			break;
		case "4":
			day = DayOfWeek.WEDNESDAY;
			break;
		case "5":
			day = DayOfWeek.THURSDAY;
			break;
		case "6":
			day = DayOfWeek.FRIDAY;
			break;
		case "7":
			day = DayOfWeek.SATURDAY;
			break;
		case "CN":
			day = DayOfWeek.SUNDAY;
			break;
		}

		return day;
	}

	public LichHoc getLichHoc(Element table, int rowIndex) {
		Element row = table.select("tr").get(rowIndex);
		Elements cols = row.select("td");

		// Xóa các cột ẩn (d-none)
		cols.removeIf(col -> col.hasClass("d-none"));

		LichHoc lichHoc = new LichHoc();

		// Nếu dòng này có ít cột (tức là dòng con), cần lấy dữ liệu cha từ dòng trước
		if (cols.size() < 7 && rowIndex > 0) {
			// Tìm dòng cha (dòng gần nhất phía trên có rowspan)
			for (int i = rowIndex - 1; i >= 0; i--) {
				Element rowParent = table.select("tr").get(i);
				Elements parentCols = rowParent.select("td");

				// Tìm xem dòng này có chứa td với rowspan không
				boolean hasRowspan = parentCols.stream().anyMatch(td -> td.hasAttr("rowspan"));
				if (hasRowspan) {
					// Lọc bỏ các cột ẩn
					parentCols.removeIf(col -> col.hasClass("d-none"));

					lichHoc.setMaMon(parentCols.get(0).text().trim());
					lichHoc.setTenMon(parentCols.get(1).text().trim());
					lichHoc.setNhomTo(parentCols.get(2).text().trim());
					lichHoc.setSoTinChi(Integer.parseInt(parentCols.get(3).text().trim()));
					lichHoc.setLop(parentCols.get(4).text().trim());
					break;
				}
			}

			// Các cột còn lại là tiết học
			lichHoc.setTietBatDau(Integer.parseInt(cols.get(1).text().trim()));
			lichHoc.setSoTiet(Integer.parseInt(cols.get(2).text().trim()));
			lichHoc.setPhong(cols.get(3).text().trim());
			lichHoc.setGiangVien(cols.get(4).text().trim());
		} else {
			// Dòng đầy đủ, không cần tra dòng cha
			lichHoc.setMaMon(cols.get(0).text().trim());
			lichHoc.setTenMon(cols.get(1).text().trim());
			lichHoc.setNhomTo(cols.get(2).text().trim());
			lichHoc.setSoTinChi(Integer.parseInt(cols.get(3).text().trim()));
			lichHoc.setLop(cols.get(4).text().trim());
			lichHoc.setTietBatDau(Integer.parseInt(cols.get(6).text().trim()));
			lichHoc.setSoTiet(Integer.parseInt(cols.get(7).text().trim()));
			lichHoc.setPhong(cols.get(8).text().trim());
			lichHoc.setGiangVien(cols.get(9).text().trim());
		}

		return lichHoc;
	}
}
