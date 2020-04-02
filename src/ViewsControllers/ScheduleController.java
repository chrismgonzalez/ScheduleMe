package ViewsControllers;

import DAO.AppointmentDao;
import DAO.AppointmentDaoImp;
import DataModels.Appointment;
import Utils.UserSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ScheduleController extends InnerController{
    @FXML ChoiceBox<String> weekMonthChoiceBox;
    ObservableList<String> weekMonthChoices = FXCollections.observableArrayList("Month", "Week");

    @FXML ListView<Appointment> appointmentsListView;
    @FXML Button addAppointmentButton;
    @FXML Button viewAppointmentButton;

    @FXML AnchorPane calendarAnchorPane;
    private CalendarView calendar;
    private WeekView week;
    private boolean isMonth;

    public void initialize() {
        weekMonthChoiceBox.setItems(weekMonthChoices);
        weekMonthChoiceBox.setValue(weekMonthChoices.get(0));
        weekMonthChoiceBox.setOnAction(e -> toggleWeekMonth(weekMonthChoiceBox.getValue()));
        setUpCalendar();
        isMonth = true;
    }

    public void checkForUpcomingAppointments(Duration duration) {
        //check for upcoming appointments occuring within the next duration as stated in the argument to the method (e.g. 15 minutes, 30 minutes, etc)
        AppointmentDao appointmentDao = new AppointmentDaoImp();
        ObservableList<Appointment> upComingAppointments = appointmentDao.getAppointmentsStartingInInterval(
                LocalDateTime.now(),
                LocalDateTime.now().plus(duration),
                UserSettings.userId
        );
        if (upComingAppointments.size() > 0) {
            StringBuilder message = new StringBuilder("You have an appointment coming up.");
            upComingAppointments.forEach(a -> message.append("\n").append(a.toString()));
            NotificationWindow notificationWindow = new NotificationWindow(message.toString());
            notificationWindow.launch();
        }
    }

    public void addAppointment() {
        //todo
    }

    public void viewAppointment() {
        //todo
    }

    private void populateAppointmentsListView(LocalDate date) {
        //todo
    }

    private void toggleWeekMonth(String choice) {
        //todo
    }

    private void setUpCalendar() {
        //todo
    }

    private void populateCalendarDayVisualsAndBehaviors() {
        //todo
    }

    private void setUpWeek() {
        //todo
    }

    private void populateWeekDayVisualsAndBehaviors() {
        //todo
    }

    private void refresh() {

    }
}