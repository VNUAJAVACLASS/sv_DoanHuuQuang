package org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterUtil {

	public static String formatLocalDate(LocalDate date, String pattern) {
		if (date == null || pattern == null || pattern.isEmpty()) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return date.format(formatter);
	}

	public static LocalDate parseToLocalDate(String dateStr, String pattern) {
		if (dateStr == null || dateStr.isEmpty() || pattern == null || pattern.isEmpty()) {
			return null;
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(dateStr, formatter);
	}
}
