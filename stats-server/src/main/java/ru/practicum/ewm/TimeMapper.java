package ru.practicum.ewm;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class TimeMapper {

    public static String timeToString(LocalDateTime time) {

        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time1 = time.format(customFormat);
        return time1;
    }

    public static LocalDateTime stringToTime(String time) {
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(time, customFormat);
    }
}