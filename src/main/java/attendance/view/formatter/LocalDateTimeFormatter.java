package attendance.view.formatter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class LocalDateTimeFormatter {

    public static String LocalDateToDayOfWeekKorFull(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    // 12월 13일 금요일
    public static String formatCurrentDate(LocalDate currDate) {
        int month = currDate.getMonthValue();
        int day = currDate.getDayOfMonth();
        String dayOfWeekKor = LocalDateToDayOfWeekKorFull(currDate);

        return month + "월 " + day + "일 " + dayOfWeekKor;
    }

    // 12월 13일 금요일 09:59
    public static String localDateTimeToStringFormat(LocalDateTime dateTime) {
        String dow = LocalDateToDayOfWeekKorFull(dateTime.toLocalDate()); // 예: "월요일"

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "MM월 dd일 " + dow + " HH:mm",
                Locale.KOREAN
        );
        return dateTime.format(formatter);
    }

    public static String localDateTimeToNotExistsAttendanceResultFormat(LocalDate date) {
        String dow = LocalDateToDayOfWeekKorFull(date); // 예: "월요일"

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "MM월 dd일 " + dow + " --:--",
                Locale.KOREAN
        );
        return date.format(formatter);
    }

    public static String localDateTimeToHourMinuteFormat(LocalDateTime dateTime) {
        String dow = LocalDateToDayOfWeekKorFull(dateTime.toLocalDate()); // 예: "월요일"

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "HH:mm",
                Locale.KOREAN
        );
        return dateTime.format(formatter);
    }
}
