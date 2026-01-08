package attendance.domain;

public enum CrewStatus {
    WARN("경고"),
    INTERVIEW("면담"),
    WEED("제적"),
    GOOD("");

    private final String crewStatusKor;

    CrewStatus(String crewStatusKor) {
        this.crewStatusKor = crewStatusKor;
    }

    public String getCrewStatusKor() {
        return crewStatusKor;
    }

    public static String judgeCrewStatus(int calculatedAbsenceCount) {
        if (calculatedAbsenceCount > 5) {
            return WEED.crewStatusKor;
        }
        if (calculatedAbsenceCount >= 3) {
            return INTERVIEW.crewStatusKor;
        }
        if (calculatedAbsenceCount >= 2) {
            return WARN.crewStatusKor;
        }
        return GOOD.crewStatusKor;
    }

}
