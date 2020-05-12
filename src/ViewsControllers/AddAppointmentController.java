package ViewsControllers;

import DAO.*;
import DataModels.*;
import Utils.InputValidator;
import Utils.UserSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;
import javafx.stage.Window;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class AddAppointmentController {

    @FXML Label userNameLabel;
    @FXML Label customerNameLabel;
    @FXML ChoiceBox<Customer> customerChoiceBox;
    @FXML TextField appointmentTitleTextField;
    @FXML TextField appointmentLocationTextField;
    @FXML TextField appointmentContactTextField;
    @FXML TextField appointmentUrlTextField;
    @FXML TextField appointmentTypeTextField;
    @FXML TextArea appointmentDescriptionTextArea;

    @FXML DatePicker datePicker;
    @FXML TextField startHourTextField;
    @FXML TextField startMinuteTextField;
    @FXML ChoiceBox<String> startAmPmChoiceBox;
    @FXML TextField endHourTextField;
    @FXML TextField endMinuteTextField;
    @FXML ChoiceBox<String> endAmPmChoiceBox;
    private final ObservableList<String> amPmOptions = FXCollections.observableArrayList("AM", "PM");

    @FXML Button addNewAppointmentButton;
    @FXML Button cancelNewAppointmentButton;

    private Customer customer;
    private Appointment addedAppointment;

    public Appointment getAddedAppointment() {
        return addedAppointment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void initialize() {
        userNameLabel.setText(UserSettings.userName);
        startAmPmChoiceBox.setItems(amPmOptions);
        startAmPmChoiceBox.setValue(amPmOptions.get(0));
        endAmPmChoiceBox.setItems(amPmOptions);
        endAmPmChoiceBox.setValue(amPmOptions.get(0));
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customerNameLabel.setText(customer.getCustomerName());
        if (!customerChoiceBox.isDisabled()) {
            customerChoiceBox.setValue(customer);
        }
    }

    public void cancelNewAppointment() {
        cancelNewAppointmentButton.getScene().getWindow().hide();
    }

    public void addNewAppointment () {
        String title = appointmentTitleTextField.getText();
        String location = appointmentLocationTextField.getText();
        String contact = appointmentContactTextField.getText();
        String url = appointmentUrlTextField.getText();
        String type = appointmentTypeTextField.getText();
        String description = appointmentDescriptionTextArea.getText();

        // if customerChoiceBox is disabled, the AddAppointmentView was launched from the customer page
        // otherwise, the AddAppointmentView was launched from the schedule screen and the customer is selected in the choice box
        if (!customerChoiceBox.isDisabled()) {
            if (customerChoiceBox.getValue() == null) {
                NotificationWindow notificationWindow = new NotificationWindow("Select a customer from the drop down menu.");
                notificationWindow.launchAndWait();
                return;
            }
            this.setCustomer(customerChoiceBox.getValue());
        }

        //date and time
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;

        try {
            String validStartHour = InputValidator.validateHourInput(startHourTextField.getText());
            String validStartMinute = InputValidator.validateMinuteInput(startMinuteTextField.getText());
            String validEndHour = InputValidator.validateHourInput(endHourTextField.getText());
            String validEndMinute = InputValidator.validateMinuteInput(endMinuteTextField.getText());
            startDateTime = createDateTime(datePicker.getValue(), validStartHour, validStartMinute, startAmPmChoiceBox.getValue());
            endDateTime = createDateTime(datePicker.getValue(), validEndHour, validEndMinute, endAmPmChoiceBox.getValue());
            InputValidator.validateAppointmentDateTimeLogic(startDateTime, endDateTime);
        } catch (IllegalArgumentException e) {
            NotificationWindow notificationWindow = new NotificationWindow(e.getMessage());
            notificationWindow.launchAndWait();
            return;
        }

        AppointmentDao appointmentDao = new AppointmentDaoImp();
        addedAppointment = appointmentDao.addAppointment(customer.getCustomerId(), UserSettings.userId, title, description, location, contact, type, url, startDateTime, endDateTime);

        // return to main ui by firing close request event
        Window window = addNewAppointmentButton.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private LocalDateTime createDateTime(LocalDate date, String hour, String minute, String amPmChoice) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime time = LocalTime.parse(hour + ":" + minute + " " + amPmChoice, timeFormatter);
        return date.atTime(time);
    }

    public void toggleCustomerChoiceBox() {
        customerNameLabel.setDisable(true);
        customerNameLabel.setVisible(false);
        customerChoiceBox.setDisable(false);
        customerChoiceBox.setVisible(true);
        CustomerDao customerDao = new CustomerDaoImp();
        ObservableList<Customer> allCustomers = customerDao.getAllCustomers();
        customerChoiceBox.setItems(allCustomers);
    }

}
