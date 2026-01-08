package attendance.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public enum EducationTimeType {
    MON(LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUES_TO_FRI(LocalTime.of(10, 0), LocalTime.of(18, 0));

    private final LocalTime startAt;
    private final LocalTime endAt;

    EducationTimeType(LocalTime startAt, LocalTime endAt) {
        this.endAt = endAt;
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public LocalTime getEndAt() {
        return endAt;
    }

    public static EducationTimeType findEducationTimeTypeByLocalDateTime(LocalDateTime dateTime) {
        int dayOfWeekInt = dateTime.getDayOfWeek().getValue();
        if (dayOfWeekInt == 6 || dayOfWeekInt == 7) {
            throw new IllegalArgumentException("주말에는 배정된 교육시간이 없습니다.");
        }
        if (dayOfWeekInt == 1) {
            return MON;
        }
        return TUES_TO_FRI;
    }
}
