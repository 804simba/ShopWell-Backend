package com.shopwell.api.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String saveDate(LocalDateTime date) {
        return date.format(formatter);
    }

    public static Date getDate(String date) {
        LocalDate localDate = LocalDate.parse(date, formatter);
        return Date.valueOf(localDate);
    }
}
