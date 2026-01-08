package attendance.domain;

import java.time.LocalDateTime;

public class Attendance {
    private String crewName;
    private LocalDateTime attendanceDateTime;
    private AttendanceResult attendanceResult;

    public Attendance(String crewName, LocalDateTime attendanceDateTime, AttendanceResult attendanceResult) {
        this.crewName = crewName;
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceResult = attendanceResult;
    }

    public String getCrewName() {
        return crewName;
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public AttendanceResult getAttendanceResult() {
        return attendanceResult;
    }
}
