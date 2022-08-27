package com.projeto.organizze.helper;

import java.text.SimpleDateFormat;

public class DateUtil {
    public static String dataAtual () {
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("dd/M/yyyy");
        String dataString = simpleDateFormat.format(data);
        return dataString;
    }
}
