package attendance.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public Attendance findAttendanceByDateOrNull(LocalDate date, String crewName) {
        try {
            return attendanceList.stream()
                    .filter(attendance -> attendance.getAttendanceDateTime().toLocalDate().equals(date))
                    .filter(attendance -> attendance.getCrewName().equals(crewName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("해당 날짜에 해당 크루의 출석 기록이 없습니다."));

        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public void removeAttendance(Attendance oldAttendance) {
        this.attendanceList = attendanceList.stream()
                .filter(attendance -> !attendance.equals(oldAttendance))
                .collect(Collectors.toList());
    }

}
