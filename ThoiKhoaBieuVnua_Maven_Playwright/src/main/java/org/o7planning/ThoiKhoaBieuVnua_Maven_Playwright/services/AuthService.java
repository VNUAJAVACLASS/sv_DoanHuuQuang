package org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.services;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.core.*;

public class AuthService {
	private final Page page;

	private final String USER_NAME_INPUT = "input[name='username']";
	private final String PASSWORD_INPUT = "input[name='password']";
	private final String LOGIN_BUTTON = "button:has-text('Đăng nhập')";
	private final String USER_NAME_LOGGED = "/html/body/app-root[1]/div/div/div/div[1]/div/div/div[2]/app-right/app-login/div/div[2]/div[1]/table/tr[2]/td[2]/span";

	public AuthService(Page page) {
		this.page = page;
	}

	public String login(String userName, String password) {
		page.navigate(Constants.URL_DAO_TAO_VNUA);

		page.waitForSelector(USER_NAME_INPUT).fill(userName);
		page.waitForSelector(PASSWORD_INPUT).fill(password);
		page.waitForSelector(LOGIN_BUTTON).click();

		System.out.println("Đang kiểm tra thông tin tài khoản...");
		page.waitForLoadState();
		int timeout = 3000;

		try {
			page.waitForSelector("xpath=" + USER_NAME_LOGGED,
					new Page.WaitForSelectorOptions().setTimeout(timeout).setState(WaitForSelectorState.ATTACHED));

			// Trả về tên người dùng nếu đăng nhập thành công
			return page.locator("xpath=" + USER_NAME_LOGGED).textContent();
		} catch (Exception e) {
			return null;
		}
	}
}
