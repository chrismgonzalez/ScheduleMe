package ViewsControllers;

import DAO.AppointmentDao;
import DAO.AppointmentDaoImp;
import DAO.CustomerDao;
import DAO.CustomerDaoImp;
import DataModels.Appointment;
import DataModels.Customer;
import Utils.UserSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScheduleController extends InnerController {

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
    // checks for appointments that start within the next 15 minutes of login
    public void checkForUpcomingAppointments(Duration duration) {

        AppointmentDao appointmentDao = new AppointmentDaoImp();
        ObservableList<Appointment> upcomingAppointments = appointmentDao.getAppointmentsStartingInInterval(
                LocalDateTime.now(),
                LocalDateTime.now().plus(duration),
                UserSettings.userId
        );
        if (upcomingAppointments.size() > 0) {
            StringBuilder message = new StringBuilder("You have an appointment starting soon.");
            upcomingAppointments.forEach(a -> message.append("\n").append(a.toString()));
            NotificationWindow notificationWindow = new NotificationWindow(message.toString());
            notificationWindow.launch();
        }
    }
//add an appointment
    public void addAppointment() {
        Parent root;
        AddAppointmentController controller;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAppointmentView.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            Appointment newAppointment = controller.getAddedAppointment();
            if (newAppointment != null) {
                appointmentsListView.getItems().add(newAppointment);
                Collections.sort(appointmentsListView.getItems());
                refresh();
            }
        });
        stage.show();
        controller.toggleCustomerChoiceBox();
    }

    public void viewAppointment() {
        Appointment selectedAppointment = appointmentsListView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            return;
        }
        Parent root;
        EditAppointmentController controller;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAppointmentView.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            populateAppointmentsListView(selectedAppointment.getStart().toLocalDate());
            refresh();
        });
        stage.show();
        controller.setAppointment(selectedAppointment);
        CustomerDao customerDao = new CustomerDaoImp();
        Customer customer = customerDao.getCustomerById(selectedAppointment.getCustomerId());
        controller.setCustomer(customer);
    }

    private void populateAppointmentsListView(LocalDate date) {
        AppointmentDao appointmentDao = new AppointmentDaoImp();
        ObservableList<Appointment> selectedAppointments = appointmentDao.getAppointmentsStartingInInterval(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), UserSettings.userId);
        Collections.sort(selectedAppointments);
        appointmentsListView.setItems(selectedAppointments);
    }
//toggle the functionality between a week view and a month view
    private void toggleWeekMonth(String choice) {
        if (choice.equals(weekMonthChoices.get(0)) && !isMonth) {
            calendarAnchorPane.getChildren().remove(week);
            setUpCalendar();
            isMonth = !isMonth;
        } else if (choice.equals(weekMonthChoices.get(1)) && isMonth) {
            calendarAnchorPane.getChildren().remove(calendar);
            setUpWeek();
            isMonth = !isMonth;
        }
    }

    private void setUpCalendar() {
        calendar = new CalendarView();
        calendarAnchorPane.getChildren().add(calendar);
        calendar.getPrevious().setOnAction(e -> {
            calendar.previousMonth();
            populateCalendarDayVisualsAndBehaviors();
        });
        calendar.getNext().setOnAction(e -> {
            calendar.nextMonth();
            populateCalendarDayVisualsAndBehaviors();
        });
        populateCalendarDayVisualsAndBehaviors();
    }

    private void populateCalendarDayVisualsAndBehaviors() {
        AppointmentDao appointmentDao = new AppointmentDaoImp();
        LocalDate start = calendar.getMonth().atDay(1);
        while (start.getDayOfWeek() != DayOfWeek.SUNDAY) {
            start = start.minusDays(1);
        }
        ObservableList<Appointment> appointments = appointmentDao.getAppointmentsStartingInInterval(start.atStartOfDay(), start.plusDays(35).atStartOfDay(), UserSettings.userId);
        Map<LocalDate, Long> count = appointments.stream().collect(Collectors.groupingBy(a -> a.getStart().toLocalDate(), Collectors.counting()));
        for (CalendarDay day : calendar.getCalendarDays()) {
            // set on-click behavior
            if (count.getOrDefault(day.getDate(), 0L) > 0 && day.getDate().getMonth().equals(calendar.getMonth().getMonth())) {
                day.setStyle("-fx-background-color: #ebae66; -fx-border-color: grey; -fx-border-width: 0.5");
                day.setOnMousePressed(e -> {
                    day.setStyle("-fx-background-color: black; -fx-border-color: black; -fx-border-width: 0.5");
                    populateAppointmentsListView(day.getDate());
                });
            } else {
                day.setOnMousePressed(e -> day.setStyle("-fx-background-color: black; -fx-border-color: black; -fx-border-width: 0.5"));
            }
            String originalStyle = day.getStyle();
            day.setOnMouseReleased(e -> day.setStyle(originalStyle));
        }
    }

    private void setUpWeek() {
        week = new WeekView();
        calendarAnchorPane.getChildren().add(week);
        week.getPrevious().setOnAction(e -> {
            week.previousWeek();
            populateWeekDayVisualsAndBehaviors();
        });
        week.getNext().setOnAction(e -> {
            week.nextWeek();
            populateWeekDayVisualsAndBehaviors();
        });
        populateWeekDayVisualsAndBehaviors();
    }

    private void populateWeekDayVisualsAndBehaviors() {
        AppointmentDao appointmentDao = new AppointmentDaoImp();
        LocalDate start = week.getFirstDayOfWeek();
        ObservableList<Appointment> appointments = appointmentDao.getAppointmentsStartingInInterval(start.atStartOfDay(), start.plusWeeks(1).atStartOfDay(), UserSettings.userId);
        for (WeekDay day : week.getWeekDays()) {
            // set appointment visuals
            List<Appointment> appointmentsOnDay = appointments.stream().filter(a -> a.getStart().toLocalDate().equals(day.getDate())).collect(Collectors.toList());
            day.setAppointments(appointmentsOnDay);
            // set on-click behavior
            if (appointmentsOnDay.size() > 0) {
                day.setOnMousePressed(e -> {
                    day.setStyle("-fx-background-color: black; -fx-border-color: black; -fx-border-width: 0.5");
                    populateAppointmentsListView(day.getDate());
                });
            } else {
                day.setOnMousePressed(e -> day.setStyle("-fx-background-color: black; -fx-border-color: black; -fx-border-width: 0.5"));
            }
            String originalStyle = day.getStyle();
            day.setOnMouseReleased(e -> day.setStyle(originalStyle));
        }
    }

    private void refresh() {
        if (isMonth) {
            calendarAnchorPane.getChildren().remove(calendar);
            setUpCalendar();
        } else {
            calendarAnchorPane.getChildren().remove(week);
            setUpWeek();
        }
    }
}