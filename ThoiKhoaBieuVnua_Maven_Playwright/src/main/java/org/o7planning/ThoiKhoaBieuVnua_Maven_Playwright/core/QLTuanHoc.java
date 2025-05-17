package org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.core;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.scraper.DaoTaoScraper;
import org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.utils.TableParser;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

/**
 * Quản lý thời khóa biểu theo tuần học
 */
public class QLTuanHoc {

	private Map<Integer, Tuan> tuanMap = new HashMap<>();
	private LocalDate ngayBatDauHocKy;
	private final Page page;
	private final DaoTaoScraper scraper;
	private final List<ElementHandle> semesters;
	private Document doc;

	public QLTuanHoc(Page page) {
		this.page = page;
		this.scraper = new DaoTaoScraper(page);
		this.semesters = scraper.fetchSemesterList();
	}

	/**
	 * Hiển thị danh sách học kỳ có sẵn
	 */
	public void showSemesterList() {
		int count = 1;
		for (ElementHandle semester : semesters) {
			System.out.println(count + ":\t" + semester.innerText());
			count++;
		}
	}

	/**
	 * Chọn học kỳ theo chỉ số
	 * 
	 * @param semesterIndex Chỉ số học kỳ (bắt đầu từ 1)
	 */
	public void chooseSemester(int semesterIndex) {
		if (semesterIndex < 1 || semesterIndex > semesters.size()) {
			throw new IllegalArgumentException("Chỉ số học kỳ không hợp lệ: " + semesterIndex);
		}
		ElementHandle selectedSemester = semesters.get(semesterIndex - 1);
		selectedSemester.click();
	}

	/**
	 * Lấy ngày bắt đầu học kỳ
	 * 
	 * @return Ngày bắt đầu học kỳ
	 */
	public LocalDate getStartDateOfTerm() {
		return scraper.fetchStartDateOfTerm();
	}

	/**
	 * Thiết lập ngày bắt đầu học kỳ
	 */
	public void setDate() {
		this.ngayBatDauHocKy = getStartDateOfTerm();
	}

	/**
	 * Lấy ngày bắt đầu học kỳ đã thiết lập
	 * 
	 * @return Ngày bắt đầu học kỳ
	 */
	public LocalDate getDate() {
		return this.ngayBatDauHocKy;
	}

	/**
	 * Lấy bảng thời khóa biểu cho học kỳ được chọn
	 * 
	 * @param semesterIndex Chỉ số học kỳ
	 */
	public void getTable(int semesterIndex) {
		String html = scraper.fetchTable(semesterIndex);
		this.doc = TableParser.parseHtmlToDocument(html);
	}

	/**
	 * Xem thời khóa biểu cho ngày hiện tại
	 */
	public void xemThoiKhoaBieuNgayHienTai() {
		xemThoiKhoaBieuTheoNgay(LocalDate.now());
	}

	/**
	 * Xem thời khóa biểu theo ngày được nhập
	 * 
	 * @param ngayNhap Ngày cần xem (định dạng yyyy-MM-dd)
	 */
	public void xemThoiKhoaBieuTheoNgayNhap(String ngayNhap) {
		try {
			LocalDate ngay = LocalDate.parse(ngayNhap, DateTimeFormatter.ISO_LOCAL_DATE);
			xemThoiKhoaBieuTheoNgay(ngay);
		} catch (Exception e) {
			System.out.println("Định dạng ngày không hợp lệ! Vui lòng nhập theo định dạng yyyy-MM-dd.");
		}
	}

	/**
	 * Xem thời khóa biểu theo ngày
	 * 
	 * @param ngay Ngày cần xem
	 */
	private void xemThoiKhoaBieuTheoNgay(LocalDate ngay) {
		if (ngayBatDauHocKy == null) {
			System.out.println("Chưa thiết lập ngày bắt đầu học kỳ!");
			return;
		}

		// Tính số tuần đã trôi qua, + 1 để tính tuần hiện tại
		int soTuan = (int) ChronoUnit.WEEKS.between(ngayBatDauHocKy, ngay) + 1;
		if (soTuan < 1) {
			System.out.println("Ngày " + ngay + " nằm trước ngày bắt đầu học kỳ!");
			return;
		}

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
		thuTrongTuan.getLichHocList().forEach(lich -> System.out.println(lich));
	}

	/**
	 * Xem thời khóa biểu theo tuần
	 * 
	 * @param soTuan Số tuần cần xem
	 */
	public void xemThoiKhoaBieuTheoTuan(int soTuan) {
		Tuan tuan = tuanMap.get(soTuan);
		if (tuan == null) {
			System.out.println("Tuần " + soTuan + " không có lịch học nào");
			return;
		}

		for (DayOfWeek day : DayOfWeek.values()) {
			System.out.println("--------------------" + day + "--------------------");
			
			Thu thu = tuan.thuMap.get(day);
			if (thu == null || thu.getLichHocList().isEmpty()) {
				System.out.println("Không có lịch học");
			} else {
				thu.getLichHocList().forEach(lich -> System.out.println(lich));
			}
		}
	}

	/**
	 * Xem thời khóa biểu theo tuần và thứ
	 * 
	 * @param soTuan Số tuần cần xem
	 * @param thuStr Thứ cần xem (2, 3, 4, 5, 6, 7, CN)
	 */
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
			System.out.println("Không có lịch học cho " + convertDayOfWeekToString(thu) + " tuần " + soTuan);
			return;
		}

		System.out.println("Lịch học " + convertDayOfWeekToString(thu) + " tuần " + soTuan + ":");
		thuTrongTuan.getLichHocList().forEach(lich -> System.out.println(lich));
	}

	/**
	 * Chuyển đổi chuỗi thứ sang DayOfWeek
	 * 
	 * @param thuStr Chuỗi thứ (2, 3, 4, 5, 6, 7, CN)
	 * @return DayOfWeek tương ứng hoặc null nếu không hợp lệ
	 */
	public DayOfWeek layDayOfWeekTuChuoi(String thuStr) {
		if (thuStr == null) {
			return null;
		}

		switch (thuStr.toUpperCase()) {
		case "2":
			return DayOfWeek.MONDAY;
		case "3":
			return DayOfWeek.TUESDAY;
		case "4":
			return DayOfWeek.WEDNESDAY;
		case "5":
			return DayOfWeek.THURSDAY;
		case "6":
			return DayOfWeek.FRIDAY;
		case "7":
			return DayOfWeek.SATURDAY;
		case "CN":
			return DayOfWeek.SUNDAY;
		default:
			return null;
		}
	}

	/**
	 * Chuyển đổi DayOfWeek sang chuỗi thứ
	 * 
	 * @param day DayOfWeek cần chuyển đổi
	 * @return Chuỗi thứ tương ứng (Thứ 2, Thứ 3, ..., Chủ nhật)
	 */
	private String convertDayOfWeekToString(DayOfWeek day) {
		switch (day) {
		case MONDAY:
			return "Thứ 2";
		case TUESDAY:
			return "Thứ 3";
		case WEDNESDAY:
			return "Thứ 4";
		case THURSDAY:
			return "Thứ 5";
		case FRIDAY:
			return "Thứ 6";
		case SATURDAY:
			return "Thứ 7";
		case SUNDAY:
			return "Chủ nhật";
		default:
			return day.toString();
		}
	}

	/**
	 * Lấy thời khóa biểu từ bảng HTML đã parse
	 * 
	 * @return Map chứa thời khóa biểu theo tuần
	 * @throws IOException Nếu có lỗi xảy ra khi xử lý dữ liệu
	 */
	public Map<Integer, Tuan> getThoiKhoaBieu() throws IOException {
		tuanMap = new HashMap<>();

		if (doc == null) {
			System.out.println("Chưa lấy dữ liệu bảng thời khóa biểu!");
			return tuanMap;
		}

		Elements rows = doc.select("tr");

		// Kiểm tra xem thời khóa biểu có đang rỗng hay không
		if (rows.isEmpty()) {
			System.out.println("Thời khóa biểu không có lịch học!");
			return tuanMap;
		}

		// Duyệt qua từng dòng trong bảng
		for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
			Element row = rows.get(rowIndex);
			Elements cols = row.select("td");

			// Xóa các cột ẩn (d-none)
			cols.removeIf(col -> col.hasClass("d-none"));

			// Bỏ qua dòng không có dữ liệu
			if (cols.isEmpty()) {
				continue;
			}

			// Lấy lịch học dòng hiện tại
			LichHoc lichHoc = getLichHoc(doc, rowIndex);

			// Lấy chuỗi tuần ở cột cuối cùng của dòng
			String weekString = cols.get(cols.size() - 1).text();

			// Lấy thứ của lịch học ở dòng hiện tại đang xét
			DayOfWeek day = getThu(doc, rowIndex);

			if (day == null || lichHoc == null) {
				continue;
			}

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

	/**
	 * Lấy thứ từ dòng trong bảng HTML
	 * 
	 * @param table    Bảng HTML
	 * @param rowIndex Chỉ số dòng
	 * @return DayOfWeek tương ứng hoặc null nếu không xác định được
	 */
	private DayOfWeek getThu(Element table, int rowIndex) {
		Element row = table.select("tr").get(rowIndex);
		Elements cols = row.select("td");

		// Xóa các cột ẩn (d-none)
		cols.removeIf(col -> col.hasClass("d-none"));

		if (cols.isEmpty()) {
			return null;
		}

		String thu;
		// Lấy giá trị thứ ở cột thứ 6 đối với dòng cha, cột 0 đối với dòng con
		if (cols.size() < 7) {
			thu = cols.get(0).text();
		} else {
			thu = cols.get(5).text();
		}

		return layDayOfWeekTuChuoi(thu);
	}

	/**
	 * Lấy thông tin lịch học từ dòng trong bảng HTML
	 * 
	 * @param table    Bảng HTML
	 * @param rowIndex Chỉ số dòng
	 * @return Đối tượng LichHoc chứa thông tin lịch học
	 */
	private LichHoc getLichHoc(Element table, int rowIndex) {
		try {
			Element row = table.select("tr").get(rowIndex);
			Elements cols = row.select("td");

			// Xóa các cột ẩn (d-none)
			cols.removeIf(col -> col.hasClass("d-none"));

			if (cols.isEmpty()) {
				return null;
			}

			LichHoc lichHoc = new LichHoc();

			// Nếu dòng này có ít cột (tức là dòng con), cần lấy dữ liệu cha từ dòng trước
			// gần nhất
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

						if (!parentCols.isEmpty()) {
							lichHoc.setMaMon(parentCols.get(0).text().trim());
							lichHoc.setTenMon(parentCols.get(1).text().trim());
							lichHoc.setNhomTo(parentCols.get(2).text().trim());
							lichHoc.setSoTinChi(parseIntSafely(parentCols.get(3).text().trim(), 0));
							lichHoc.setLop(parentCols.get(4).text().trim());
							break;
						}
					}
				}

				// Các cột còn lại là tiết học
				if (cols.size() > 4) {
					lichHoc.setTietBatDau(parseIntSafely(cols.get(1).text().trim(), 0));
					lichHoc.setSoTiet(parseIntSafely(cols.get(2).text().trim(), 0));
					lichHoc.setPhong(cols.get(3).text().trim());
					lichHoc.setGiangVien(cols.get(4).text().trim());
				}
			} else if (cols.size() >= 10) {
				// Dòng đầy đủ, không cần tra dòng cha
				lichHoc.setMaMon(cols.get(0).text().trim());
				lichHoc.setTenMon(cols.get(1).text().trim());
				lichHoc.setNhomTo(cols.get(2).text().trim());
				lichHoc.setSoTinChi(parseIntSafely(cols.get(3).text().trim(), 0));
				lichHoc.setLop(cols.get(4).text().trim());
				lichHoc.setTietBatDau(parseIntSafely(cols.get(6).text().trim(), 0));
				lichHoc.setSoTiet(parseIntSafely(cols.get(7).text().trim(), 0));
				lichHoc.setPhong(cols.get(8).text().trim());
				lichHoc.setGiangVien(cols.get(9).text().trim());
			}

			return lichHoc;
		} catch (Exception e) {
			System.err.println("Lỗi khi xử lý dòng " + rowIndex + ": " + e.getMessage());
			return null;
		}
	}

	/**
	 * Chuyển đổi chuỗi thành số nguyên an toàn
	 * 
	 * @param str          Chuỗi cần chuyển đổi
	 * @param defaultValue Giá trị mặc định nếu chuyển đổi thất bại
	 * @return Số nguyên sau khi chuyển đổi hoặc giá trị mặc định nếu thất bại
	 */
	private int parseIntSafely(String str, int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
