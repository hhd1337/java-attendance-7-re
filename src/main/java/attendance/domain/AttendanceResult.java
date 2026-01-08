package attendance.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceResult {
    SUCCESS, LATE, ABSENT;

    // LocalDateTime을 주면 SUCCESS, LATE, ABSENT 반환하는 함수
    public static AttendanceResult judgeAttendanceResult(LocalDateTime attendedDateTime) {
        EducationTimeType dateType = EducationTimeType.findEducationTimeTypeByLocalDateTime(attendedDateTime);
        LocalTime startAt = dateType.getStartAt();
        LocalTime attendedTime = attendedDateTime.toLocalTime();

        if (attendedTime.isAfter(startAt.plusMinutes(30))) {
            return ABSENT;
        }
        if (attendedTime.isAfter(startAt.plusMinutes(5))) {
            return LATE;
        }
        return SUCCESS;
    }
}
