package attendance.view;

import static attendance.domain.AttendanceResult.ABSENT;
import static attendance.domain.AttendanceResult.LATE;
import static attendance.domain.AttendanceResult.SUCCESS;

import attendance.controller.dto.AttendanceDto;
import attendance.domain.Attendance;
import attendance.domain.AttendanceCatalog;
import attendance.domain.AttendanceResult;
import attendance.domain.CrewStatus;
import attendance.domain.Holiday;
import attendance.util.ErrorMessage;
import attendance.view.formatter.LocalDateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OutputView {

    public void printErrorMessage(Exception exception) {
        System.out.println(ErrorMessage.PREFIX + exception.getMessage());
    }

    public void printHelloAndMenu(LocalDate currDate) {
        String formattedDate = LocalDateTimeFormatter.formatCurrentDate(currDate);
        System.out.println("오늘은 " + formattedDate + "입니다. 기능을 선택해 주세요.");
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
    }

    public void printNickNameInputPrompt() {
        System.out.println("닉네임을 입력해 주세요.");
    }

    public void printAttendTimeInputPrompt() {
        System.out.println("등교 시간을 입력해 주세요.");
    }

    // 12월 13일 금요일 09:59 (출석)
    public void printAttendInsertResult(LocalDateTime attendTime, String AttendanceResultKor) {
        String dateTimeString = LocalDateTimeFormatter.localDateTimeToStringFormat(attendTime);
        System.out.println(dateTimeString + " (" + AttendanceResultKor + ")");
    }

    public void printNotExistAttendanceResult(LocalDate date) {
        String dateString = LocalDateTimeFormatter.localDateTimeToNotExistsAttendanceResultFormat(date);
        System.out.println(dateString + " (결석)");
    }

    public void printNickNameInputForUpdatePrompt() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
    }

    public void printUpdateDateInputPrompt() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
    }

    public void printUpdateTimeInputPrompt() {
        System.out.println("언제로 변경하겠습니까?");
    }

    public void printUpdateTimeInputPrompt(AttendanceDto dto) {
        LocalDateTime oldDateTime = dto.getOldAttendance().getAttendanceDateTime();
        String oldAttendanceResult = dto.getOldAttendance().getAttendanceResult().getAttendanceResultKor();

        LocalDateTime newDateTime = dto.getNewAttendance().getAttendanceDateTime();
        String newAttendanceResult = dto.getNewAttendance().getAttendanceResult().getAttendanceResultKor();

        String dateTimeString = LocalDateTimeFormatter.localDateTimeToStringFormat(oldDateTime);
        String newDateTimeString = LocalDateTimeFormatter.localDateTimeToHourMinuteFormat(newDateTime);

        System.out.println(
                dateTimeString + " (" + oldAttendanceResult + ") -> " + newDateTimeString + " (" + newAttendanceResult
                        + ") 수정 완료!");
    }

    public void printCrewAttendHistory(LocalDate currDate, AttendanceCatalog catalog, String crewName) {
        LocalDate date = currDate.withDayOfMonth(1);
        int absentCount = 0;
        int lateCount = 0;
        int successCount = 0;

        while (!date.isAfter(currDate.minusDays(1))) {
            if (date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7 || Holiday.isHoliday(date)) {
                date = date.plusDays(1);
                continue;
            }
            Attendance attendance = catalog.findAttendanceByDateOrNull(date, crewName);
            if (attendance == null) {
                printNotExistAttendanceResult(date);
                absentCount++;
            }
            if (attendance != null) {
                AttendanceResult attendanceResult = attendance.getAttendanceResult();

                printAttendInsertResult(attendance.getAttendanceDateTime(),
                        attendanceResult.getAttendanceResultKor());

                if (attendanceResult.equals(ABSENT)) {
                    absentCount++;
                }
                if (attendanceResult.equals(LATE)) {
                    lateCount++;
                }
                if (attendanceResult.equals(SUCCESS)) {
                    successCount++;
                }
            }
            date = date.plusDays(1);
        }

        System.out.printf("출석: %d회%n", successCount);
        System.out.printf("지각: %d회%n", lateCount);
        System.out.printf("결석: %d회%n", absentCount);
        absentCount = absentCount + (lateCount / 3);

        String crewStatus = CrewStatus.judgeCrewStatus(absentCount);
        if (crewStatus.equals(CrewStatus.GOOD.getCrewStatusKor())) {
            return;
        }
        System.out.printf("%s 대상자입니다.%n", crewStatus);
    }


}
