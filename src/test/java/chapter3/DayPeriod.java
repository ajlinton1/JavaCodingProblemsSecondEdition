package chapter3;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DayPeriod {

    public static String getDayPeriod(ZonedDateTime dateTime) {
        int hour = dateTime.getHour();

        if (hour >= 0 && hour < 6) {
            return "Night";
        } else if (hour >= 6 && hour < 12) {
            return "Morning";
        } else if (hour >= 12 && hour < 18) {
            return "Afternoon";
        } else {
            return "Evening";
        }
    }
}
