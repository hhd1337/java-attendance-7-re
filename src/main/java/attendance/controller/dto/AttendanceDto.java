package attendance.controller.dto;

import attendance.domain.Attendance;

public class AttendanceDto {
    private Attendance oldAttendance;
    private Attendance newAttendance;

    public AttendanceDto(Attendance oldAttendance, Attendance newAttendance) {
        this.oldAttendance = oldAttendance;
        this.newAttendance = newAttendance;
    }

    public Attendance getOldAttendance() {
        return oldAttendance;
    }

    public Attendance getNewAttendance() {
        return newAttendance;
    }
}
