package attendance.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringToLocalDateTimeConverter {
    private static final DateTimeFormatter MINUTE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter TIME_MINUTE_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public LocalDateTime convertToMinute(String value) {
        String trimmed = validateAndTrim(value);
        try {
            return LocalDateTime.parse(trimmed, MINUTE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜/시간(분) 형식이 올바르지 않습니다. 예) 2025-01-10 10:06");
        }
    }

    public LocalTime convertToTimeMinute(String value) {
        String trimmed = validateAndTrim(value);
        try {
            return LocalTime.parse(trimmed, TIME_MINUTE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
        }
    }

    // 3 입력 받아서 LocalDate 반환
    public LocalDate convertToLocalDate(String value) {
        String trimmed = validateAndTrim(value);

        try {
            int dayOfMonth = Integer.parseInt(trimmed);
            return LocalDate.of(2024, 12, dayOfMonth);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
        }
    }


    private String validateAndTrim(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("날짜/시간 입력이 비어있습니다.");
        }
        return value.trim();
    }
}
