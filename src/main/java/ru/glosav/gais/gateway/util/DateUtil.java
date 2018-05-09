package ru.glosav.gais.gateway.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String parse(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }
}
