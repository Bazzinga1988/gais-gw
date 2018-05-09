package ru.glosav.gais.gateway.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String parse(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    public static void main(String[] args) {
        // 1546214400 соответствует 31.12.2018.
        Date d = new Date(1546214400000L);
        System.out.println("d = " + d);
        System.out.println("d.getTime() / 1000 = " + d.getTime() / 1000);

    }
}
