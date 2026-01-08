package attendance.view.formatter;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
}
