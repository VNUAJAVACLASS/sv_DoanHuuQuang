package org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.core;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class Tuan {
	public Map<DayOfWeek, Thu> thuMap;

	public Tuan() {
		super();
		thuMap = new HashMap<>();
	}

	public Tuan(Map<DayOfWeek, Thu> thuMap) {
		super();
		this.thuMap = thuMap;
	}

	public Map<DayOfWeek, Thu> getThuMap() {
		return thuMap;
	}

	public void setThuMap(Map<DayOfWeek, Thu> thuMap) {
		this.thuMap = thuMap;
	}
}
