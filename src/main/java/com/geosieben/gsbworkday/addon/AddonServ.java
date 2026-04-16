package com.geosieben.gsbworkday.addon;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class AddonServ {
    public static String fullDateFormatter(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return date.format(formatter);
    }
        public static String monthYearFormatter(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yyyy");
        return date.format(formatter);
    }
            public static String monthNumExtract(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M");
        return date.format(formatter);
    }
            public static String yearExtract(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        return date.format(formatter);

        
    }
public static String changePattern(String input) {
    DateTimeFormatter inputFormatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive() // This makes it ignore casing
            .appendPattern("MMM-yyyy")
            .toFormatter(Locale.ENGLISH);

    YearMonth yearMonth = YearMonth.parse(input, inputFormatter);
    return yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
}

public static int noofdaysinmonth(String input) {
    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("MMM-yyyy")
            .toFormatter(Locale.ENGLISH);

    return YearMonth.parse(input, formatter).lengthOfMonth();
}


}
