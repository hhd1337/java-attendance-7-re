package attendance.controller;

import attendance.controller.dto.AttendanceDto;
import attendance.converter.StringToLocalDateTimeConverter;
import attendance.converter.StringToMenuConverter;
import attendance.domain.Attendance;
import attendance.domain.AttendanceCatalog;
import attendance.domain.AttendanceResult;
import attendance.domain.Crews;
import attendance.domain.Holiday;
import attendance.domain.Menu;
import attendance.view.InputView;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class InputHandler {

    private final InputView inputView;
    private final IteratorInputTemplate inputTemplate;

    public InputHandler(InputView inputView, IteratorInputTemplate iteratorInputTemplate) {
        this.inputView = inputView;
        this.inputTemplate = iteratorInputTemplate;
    }

    // Menu 메서드
    public Menu inputMenu(LocalDate currDate) {
        StringToMenuConverter converter = new StringToMenuConverter();
        return inputTemplate.execute(
                inputView::inputMenu,
                value -> {
                    value = value.trim();
                    // - 예외) 주말 또는 공휴일에 출석을 확인하거나 수정하는 경우
                    if (value.equals(Menu.ATTENDANCE_INSERT.getSymbol())
                            || value.equals(Menu.ATTENDANCE_UPDATE.getSymbol())) {
                        validateCurrdate(currDate);
                    }
                    return converter.convert(value);
                }
        );
    }

    public String inputNickName(Crews crews, AttendanceCatalog attendanceCatalog, LocalDate currDate) {
        return inputTemplate.execute(
                inputView::inputNickName,
                value -> {
                    value = value.trim();
                    if (!crews.isExists(value)) {
                        throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
                    }
                    if (attendanceCatalog.isCrewAttendedToday(value, currDate)) {
                        throw new IllegalArgumentException("이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
                    }
                    return value;
                }
        );
    }

    public String inputNickNameForUpdate(Crews crews) {
        return inputTemplate.execute(
                inputView::inputNickNameForUpdate,
                value -> {
                    value = value.trim();
                    if (!crews.isExists(value)) {
                        throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
                    }
                    return value;
                }
        );
    }

    public Attendance inputAttendTime(String crewName, AttendanceCatalog attendanceCatalog, LocalDate currDate) {
        StringToLocalDateTimeConverter converter = new StringToLocalDateTimeConverter();
        return inputTemplate.execute(
                inputView::inputAttendTime,
                value -> {
                    value = value.trim();
                    LocalTime attendTime = converter.convertToTimeMinute(value);
                    LocalDateTime attendDateTime = LocalDateTime.of(currDate, attendTime);
                    AttendanceResult attendanceResult = AttendanceResult.judgeAttendanceResult(attendDateTime);

                    Attendance attendance = new Attendance(crewName, attendDateTime, attendanceResult);
                    attendanceCatalog.addAttendance(attendance);

                    return attendance;
                }
        );
    }

    public LocalDate inputUpdateDayOfMonth(LocalDate currDate) {
        StringToLocalDateTimeConverter converter = new StringToLocalDateTimeConverter();

        return inputTemplate.execute(
                inputView::inputUpdateDayOfMonth,
                value -> {
                    value = value.trim(); // 3
                    LocalDate updateDate = converter.convertToLocalDate(value);
                    if (updateDate.isAfter(currDate)) {
                        throw new IllegalArgumentException("아직 수정할 수 없습니다.");
                    }

                    return updateDate;
                }
        );
    }

    public AttendanceDto inputUpdateTime(String crewName, AttendanceCatalog attendanceCatalog, LocalDate updateDate) {
        StringToLocalDateTimeConverter converter = new StringToLocalDateTimeConverter();

        return inputTemplate.execute(
                inputView::inputUpdateTime,
                value -> {
                    value = value.trim(); // 09:58
                    LocalTime updateTime = converter.convertToTimeMinute(value);

                    Attendance oldAttendance = attendanceCatalog.findAttendanceByDate(updateDate, crewName);

                    LocalDateTime updateDateTime = LocalDateTime.of(updateDate, updateTime);
                    AttendanceResult attendanceResult = AttendanceResult.judgeAttendanceResult(updateDateTime);

                    Attendance newAttendance = new Attendance(crewName, updateDateTime, attendanceResult);

                    attendanceCatalog.addAttendance(newAttendance);

                    // oldAttendance 삭제하는 로직 추가
                    attendanceCatalog.removeAttendance(oldAttendance);

                    return new AttendanceDto(oldAttendance, newAttendance);
                }
        );
    }

    private void validateCurrdate(LocalDate currDate) {
        // 주말 또는 공휴일에 출석을 확인하거나 수정하는 경우
        DayOfWeek dayOfWeek = currDate.getDayOfWeek();
        if (dayOfWeek.getValue() == 6 || dayOfWeek.getValue() == 7 || Holiday.isHoliday(currDate)) {
            String dayOfWeekKor = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
            throw new IllegalArgumentException(
                    currDate.getMonthValue() + "월 " + currDate.getDayOfMonth() + "일 " + dayOfWeekKor + "은 등교일이 아닙니다.");
        }
    }

}