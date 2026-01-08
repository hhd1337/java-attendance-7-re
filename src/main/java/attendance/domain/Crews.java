package attendance.domain;

import java.util.List;

public class Crews {
    private List<String> crewList;

    public Crews(List<String> crewList) {
        this.crewList = crewList;
    }

    public boolean isExists(String name) {
        return crewList.stream().anyMatch(crewName -> crewName.equals(name));
    }

    public void addCrew(String crew) {
        crewList.add(crew);
    }
}
