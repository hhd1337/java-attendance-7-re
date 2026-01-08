package attendance.domain;

import java.util.Arrays;

public enum Menu {
    ATTENDANCE_INSERT("1"),
    ATTENDANCE_UPDATE("2"),
    CREW_ATTENDANCE_HISTORY("3"),
    EXPULSION_DANGERED_CREWS("4"),
    QUIT("Q");

    private final String symbol;

    Menu(String symbol) {
        this.symbol = symbol;
    }

    public static Menu findBySymbol(String symbol) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.symbol.equals(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 형식을 입력하였습니다."));
    }

    public String getSymbol() {
        return this.symbol;
    }
}
