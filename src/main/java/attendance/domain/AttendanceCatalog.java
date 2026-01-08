package attendance.domain;

import java.time.LocalDate;
import java.util.List;

public class AttendanceCatalog {
    private List<Attendance> attendanceList;

    public AttendanceCatalog(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public boolean isCrewAttendedToday(String crewName, LocalDate currDate) {
        return attendanceList.stream()
                .anyMatch(attendance ->
                        attendance.getCrewName().equals(crewName) &&
                                attendance.getAttendanceDateTime().toLocalDate().equals(currDate));
    }

    public void addAttendance(Attendance attendance) {
        attendanceList.add(attendance);
    }

}
