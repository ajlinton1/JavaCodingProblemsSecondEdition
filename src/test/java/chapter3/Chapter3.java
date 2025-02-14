package chapter3;

import org.junit.*;

import java.text.DateFormatSymbols;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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

    static YearMonth convertDateToYearMonth(Date date) {
        return YearMonth.from(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    static Date convertYearMonthToDate(YearMonth yearMonth) {
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

    static int[] extractWeekYearFromLocalDate(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int week = date.get(weekFields.weekOfYear());
        int year = date.getYear();
        return new int[]{week, year};
    }

    static int[] extractWeekYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        return new int[]{week, year};
    }

    static int convertYearMonthToInt(YearMonth yearMonth) {
        return yearMonth.getYear() * 12 + yearMonth.getMonthValue();
    }

    static YearMonth convertIntToYearMonth(int value) {
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

    static Date convertWeekYearToDate(int week, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    static LocalDate convertWeekYearToLocalDate(int week, int year) {
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

    static boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0; // Leap year if divisible by 400
            }
            return true; // Leap year if divisible by 4 but not 100
        }
        return false; // Not a leap year if not divisible by 4
    }

    static boolean isLeapYearCalendar(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
        // Check if February has 29 days (i.e., leap year)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH) == 29;
    }

    @Test
    public void problem72() {
        /*
        Checking for a leap year: Let’s consider that an integer is given representing a year. Write an application that checks if this year is a leap year. Provide at least three solutions.
         */
        int year = 2024;
        boolean leapYear = isLeapYear(year);
        assert (leapYear);

        leapYear = Year.of(year).isLeap();
        assert (leapYear);

        leapYear = isLeapYearCalendar(year);
        assert (leapYear);
    }

    static int getQuarter(LocalDate date) {
        int month = date.getMonthValue();
        if (month <= 3) {
            return 1;
        }
        if (month <= 6) {
            return 2;
        }
        if (month <= 9) {
            return 3;
        }
        return 4;
    }

    static String getQuarterString(LocalDate date) {
        int quarter = getQuarter(date);
        switch (quarter) {
            case 1:
                return "Q1";
            case 2:
                return "Q2";
            case 3:
                return "Q3";
            case 4:
                return "Q4";
            default:
                return null;
        }
    }

    @Test
    public void problem73() {
        /*
        Calculating the quarter of a given date: Let’s consider that a java.util.Date is given. Write a program that returns the quarter containing this date as an integer (1, 2,3, or 4) and as a string (Q1, Q2, Q3, or Q4).
         */
        Date currentDate = new Date();
        int quarter = getQuarter(currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        assert (quarter == 1);

        String quarterString = getQuarterString(currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        assert (quarterString.equals("Q1"));
    }

    static Date getFirstDateOfQuarter(int quarter, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, quarter * 3 - 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    static Date getLastDateOfQuarter(int quarter, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, quarter * 3 - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    @Test
    public void problem74() {
        /*
        Getting the first and last day of a quarter: Let’s consider that a java.util.Date is given. Write a program that returns the first and last day of the quarter containing this date. Represent the returned days as Date (implementation based on Calendar) and LocalDate (implementation based on the JDK 8 Date/Time API).
         */

        Date currentDate = new Date();
        int year = currentDate.getYear() + 1900;
        int quarter = getQuarter(currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Date firstDate = getFirstDateOfQuarter(quarter, year);

        Date lastDate = getLastDateOfQuarter(quarter, year);

        assert(firstDate.compareTo(lastDate) < 0);

        LocalDate currentLocalDate = LocalDate.now();
        LocalDate firstLocalDate = currentLocalDate.withMonth(quarter * 3 - 3 + 1).withDayOfMonth(1);
        LocalDate lastLocalDate = currentLocalDate.withMonth(quarter * 3).withDayOfMonth(currentLocalDate.lengthOfMonth());
        assert(firstLocalDate.compareTo(lastLocalDate) < 0);
    }

    @Test
    public void problem75() {
        /*
        Extracting the months from a given quarter: Let’s consider that a quarter is given (as an integer, a string (Q1, Q2, Q3, or Q4), or a LocalDate). Write a program that extracts the names of the months of this quarter.
         */
        int quarter = 2;
        String quarterString = "Q1";
        LocalDate quarterLocalDate = LocalDate.of(2025, 1, 1);

        Date firstDate = getFirstDateOfQuarter(quarter, 2025);
        Date lastDate = getLastDateOfQuarter(quarter, 2025);
        LocalDate localFirstDate = lastDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate localLastDate = firstDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        int firstMonth = (quarter - 1) * 3;
        int lastMonth = firstMonth + 3;

        Month[] months = Month.values();
        for (int i = 0; i < months.length; i++) {
            if (i >= firstMonth && i < lastMonth) {
                System.out.println(months[i]);
            }
        }
    }

    @Test
    public void problem76() {
        /*
        Computing pregnancy due date: Write a pregnancy due date calculator.
         */
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusMonths(9);
        System.out.println("Due date: " + dueDate);
    }

    @Test
    public void problem77() {
        /*
        Implementing a stopwatch: Write a program that implements a stopwatch via System.nanoTime() and via Instant.now().
         */
        long startTime = System.nanoTime();
        for (int i=0; i<1000; i++) {
            System.out.println(i);
        }
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime - startTime) + " nanoseconds");

        Instant startTime1 = Instant.now();
        for (int i=0; i<1000; i++) {
            System.out.println(i);
        }
        Instant endTime1 = Instant.now();
        System.out.println("Time taken: " + (endTime1.toEpochMilli() - startTime1.toEpochMilli()) + " milliseconds");
    }

    @Test
    public void problem78() {
        /*
        Extracting the count of milliseconds since midnight: Let’s consider that a LocalDateTime is given. Write an application that counts the milliseconds passed from midnight to this LocalDateTime.
         */
        LocalDateTime localDateTime = LocalDateTime.now();
        long milliseconds = Duration.between(localDateTime.toLocalTime(), LocalTime.MIDNIGHT).toMillis();
        System.out.println("Milliseconds passed from midnight: " + milliseconds);
        assert(true);
    }

    @Test
    public void problem79() {
        /*
        Splitting a date-time range into equal intervals: Let’s assume that we have a date-time range given via two LocalDateTime instances, and an integer, n. Write an application that splits the given range into n equal intervals (n equal LocalDateTime instances).
         */
        LocalDateTime startDateTime = LocalDateTime.of(2023, 10, 5, 10, 30);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 10, 5, 11, 30);
        int n = 3;
        LocalDateTime[] intervals = new LocalDateTime[n];
        for (int i=0; i<n; i++) {
            intervals[i] = startDateTime.plusMinutes(i * (endDateTime.toInstant(ZoneOffset.UTC).toEpochMilli() - startDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()) / (n - 1));
        }
        for (int i=0; i<n; i++) {
            System.out.println("Interval " + i + ": " + intervals[i]);
        }
        assert(true);
    }

    @Test
    public void problem80() {
        /*
        Explaining the difference between Clock.systemUTC() and Clock.systemDefaultZone(): Explain via meaningful examples what is the difference between systemUTC() and systemDefaultZone().
         */
        Clock utcClock = Clock.systemUTC();
        Instant timestamp = utcClock.instant();

        System.out.println("UTC Timestamp: " + timestamp);

        Clock localClock = Clock.systemDefaultZone();
        ZonedDateTime localTime = ZonedDateTime.now(localClock);

        System.out.println("Local Time: " + localTime);
    }

    @Test
    public void problem81() {
        /*
        Displaying the names of the days of the week: Display the names of the days of the week via the java.text.DateFormatSymbols API.
         */
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        String[] weekdays = dateFormatSymbols.getWeekdays();
        for (String day : weekdays) {
            System.out.println(day);
        }
        assert(true);
    }

    @Test
    public void problem82() {
        /*
        Getting the first and last day of the year: Let’s consider that an integer representing a year is given. Write a program that returns the first and last day of this year. Provide a solution based on the Calendar API and one based on the JDK 8 Date/Time API.
         */
        int year = 2025;
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, 0, 1);
        Date firstDate = calendar.getTime();
        calendar.clear();
        calendar.set(year, 11, 31);
        Date lastDate = calendar.getTime();
        System.out.println("First date: " + firstDate);
        System.out.println("Last date: " + lastDate);
        assert(true);
    }

    @Test
    public void problem83() {
        /*
        Getting the first and last day of the week: Let’s assume that we have an integer representing a number of weeks (for instance, 3 represents three consecutive weeks starting from the current date). Write a program that returns the first and last day of each week. Provide a solution based on the Calendar API and one based on the JDK 8 Date/Time API.
         */
        int numberOfWeeks = 3; // Example: 3 weeks
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < numberOfWeeks; i++) {
            // Move to the start of the week
            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
            System.out.println("Week " + (i + 1) + " starts on: " + calendar.getTime());

            // Move to the end of the week
            calendar.add(Calendar.DAY_OF_WEEK, 6);
            System.out.println("Week " + (i + 1) + " ends on: " + calendar.getTime());

            // Move to the next week
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        //
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < numberOfWeeks; i++) {
            // Get the first day of the week (based on Monday start-of-week)
            LocalDate startOfWeek = currentDate.with(DayOfWeek.MONDAY);
            System.out.println("Week " + (i + 1) + " starts on: " + startOfWeek.format(formatter));

            // Get the last day of the week (Sunday is the last day)
            LocalDate endOfWeek = currentDate.with(DayOfWeek.SUNDAY);
            System.out.println("Week " + (i + 1) + " ends on: " + endOfWeek.format(formatter));

            // Move to the next week
            currentDate = currentDate.plusWeeks(1);
        }


    }

    static Date findMiddleOfMonthUsingCalendar(int year, int month) {
        Calendar calendar = Calendar.getInstance();

        // Set the year and month (note: month in Calendar API is 0-based)
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // Adjust for 0-based indexing

        // Set the day to the middle of the month (15th day)
        calendar.set(Calendar.DAY_OF_MONTH, 15);

        // Return the resulting date
        return calendar.getTime();
    }

    static LocalDate findMiddleOfMonthUsingDateTimeAPI(int year, int month) {
        // Create a LocalDate for the middle of the month (15th day)
        return LocalDate.of(year, month, 15);
    }

    @Test
    public void problem84() {
        /*
        Calculating the middle of the month: Provide an application containing a snippet based on the Calendar API, and one based on the JDK 8 Date/Time API for calculating the middle of the given month as a Date, respectively as a LocalDate.
         */

        int year = 2025;
        int month = 2;

        System.out.println("\n--- Using the Calendar API ---");
        Date middleOfMonthUsingCalendar = findMiddleOfMonthUsingCalendar(year, month);
        System.out.println("Middle of Month (Calendar API): " + middleOfMonthUsingCalendar);

        System.out.println("\n--- Using the JDK 8 Date/Time API ---");
        LocalDate middleOfMonthUsingDateTimeAPI = findMiddleOfMonthUsingDateTimeAPI(year, month);
        System.out.println("Middle of Month (Date/Time API): " + middleOfMonthUsingDateTimeAPI);
    }

    public static int countQuartersInRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to the end date.");
        }

        // Get the year and quarter for the start and end dates
        int startYear = startDate.getYear();
        int startQuarter = getQuarterOfDate(startDate);

        int endYear = endDate.getYear();
        int endQuarter = getQuarterOfDate(endDate);

        // Calculate the total number of quarters
        int totalQuarters = (endYear - startYear) * 4 + (endQuarter - startQuarter) + 1;

        return totalQuarters;
    }

    // Helper method to determine the quarter of the year for a date
    private static int getQuarterOfDate(LocalDate date) {
        int month = date.getMonthValue();
        if (month <= 3) {
            return 1; // Q1
        } else if (month <= 6) {
            return 2; // Q2
        } else if (month <= 9) {
            return 3; // Q3
        } else {
            return 4; // Q4
        }
    }

    @Test
    public void problem85() {
        /*
        Getting the number of quarters between two dates: Let’s consider that a date-time range is given via two LocalDate instances. Write a program that counts the number of quarters contained in this range.
         */
        // Example date range
        LocalDate startDate = LocalDate.of(2023, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2024, Month.MARCH, 15);

        // Counting the quarters
        int numQuarters = countQuartersInRange(startDate, endDate);

        System.out.println("Number of quarters in the range: " + numQuarters);
    }

    // Method to convert Calendar to LocalDateTime
    public static LocalDateTime convertToLocalDateTime(Calendar calendar) {
        Date date = calendar.getTime(); // Convert Calendar to Date
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()); // Use system default time zone
    }

    // Method to convert Calendar to ZonedDateTime for a specific time zone
    public static ZonedDateTime convertToZonedDateTime(Calendar calendar, String timeZone) {
        Date date = calendar.getTime(); // Convert Calendar to Date
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of(timeZone)); // Use given time zone (e.g., Asia/Calcutta)
    }

    @Test
    public void problem86() {
        /*
        Converting Calendar to LocalDateTime: Write a program that converts the given Calendar into a LocalDateTime (default time zone), respectively into a ZonedDateTime (for the Asia/Calcutta time zone).
        */
        // Step 1: Create a Calendar instance
        Calendar calendar = Calendar.getInstance(); // Current date and time
        calendar.set(2023, Calendar.OCTOBER, 30, 10, 45, 00); // Example: 30 October 2023, 10:45:00

        System.out.println("Original Calendar: " + calendar.getTime());

        // Step 2: Convert Calendar to LocalDateTime (default time zone)
        LocalDateTime localDateTime = convertToLocalDateTime(calendar);
        System.out.println("LocalDateTime (default time zone): " + localDateTime);

        // Step 3: Convert Calendar to ZonedDateTime for Asia/Calcutta
        ZonedDateTime zonedDateTime = convertToZonedDateTime(calendar, "Asia/Calcutta");
        System.out.println("ZonedDateTime (Asia/Calcutta): " + zonedDateTime);
    }

    // Method to calculate the number of weeks using Calendar API (Date range)
    public static int calculateWeeksUsingCalendar(Date start, Date end) {
        if (start.after(end)) {
            throw new IllegalArgumentException("Start date must not be after end date.");
        }

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(start);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(end);

        // Calculate the difference in days
        long startTimeInMillis = startCal.getTimeInMillis();
        long endTimeInMillis = endCal.getTimeInMillis();
        long daysDifference = (endTimeInMillis - startTimeInMillis) / (1000 * 60 * 60 * 24); // Milliseconds to days

        // Convert days to weeks
        return (int) Math.ceil(daysDifference / 7.0); // Round up to include partial weeks
    }

    // Method to calculate the number of weeks using JDK 8 Date/Time API (LocalDateTime range)
    public static int calculateWeeksUsingDateTimeAPI(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date-time must not be after end date-time.");
        }

        // Calculate the difference in days
        long daysDifference = ChronoUnit.DAYS.between(start, end);

        // Convert days to weeks
        return (int) Math.ceil(daysDifference / 7.0); // Round up to include partial weeks
    }

    @Test
    public void problem87() {
        /*
        Getting the number of weeks between two dates: Let’s assume that we have a date-time range given as two Date instances or as two LocalDateTime instances. Write an application that returns the number of weeks contained in this range. For the Date range, write a solution based on the Calendar API, while for the LocalDateTime range, write a solution based on the JDK 8 Date/Time API.
         */
        // Example range for Date (Calendar-based range)
        Calendar startDateCalendar = Calendar.getInstance();
        startDateCalendar.set(2023, Calendar.OCTOBER, 1); // Start: 1st October 2023
        Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar.set(2023, Calendar.NOVEMBER, 5); // End: 5th November 2023

        Date startDate = startDateCalendar.getTime();
        Date endDate = endDateCalendar.getTime();

        System.out.println("--- Using Calendar API (Date range) ---");
        int weeksInDateRange = calculateWeeksUsingCalendar(startDate, endDate);
        System.out.println("Number of weeks (Date): " + weeksInDateRange);

        // Example range for LocalDateTime (JDK 8+ Date/Time API range)
        LocalDateTime startDateTime = LocalDateTime.of(2023, 10, 1, 0, 0); // Start: 1st October 2023
        LocalDateTime endDateTime = LocalDateTime.of(2023, 11, 5, 0, 0); // End: 5th November 2023

        System.out.println("\n--- Using JDK 8 Date/Time API (LocalDateTime range) ---");
        int weeksInLocalDateTimeRange = calculateWeeksUsingDateTimeAPI(startDateTime, endDateTime);
        System.out.println("Number of weeks (LocalDateTime): " + weeksInLocalDateTimeRange);
    }
}
