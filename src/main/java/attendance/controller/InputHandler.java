package attendance.controller;

import attendance.converter.StringToMenuConverter;
import attendance.domain.Holiday;
import attendance.domain.Menu;
import attendance.view.InputView;
import java.time.DayOfWeek;
import java.time.LocalDate;
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