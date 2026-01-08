package attendance.view;

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

    public void printNickNameInputForUpdatePrompt() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
    }

}
