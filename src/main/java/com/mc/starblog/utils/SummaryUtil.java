package com.mc.starblog.utils;

import org.jsoup.Jsoup;

public class SummaryUtil {

    public static String generateSummary(String summary, String htmlContent, int maxLength) {
        if (summary != null && !summary.isBlank()) {
            return summary;
        }
        if (htmlContent == null || htmlContent.isBlank()) {
            return "";
        }
        String plainText = Jsoup.parse(htmlContent).text();
        return plainText.length() > maxLength ? plainText.substring(0, maxLength) + "..." : plainText;
    }
}
