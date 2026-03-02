package com.geosieben.gsbworkday.addon;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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


}
