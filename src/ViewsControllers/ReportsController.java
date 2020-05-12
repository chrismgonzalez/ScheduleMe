package ViewsControllers;

import DAO.AppointmentDao;
import DAO.AppointmentDaoImp;
import DAO.UserDao;
import DAO.UserDaoImpl;
import DataModels.Appointment;
import DataModels.Report;
import DataModels.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.Month;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class ReportsController extends InnerController {

    @FXML ToggleGroup reportOptions;
    @FXML RadioButton appointmentCountByTypeReportRb;
    @FXML RadioButton scheduleReportRb;
    @FXML RadioButton appointmentUniqueTypesReportRb;
    @FXML TableView<List<Object>> reportTable;
    @FXML ListView<User> userListView;
    @FXML ListView<Month> monthListView;
    @FXML ListView<Integer> yearListView;
    @FXML Button generateReportButton;

    @FXML private ObservableList<Appointment> appointments;

    public void initialize() {
        // set up report filters
        userListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        monthListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        yearListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        populateReportFilterListViews();
        // cache data
        AppointmentDao appointmentDao = new AppointmentDaoImp();
        appointments = appointmentDao.getAllAppointments();
        Collections.sort(appointments);
    }

    public void generate() {
        reportTable.getColumns().clear();
        reportTable.getItems().clear();
        Report report;
        if (reportOptions.getSelectedToggle().equals(scheduleReportRb)) {
            report = consultantScheduleReport();
        } else if (reportOptions.getSelectedToggle().equals(appointmentUniqueTypesReportRb)) {
            report = appointmentUniqueTypesReport();
        } else {
            report = appointmentCountByTypeReport();
        }
        // populate table
        for (int j = 0 ; j < report.getNumColumns() ; j++) {
            TableColumn<List<Object>, Object> column = new TableColumn<>(report.getColumnName(j));
            int columnIndex = j;
            column.setCellValueFactory(rowWrapper -> new SimpleObjectProperty<>(rowWrapper.getValue().get(columnIndex)));
            reportTable.getColumns().add(column);
        }
        reportTable.getItems().setAll(report.getData());
    }

    private void populateReportFilterListViews() {
        // user selection
        UserDao userDao = new UserDaoImpl();
        ObservableList<User> userList = userDao.getAllActiveUsers();
        userListView.setItems(userList);
        // month selection
        ObservableList<Month> monthList = FXCollections.observableArrayList(Month.values());
        monthListView.setItems(monthList);
        // year selection
        ObservableList<Integer> yearList = FXCollections.observableArrayList(2020, 2021, 2022, 2023, 2024);
        yearListView.setItems(yearList);
    }

    private List<Appointment> filterAppointments() {
        // get lists of included items
        ObservableList<User> selectedUsers = userListView.getSelectionModel().getSelectedItems();
        Map<Integer, String> selectedUsersIdsNames = new HashMap<>();
        selectedUsers.forEach(u -> selectedUsersIdsNames.put(u.getUserId(), u.getUserName()));
        ObservableList<Month> selectedMonths = monthListView.getSelectionModel().getSelectedItems();
        ObservableList<Integer> selectedYears = yearListView.getSelectionModel().getSelectedItems();

        //if the data grew significantly, we would want to add a filter to make the data easier to search, but that is not needed at this point in the program
        return appointments.stream()
                .filter(a -> selectedUsersIdsNames.containsKey(a.getUserId()))
                .filter(a -> selectedMonths.contains(a.getStart().getMonth()))
                .filter(a -> selectedYears.contains(a.getStart().getYear()))
                .collect(Collectors.toList());
    }

    private Report consultantScheduleReport() {
        List<Appointment> filteredAppointments = filterAppointments();
        // get lists of included users
        ObservableList<User> selectedUsers = userListView.getSelectionModel().getSelectedItems();
        Map<Integer, String> selectedUsersIdsNames = new HashMap<>();
        selectedUsers.forEach(u -> selectedUsersIdsNames.put(u.getUserId(), u.getUserName()));
        // Build Report
        List<List<Object>> reportData = new ArrayList<>();
        List<String> columnNames = Arrays.asList("User", "Appointment Type", "Start", "End", "Location", "Title");
        for (Appointment filteredAppointment : filteredAppointments) {
            List<Object> row = new ArrayList<>();
            row.add(selectedUsersIdsNames.get(filteredAppointment.getUserId()));
            row.add(filteredAppointment.getType());
            row.add(filteredAppointment.getStart());
            row.add(filteredAppointment.getEnd());
            row.add(filteredAppointment.getLocation());
            row.add(filteredAppointment.getTitle());
            reportData.add(row);
        }
        return new Report(columnNames, reportData);
    }

    private Report appointmentUniqueTypesReport() {
        List<Appointment> filteredAppointments = filterAppointments();
        // calculate number of appointment types
        Map<YearMonth, Set<String>> appointmentsByMonth = filteredAppointments.stream()
                .collect(Collectors.groupingBy(
                        a -> YearMonth.of(a.getStart().getYear(), a.getStart().getMonth()),
                        Collectors.mapping(
                                Appointment::getType,
                                Collectors.toSet())));

        Map<YearMonth, Integer> appointmentTypeCounts = new HashMap<>();
        appointmentsByMonth.forEach((k, v) -> appointmentTypeCounts.put(k, v.size()));
        // Build Report
        List<List<Object>> reportData = new ArrayList<>();
        List<String> columnNames = Arrays.asList("Year", "Month", "Unique Appointment Types");
        for (YearMonth yearMonth : appointmentTypeCounts.keySet()) {
            List<Object> row = new ArrayList<>();
            row.add(yearMonth.getYear());
            row.add(yearMonth.getMonth());
            row.add(appointmentTypeCounts.get(yearMonth));
            reportData.add(row);
        }
        return new Report(columnNames, reportData);
    }

    private Report appointmentCountByTypeReport() {
        List<Appointment> filteredAppointments = filterAppointments();
        // calculate number of appointment types
        Map<String, Long> appointmentTypeCounts = filteredAppointments.stream().collect(Collectors.groupingBy(Appointment::getType, Collectors.counting()));
        // Build Report
        List<List<Object>> reportData = new ArrayList<>();
        List<String> columnNames = Arrays.asList("Appointment Type", "Count");
        for (String type : appointmentTypeCounts.keySet()) {
            List<Object> row = new ArrayList<>();
            row.add(type);
            row.add(appointmentTypeCounts.get(type));
            reportData.add(row);
        }
        return new Report(columnNames, reportData);
    }

}