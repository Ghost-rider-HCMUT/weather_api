package com.example.spb_api.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HandleTimeUtil {
    // Handle Date
    public static LocalDateTime handleTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(time, formatter);
    }
}
