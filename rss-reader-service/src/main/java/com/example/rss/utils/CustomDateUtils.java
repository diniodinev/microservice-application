package com.example.rss.utils;

import org.apache.commons.lang.StringUtils;

public class CustomDateUtils {

    public static final String[] shortenMonthsBG = new String[] { "яну", "фев", "мар", "апр", "май", "юни", "юли",
            "авг", "сеп", "окт", "ное", "дек" };

    public static final String[] longMonthsBG = new String[] { "Януари", "Февруари", "Март", "Април", "Май", "Юни",
            "Юли", "Август", "Септември", "Октомври", "Ноември", "Декември" };

    private CustomDateUtils() {
        super();
    }

    public static String replaceWithFullMonthName(String fullDate) {
        if (fullDate != null) {
            for (int i = 0; i < 12; i++) {
                if (StringUtils.contains(fullDate, shortenMonthsBG[i])) {
                    return StringUtils.replace(fullDate, shortenMonthsBG[i], longMonthsBG[i]);
                }
            }
        }
        return null;
    }

}
