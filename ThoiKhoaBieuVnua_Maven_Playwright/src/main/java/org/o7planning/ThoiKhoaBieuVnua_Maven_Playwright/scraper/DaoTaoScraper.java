package org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.scraper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.core.Constants;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

public class DaoTaoScraper {
    private static final String COMBO_BOX_XPATH = "//*[@id=\"fullScreen\"]/div[2]/div[2]/div[1]/ng-select";
    private static final String DROP_DOWN_ITEM_SELECTOR = ".ng-option";
    private static final String SEMESTER_COMBO_BOX_XPATH = "/html/body/app-root[1]/div/div/div/div[1]/div/div/div[1]/app-thoikhoabieu-tuan/div[1]/div[2]/div[1]/div[1]/ng-select/div/div";
    private static final String SEMESTER_DROP_DOWN_SELECTOR = ".ng-option";
    private static final String SEMESTER_TABLE_COMBO_BOX_XPATH = "/html/body/app-root[1]/div/div/div/div[1]/div/div/div[1]/app-tkb-hocky/div/div[2]/div[1]/div/ng-select/div";

    private static final int TIMEOUT_MS = 3000;

    private final Page page;

    public DaoTaoScraper(Page page) {
        this.page = page;
    }

    public List<ElementHandle> fetchSemesterList() {
        try {
            page.navigate(Constants.URL_DAO_TAO_VNUA_TKB_TUAN);
            waitForPage();

            page.locator("xpath=" + SEMESTER_COMBO_BOX_XPATH).click();
            waitForPage();

            return page.querySelectorAll(SEMESTER_DROP_DOWN_SELECTOR);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Trả về list rỗng thay vì null
        }
    }

    public LocalDate fetchStartDateOfTerm() {
        try {
            waitForPage();

            page.locator("xpath=" + COMBO_BOX_XPATH).click();
            waitForPage();

            List<ElementHandle> weeks = page.querySelectorAll(DROP_DOWN_ITEM_SELECTOR);
            if (weeks.isEmpty()) return null;

            ElementHandle firstWeek = weeks.get(0);
            String weekId = firstWeek.getAttribute("id");
            if (weekId == null || !weekId.contains("-")) return null;

            String[] parts = weekId.split("-");
            int weekNumber = Integer.parseInt(parts[1]) + 1;

            LocalDate currentWeekDate = parseStringToDate(firstWeek.innerText());
            if (currentWeekDate == null) return null;

            // Nếu là tuần đầu tiên thì không cần trừ
            return weekNumber == 0 ? currentWeekDate : currentWeekDate.minusWeeks(weekNumber - 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public LocalDate parseStringToDate(String dateString) {
        if (dateString == null || !dateString.contains("[")) return null;

        int startIndex = dateString.indexOf('[');
        int endIndex = dateString.indexOf(']');
        if (startIndex == -1 || endIndex == -1 || endIndex <= startIndex) return null;

        String insideBrackets = dateString.substring(startIndex + 1, endIndex);
        for (String part : insideBrackets.split(" ")) {
            if (part.matches("\\d{2}/\\d{2}/\\d{4}")) {
                return LocalDate.parse(part, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        }
        return null;
    }

    public String fetchTable(int semesterIndex) {
        try {
            page.navigate(Constants.URL_DAO_TAO_VNUA_TKB_HK);
            waitForPage();

            page.locator("xpath=" + SEMESTER_TABLE_COMBO_BOX_XPATH).click();
            waitForPage();

            List<ElementHandle> semesters = page.querySelectorAll(SEMESTER_DROP_DOWN_SELECTOR);

            if (semesterIndex <= 0 || semesterIndex > semesters.size()) {
                System.err.println("Invalid semester index: " + semesterIndex);
                return null;
            }

            semesters.get(semesterIndex - 1).click();
            waitForPage();

            return page.content();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void waitForPage() {
        try {
            Thread.sleep(TIMEOUT_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
