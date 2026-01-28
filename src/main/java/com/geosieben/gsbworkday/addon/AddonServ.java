package com.geosieben.gsbworkday.addon;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddonServ {
    public static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return date.format(formatter);
    }


}
