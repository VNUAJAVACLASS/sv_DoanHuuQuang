package org.o7planning.ThoiKhoaBieuVnua_Maven_Playwright.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TableParser {
	public static Document parseHtmlToDocument(String html) {
        return Jsoup.parse(html);
    }
}
