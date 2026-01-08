package attendance.io;

import attendance.converter.StringToLocalDateTimeConverter;
import attendance.domain.Attendance;
import attendance.domain.AttendanceCatalog;
import attendance.domain.AttendanceResult;
import attendance.domain.Crews;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {
    private static final String FILE_NAME = "attendances.csv";
    private static final String DELIMITER = ",";

    public AttendanceCatalog makeAttendanceCatalog() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME);
        StringToLocalDateTimeConverter converter = new StringToLocalDateTimeConverter();
        if (inputStream == null) {
            throw new IllegalArgumentException(FILE_NAME + "을 classpath에서 찾을 수 없습니다.");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<Attendance> attendanceList = reader.lines()
                    .skip(1)
                    .filter(line -> !line.isBlank())
                    .map(line -> line.split(DELIMITER))
                    .map(splitedLine -> {
                        String crewName = splitedLine[0].trim();
                        LocalDateTime attendanceDateTime = converter.convert(splitedLine[1].trim());
                        AttendanceResult attendanceResult = AttendanceResult.judgeAttendanceResult(attendanceDateTime);

                        return new Attendance(crewName, attendanceDateTime, attendanceResult);
                    })
                    .collect(Collectors.toList());

            return new AttendanceCatalog(attendanceList);
        } catch (IOException e) {
            throw new IllegalArgumentException(FILE_NAME + "파일을 읽는 과정에서 문제가 발생했습니다.");
        }
    }

    public Crews makeCrews() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME);

        if (inputStream == null) {
            throw new IllegalArgumentException(FILE_NAME + "을 classpath에서 찾을 수 없습니다.");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            Crews crews = new Crews(new ArrayList<>());

            reader.lines()
                    .skip(1)
                    .filter(line -> !line.isBlank())
                    .map(line -> line.split(DELIMITER))
                    .map(splitedLine -> splitedLine[0].trim())
                    .forEach(name -> {
                        if (!crews.isExists(name)) {
                            crews.addCrew(name);
                        }
                    });

            return crews;
        } catch (IOException e) {
            throw new IllegalArgumentException(FILE_NAME + "파일을 읽는 과정에서 문제가 발생했습니다.");
        }
    }
}
