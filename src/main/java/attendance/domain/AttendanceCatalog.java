package attendance.domain;

import java.util.List;

public class AttendanceCatalog {
    private List<Attendance> attendanceList;

    public AttendanceCatalog(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }
}
