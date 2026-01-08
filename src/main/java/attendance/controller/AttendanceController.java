package attendance.controller;

import attendance.domain.AttendanceCatalog;
import attendance.domain.Crews;
import attendance.domain.Menu;
import attendance.io.FileReader;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class AttendanceController {

    private final InputHandler inputHandler;
    private final OutputView outputView;

    public AttendanceController(InputHandler inputHandler, OutputView outputView) {
        this.inputHandler = inputHandler;
        this.outputView = outputView;
    }

    public void process() {
        FileReader fileReader = new FileReader();
        AttendanceCatalog attendanceCatalog = fileReader.makeAttendanceCatalog();
        Crews crews = fileReader.makeCrews();

        Menu menu;
        do {
            LocalDate currDate = DateTimes.now().toLocalDate();
            outputView.printHelloAndMenu(currDate);
            menu = inputHandler.inputMenu(currDate);
            run(menu, crews, attendanceCatalog, currDate);
        } while (menu != Menu.QUIT);
    }

    private void run(Menu menu, Crews crews, AttendanceCatalog attendanceCatalog, LocalDate currDate) {
        if (menu == Menu.ATTENDANCE_INSERT) {
            runAttendanceInsert(crews, attendanceCatalog, currDate);
        }
        if (menu == Menu.ATTENDANCE_UPDATE) {
            // runAttendanceUpdate();
        }
        if (menu == Menu.CREW_ATTENDANCE_HISTORY) {
            // runCrewAttendanceHistory();
        }
        if (menu == Menu.EXPULSION_DANGERED_CREWS) {
            // runExpulsionDangeredCrews();
        }
    }

    private void runAttendanceInsert(Crews crews, AttendanceCatalog attendanceCatalog, LocalDate currDate) {
        outputView.printNickNameInputPrompt();
        String crewName = inputHandler.inputNickName(crews, attendanceCatalog, currDate);

    }

}
