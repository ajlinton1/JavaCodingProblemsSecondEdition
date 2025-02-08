package chapter3;

import org.junit.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static chapter3.DayPeriod.getDayPeriod;


public class Chapter3 {

    @Test
    public void problem68() {
        /*
        Defining a day period: Write an application that goes beyond AM/PM flags and split the day into four periods: night, morning, afternoon, and evening. Depending on the given date-time and time zone generate one of these periods.
         */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

        ZonedDateTime dateTime1 = ZonedDateTime.of(LocalDateTime.of(2023, 10, 5, 5, 30), ZoneId.of("America/New_York"));
        ZonedDateTime dateTime2 = ZonedDateTime.of(LocalDateTime.of(2023, 10, 5, 11, 30), ZoneId.of("Europe/London"));
        ZonedDateTime dateTime3 = ZonedDateTime.of(LocalDateTime.of(2023, 10, 5, 15, 30), ZoneId.of("Asia/Tokyo"));
        ZonedDateTime dateTime4 = ZonedDateTime.of(LocalDateTime.of(2023, 10, 5, 20, 30), ZoneId.of("Australia/Sydney"));

        System.out.println("DateTime: " + dateTime1.format(formatter) + " - Period: " + getDayPeriod(dateTime1));
        System.out.println("DateTime: " + dateTime2.format(formatter) + " - Period: " + getDayPeriod(dateTime2));
        System.out.println("DateTime: " + dateTime3.format(formatter) + " - Period: " + getDayPeriod(dateTime3));
        System.out.println("DateTime: " + dateTime4.format(formatter) + " - Period: " + getDayPeriod(dateTime4));
        assert(true);
    }

    public static YearMonth convertDateToYearMonth(Date date) {
        return YearMonth.from(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public static Date convertYearMonthToDate(YearMonth yearMonth) {
        return Date.from(yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Test
    public void problem69() {
        /*
        Converting between Date and YearMonth: Write an application that converts between java.util.Date and java.time.YearMonth and vice versa.
         */
        // Convert Date to YearMonth
        Date currentDate = new Date();
        YearMonth yearMonth = convertDateToYearMonth(currentDate);
        System.out.println("Converted YearMonth: " + yearMonth);

        // Convert YearMonth to Date
        Date convertedDate = convertYearMonthToDate(yearMonth);
        System.out.println("Converted Date: " + convertedDate);
        assert(true);
    }

    public static int[] extractWeekYearFromLocalDate(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int week = date.get(weekFields.weekOfYear());
        int year = date.getYear();
        return new int[]{week, year};
    }

    public static int[] extractWeekYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        return new int[]{week, year};
    }

    public static int convertYearMonthToInt(YearMonth yearMonth) {
        return yearMonth.getYear() * 12 + yearMonth.getMonthValue();
    }

    public static YearMonth convertIntToYearMonth(int value) {
        int year = value / 12;
        int month = value % 12;
        return YearMonth.of(year, month == 0 ? 12 : month);
    }

    @Test
    public void problem70() {
        /*
        Converting between int and YearMonth: Let’s consider that a YearMonth is given (for instance, 2023-02). Convert it to an integer representation (for instance, 24277) that can be converted back to YearMonth.
        */

        YearMonth yearMonth = YearMonth.of(2023, 2);
        int intValue = convertYearMonthToInt(yearMonth);
        System.out.println("Integer representation: " + intValue);

        YearMonth convertedYearMonth = convertIntToYearMonth(intValue);
        System.out.println("Converted YearMonth: " + convertedYearMonth);
    }

    public static Date convertWeekYearToDate(int week, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    public static LocalDate convertWeekYearToLocalDate(int week, int year) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return LocalDate.now()
                .withYear(year)
                .with(weekFields.weekOfYear(), week)
                .with(weekFields.dayOfWeek(), 1);
    }

    @Test
    public void problem71() {
        /*
        Converting week/year to Date: Let’s consider that two integers are given representing a week and a year (for instance, week 10, year 2023). Write a program that converts 10-2023 to a java.util.Date via Calendar and to a LocalDate via the WeekFields API. Also, do vice versa: from a given Date/LocalDate extract the year and the week as integers.
         */

        Date date = convertWeekYearToDate(10, 2023);
        System.out.println("Converted Date: " + date);

        // Convert week and year to LocalDate
        LocalDate localDate = convertWeekYearToLocalDate(10, 2023);
        System.out.println("Converted LocalDate: " + localDate);

        // Extract week and year from Date
        int[] weekYearFromDate = extractWeekYearFromDate(date);
        System.out.println("Extracted from Date - Week: " + weekYearFromDate[0] + ", Year: " + weekYearFromDate[1]);

        // Extract week and year from LocalDate
        int[] weekYearFromLocalDate = extractWeekYearFromLocalDate(localDate);
        System.out.println("Extracted from LocalDate - Week: " + weekYearFromLocalDate[0] + ", Year: " + weekYearFromLocalDate[1]);

    }

    @Test
    public void problem72() {
        /*
        Checking for a leap year: Let’s consider that an integer is given representing a year. Write an application that checks if this year is a leap year. Provide at least three solutions.
         */
    }

    @Test
    public void problem73() {
        /*
        Calculating the quarter of a given date: Let’s consider that a java.util.Date is given. Write a program that returns the quarter containing this date as an integer (1, 2,3, or 4) and as a string (Q1, Q2, Q3, or Q4).
         */
    }

    @Test
    public void problem74() {
        /*
        Getting the first and last day of a quarter: Let’s consider that a java.util.Date is given. Write a program that returns the first and last day of the quarter containing this date. Represent the returned days as Date (implementation based on Calendar) and LocalDate (implementation based on the JDK 8 Date/Time API).
         */
    }

    @Test
    public void problem75() {
        /*
        Extracting the months from a given quarter: Let’s consider that a quarter is given (as an integer, a string (Q1, Q2, Q3, or Q4), or a LocalDate). Write a program that extracts the names of the months of this quarter.
         */
    }

    @Test
    public void problem76() {
        /*
        Computing pregnancy due date: Write a pregnancy due date calculator.
         */
    }

    @Test
    public void problem77() {
        /*
        Implementing a stopwatch: Write a program that implements a stopwatch via System.nanoTime() and via Instant.now().
         */
    }

    @Test
    public void problem78() {
        /*
        Extracting the count of milliseconds since midnight: Let’s consider that a LocalDateTime is given. Write an application that counts the milliseconds passed from midnight to this LocalDateTime.
         */
    }

    @Test
    public void problem79() {
        /*
        Splitting a date-time range into equal intervals: Let’s assume that we have a date-time range given via two LocalDateTime instances, and an integer, n. Write an application that splits the given range into n equal intervals (n equal LocalDateTime instances).
         */
    }

    @Test
    public void problem80() {
        /*
        Explaining the difference between Clock.systemUTC() and Clock.systemDefaultZone(): Explain via meaningful examples what is the difference between systemUTC() and systemDefaultZone().
         */
    }

    @Test
    public void problem81() {
        /*
        Displaying the names of the days of the week: Display the names of the days of the week via the java.text.DateFormatSymbols API.
         */
    }

    @Test
    public void problem82() {
        /*
        Getting the first and last day of the year: Let’s consider that an integer representing a year is given. Write a program that returns the first and last day of this year. Provide a solution based on the Calendar API and one based on the JDK 8 Date/Time API.
         */
    }

    @Test
    public void problem83() {
        /*
        Getting the first and last day of the week: Let’s assume that we have an integer representing a number of weeks (for instance, 3 represents three consecutive weeks starting from the current date). Write a program that returns the first and last day of each week. Provide a solution based on the Calendar API and one based on the JDK 8 Date/Time API.
         */
    }

    @Test
    public void problem84() {
        /*
        Calculating the middle of the month: Provide an application containing a snippet based on the Calendar API, and one based on the JDK 8 Date/Time API for calculating the middle of the given month as a Date, respectively as a LocalDate.
         */
    }

    @Test
    public void problem85() {
        /*
        Getting the number of quarters between two dates: Let’s consider that a date-time range is given via two LocalDate instances. Write a program that counts the number of quarters contained in this range.
         */
    }

    @Test
    public void problem86() {
        /*
        Converting Calendar to LocalDateTime: Write a program that converts the given Calendar into a LocalDateTime (default time zone), respectively into a ZonedDateTime (for the Asia/Calcutta time zone).
        */
    }

    @Test
    public void problem87() {
        /*
        Getting the number of weeks between two dates: Let’s assume that we have a date-time range given as two Date instances or as two LocalDateTime instances. Write an application that returns the number of weeks contained in this range. For the Date range, write a solution based on the Calendar API, while for the LocalDateTime range, write a solution based on the JDK 8 Date/Time API.
         */
    }
}
