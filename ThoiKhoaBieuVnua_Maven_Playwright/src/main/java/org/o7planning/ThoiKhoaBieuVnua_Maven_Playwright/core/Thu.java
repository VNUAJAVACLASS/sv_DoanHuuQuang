package org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.core;

import java.util.ArrayList;
import java.util.List;

public class Thu {
	public List<LichHoc> lichHocList;

	public Thu() {
		lichHocList = new ArrayList<>();
	}

	public Thu(List<LichHoc> lichHocList) {
		super();
		this.lichHocList = lichHocList;
	}

	public List<LichHoc> getLichHocList() {
		return lichHocList;
	}

	public void setLichHocList(List<LichHoc> lichHocList) {
		this.lichHocList = lichHocList;
	}
}
