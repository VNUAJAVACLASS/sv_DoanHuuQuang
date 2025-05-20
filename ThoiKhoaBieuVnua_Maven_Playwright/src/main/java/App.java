import org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.services.AuthService;
import org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.utils.DateTimeFormatterUtil;
import org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.core.QLTuanHoc;

import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Ứng dụng xem thời khóa biểu VNUA sử dụng Playwright
 */
public class App {
	private static final int TIME_OUT = 3000; // Thời gian chờ (ms)
	private static final boolean HEADLESS_MODE = false; // Chế độ không hiển thị trình duyệt

	private Playwright playwright;
	private Browser browser;
	private Page page;
	private Scanner scanner;
	private AuthService authService;
	private QLTuanHoc qlTuanHoc;

	/**
	 * Khởi tạo ứng dụng
	 */
	public App() {
		this.scanner = new Scanner(System.in);
	}

	/**
	 * Điểm vào chương trình
	 * 
	 * @param args Tham số dòng lệnh
	 */
	public static void main(String[] args) {
		App app = new App();
		app.run();
	}

	/**
	 * Chạy ứng dụng
	 */
	public void run() {
		try {
			System.out.println("===== THỜI KHÓA BIỂU VNUA =====");
			System.out.println("Đang khởi tạo ứng dụng...");

			initPlaywright();

			// Đăng nhập vào hệ thống
			login();

			// Lấy thông tin học kỳ
			initSemesterData();

			// Hiển thị menu chức năng
			showFeatureMenu();

		} catch (Exception e) {
			System.err.println("Đã xảy ra lỗi: " + e.getMessage());
			e.printStackTrace();
		} finally {
			cleanup();
		}
	}

	/**
	 * Khởi tạo Playwright và trình duyệt
	 */
	private void initPlaywright() {
		try {
			playwright = Playwright.create();

			LaunchOptions options = new LaunchOptions().setHeadless(HEADLESS_MODE);

			browser = playwright.chromium().launch(options);
			page = browser.newPage();

			authService = new AuthService(page);
		} catch (Exception e) {
			throw new RuntimeException("Không thể khởi tạo trình duyệt: " + e.getMessage(), e);
		}
	}

	/**
	 * Đăng nhập vào hệ thống
	 * 
	 * @throws InterruptedException nếu quá trình bị gián đoạn
	 */
	private void login() throws InterruptedException {
		boolean isLoggedIn = false;

		while (!isLoggedIn) {
			isLoggedIn = performLogin();

			if (!isLoggedIn) {
				System.out.println("Thử lại sau 2 giây...");
				TimeUnit.SECONDS.sleep(2);
			}
		}
	}

	/**
	 * Thực hiện đăng nhập
	 * 
	 * @return true nếu đăng nhập thành công, ngược lại false
	 */
	private boolean performLogin() {
		String studentId = null;
		String password = null;

		System.out.print("Nhập mã sinh viên: ");
		studentId = scanner.nextLine();
		System.out.print("Nhập mật khẩu: ");
		password = scanner.nextLine();

		System.out.println("Đang đăng nhập...");

		// Thực hiện đăng nhập
		String userName = authService.login(studentId, password);
		boolean isLoggedIn = userName != null && !userName.isEmpty();

		// Hiển thị kết quả đăng nhập
		if (isLoggedIn) {
			System.out.println("Xin chào: " + userName);
		} else {
			System.out.println("Đăng nhập không thành công, thông tin tài khoản không đúng!");
		}

		System.out.println("==============================");
		return isLoggedIn;
	}

	/**
	 * Khởi tạo dữ liệu học kỳ
	 * 
	 * @throws InterruptedException nếu quá trình bị gián đoạn
	 */
	private void initSemesterData() throws InterruptedException {
		System.out.println("Đang lấy thông tin học kỳ...");
		TimeUnit.MILLISECONDS.sleep(TIME_OUT);

		qlTuanHoc = new QLTuanHoc(page);

		// Hiển thị danh sách học kỳ
		qlTuanHoc.showSemesterList();
		System.out.println("==============================");

		int semesterIndex = promptForSemesterIndex();

		System.out.println("Đang lấy thông tin thời khóa biểu... ");
		qlTuanHoc.chooseSemester(semesterIndex);

		TimeUnit.MILLISECONDS.sleep(TIME_OUT);

		// Thiết lập ngày bắt đầu học kỳ
		qlTuanHoc.setDate();
		LocalDate startDate = qlTuanHoc.getDate();
		System.out.println("Ngày bắt đầu học kỳ: " + DateTimeFormatterUtil.formatLocalDate(startDate, "dd/MM/yyyy"));
		
		System.out.println("Đang tải thời khỏa biểu... ");
		TimeUnit.MILLISECONDS.sleep(TIME_OUT);

		// Lấy dữ liệu bảng thời khóa biểu
		qlTuanHoc.getTable(semesterIndex);
		TimeUnit.MILLISECONDS.sleep(TIME_OUT);

		try {
			qlTuanHoc.getThoiKhoaBieu();
			System.out.println("Đã tải xong thời khóa biểu!");
		} catch (Exception e) {
			System.err.println("Lỗi khi tải thời khóa biểu: " + e.getMessage());
		}

		System.out.println("==============================");
	}

	/**
	 * Hiển thị và xử lý menu chức năng
	 */
	private void showFeatureMenu() {
		boolean exitRequested = false;

		while (!exitRequested) {
			try {
				displayMenuOptions();
				int choice = promptForMenuChoice();

				exitRequested = processMenuChoice(choice);
			} catch (InputMismatchException e) {
				System.err.println("Lựa chọn không hợp lệ. Vui lòng nhập số.");
				scanner.nextLine(); // Xóa buffer đầu vào
			} catch (Exception e) {
				System.err.println("Đã xảy ra lỗi: " + e.getMessage());
			}
		}
	}

	/**
	 * Hiển thị các tùy chọn menu
	 */
	private void displayMenuOptions() {
		System.out.println();
		System.out.println("===== MENU CHỨC NĂNG =====");
		System.out.println("0. Thoát ứng dụng");
		System.out.println("1. Xem thời khóa biểu ngày hiện tại");
		System.out.println("2. Xem thời khóa biểu theo tuần");
		System.out.println("3. Xem thời khóa biểu theo tuần và thứ");
		System.out.println("4. Xem thời khóa biểu theo ngày");
		System.out.println("===========================");
	}

	/**
	 * Yêu cầu người dùng nhập lựa chọn menu
	 * 
	 * @return Lựa chọn menu
	 */
	private int promptForMenuChoice() {
		System.out.print("Nhập lựa chọn của bạn: ");
		int choice = scanner.nextInt();
		scanner.nextLine(); // Xóa buffer đầu vào
		return choice;
	}

	/**
	 * Yêu cầu người dùng nhập chỉ số học kỳ
	 * 
	 * @return Chỉ số học kỳ
	 */
	private int promptForSemesterIndex() {
		while (true) {
			try {
				System.out.print("Chọn học kỳ để xem thời khóa biểu: ");
				int index = scanner.nextInt();
				scanner.nextLine();
				return index;
			} catch (InputMismatchException e) {
				System.err.println("Vui lòng nhập số hợp lệ.");
				scanner.nextLine();
			}
		}
	}

	/**
	 * Xử lý lựa chọn menu
	 * 
	 * @param choice Lựa chọn menu
	 * @return true nếu người dùng muốn thoát, ngược lại false
	 */
	private boolean processMenuChoice(int choice) {
		switch (choice) {
		case 0:
			System.out.println("===== Đang đóng ứng dụng... =====");
			return true;

		case 1:
			System.out.println("===== Thời khóa biểu ngày hiện tại =====");
			qlTuanHoc.xemThoiKhoaBieuNgayHienTai();
			break;

		case 2:
			int weekNumber = promptForWeekNumber();
			System.out.println("===== Thời khóa biểu tuần " + weekNumber + " =====");
			qlTuanHoc.xemThoiKhoaBieuTheoTuan(weekNumber);
			break;

		case 3:
			int week = promptForWeekNumber();
			String dayOfWeek = promptForDayOfWeek();
			System.out
					.println("===== Thời khóa biểu " + convertDayOfWeekString(dayOfWeek) + " tuần " + week + " =====");
			qlTuanHoc.xemThoiKhoaBieuTheoTuanVaThu(week, dayOfWeek);
			break;

		case 4:
			String date = promptForDate();
			System.out.println("===== Thời khóa biểu ngày " + date + " =====");
			qlTuanHoc.xemThoiKhoaBieuTheoNgayNhap(date);
			break;

		default:
			System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
			break;
		}

		return false;
	}

	/**
	 * Chuyển đổi mã thứ thành tên thứ đầy đủ
	 * 
	 * @param dayCode Mã thứ (2, 3, 4, 5, 6, 7, CN)
	 * @return Tên thứ đầy đủ
	 */
	private String convertDayOfWeekString(String dayCode) {
		switch (dayCode) {
		case "2":
			return "Thứ 2";
		case "3":
			return "Thứ 3";
		case "4":
			return "Thứ 4";
		case "5":
			return "Thứ 5";
		case "6":
			return "Thứ 6";
		case "7":
			return "Thứ 7";
		case "CN":
			return "Chủ nhật";
		default:
			return dayCode;
		}
	}

	/**
	 * Yêu cầu người dùng nhập số tuần
	 * 
	 * @return Số tuần
	 */
	private int promptForWeekNumber() {
		while (true) {
			try {
				System.out.print("Nhập số tuần (lớn hơn 0): ");
				int week = scanner.nextInt();
				scanner.nextLine(); // Xóa buffer đầu vào

				if (week <= 0) {
					System.err.println("Số tuần phải lớn hơn 0.");
					continue;
				}

				return week;
			} catch (InputMismatchException e) {
				System.err.println("Vui lòng nhập số hợp lệ.");
				scanner.nextLine(); // Xóa buffer đầu vào
			}
		}
	}

	/**
	 * Yêu cầu người dùng nhập thứ
	 * 
	 * @return Thứ (2, 3, 4, 5, 6, 7, CN)
	 */
	private String promptForDayOfWeek() {
		while (true) {
			System.out.print("Nhập thứ (2, 3, 4, 5, 6, 7, CN): ");
			String day = scanner.nextLine().trim();
			if (day.matches("[2-7]|CN")) {
				return day;
			} else {
				System.err.println("Thứ không hợp lệ. Vui lòng nhập lại.");
			}
		}
	}

	/**
	 * Yêu cầu người dùng nhập ngày
	 * 
	 * @return Ngày (yyyy-MM-dd)
	 */
	private String promptForDate() {
		System.out.print("Nhập ngày cần xem (yyyy-MM-dd): ");
		return scanner.nextLine().trim();
	}

	/**
	 * Dọn dẹp tài nguyên
	 */
	private void cleanup() {
		System.out.println("Đang đóng ứng dụng...");

		try {
			if (scanner != null) {
				scanner.close();
			}

			// Đóng các tài nguyên của Playwright
			if (page != null) {
				page.close();
			}

			if (browser != null) {
				browser.close();
			}

			if (playwright != null) {
				playwright.close();
			}
		} catch (Exception e) {
			System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
		}
	}
}