package ViewsControllers;

import DAO.AppointmentDao;
import DAO.AppointmentDaoImp;
import DataModels.Appointment;
import DataModels.Customer;
import Utils.InputValidator;
import Utils.UserSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class EditAppointmentController {
    @FXML Label userNameLabel;
    @FXML Label customerNameLabel;
    @FXML TextField appointmentTitleTextField;
    @FXML TextField appointmentLocationTextField;
    @FXML TextField appointmentContactTextField;
    @FXML TextField appointmentUrlTextField;
    @FXML TextField appointmentTypeTextField;
    @FXML TextArea  appointmentDescriptionTextArea;

    @FXML DatePicker datePicker;
    @FXML TextField startHourTextField;
    @FXML TextField startMinuteTextField;
    @FXML ChoiceBox<String> startAmPmChoiceBox;
    @FXML TextField endHourTextField;
    @FXML TextField endMinuteTextField;
    @FXML ChoiceBox<String> endAmPmChoiceBox;

    private final ObservableList<String> amPmOptions = FXCollections.observableArrayList("AM", "PM");

    @FXML Button editAppointmentButton;
    @FXML Button saveAppointmentButton;
    @FXML Button cancelEditAppointmentButton;
    @FXML Button closeAppointmentButton;
    @FXML Button deleteAppointmentButton;

    private Customer customer;
    private Appointment appointment;

    public void initialize() {
        userNameLabel.setText(UserSettings.userName);
        startAmPmChoiceBox.setItems(amPmOptions);
        endAmPmChoiceBox.setItems(amPmOptions);
        toggleEditAppointmentUiElements(false);
        setAppointmentFieldOpacity(0.8);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customerNameLabel.setText(customer.getCustomerName());
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
        populateAppointmentFields();
    }

    public void closeAppointment() {
        // return to main ui by firing close request event
        Window window = saveAppointmentButton.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void endEditAppointment() {
        toggleEditAppointmentUiElements(false);
        populateAppointmentFields();
        setAppointmentFieldOpacity(0.8);
    }

    public void beginEditAppointment() {
        toggleEditAppointmentUiElements(true);
        setAppointmentFieldOpacity(1);
    }

    public void saveEditAppointment() {
        // get text fields
        String title = appointmentTitleTextField.getText();
        String location = appointmentLocationTextField.getText();
        String contact = appointmentContactTextField.getText();
        String url = appointmentUrlTextField.getText();
        String type = appointmentTypeTextField.getText();
        String description = appointmentDescriptionTextArea.getText();

        // dates and times
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

        // update appointment
        AppointmentDao appointmentDao = new AppointmentDaoImp();
        boolean isUpdated = appointmentDao.updateAppointment(appointment.getAppointmentId(), customer.getCustomerId(), UserSettings.userId,
                title, description, location, contact, type, url, startDateTime, endDateTime);
        if (isUpdated) {
            appointment.setTitle(title);
            appointment.setDescription(description);
            appointment.setLocation(location);
            appointment.setContact(contact);
            appointment.setType(type);
            appointment.setUrl(url);
            appointment.setStart(startDateTime);
            appointment.setEnd(endDateTime);
        }
        endEditAppointment();
    }

    public void requestDeleteAppointment() {
        String confirmationMessage = "Are you sure you want to permanently remove this appointment?\n\n\n";
        boolean deleteConfirmed = new ConfirmationWindow().display(confirmationMessage);
        if (deleteConfirmed) {
            AppointmentDao appointmentDao = new AppointmentDaoImp();
            appointmentDao.deleteAppointment(appointment);
            closeAppointment();
        }
    }

    private LocalDateTime createDateTime(LocalDate date, String hour, String minute, String amPmChoice) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime time = LocalTime.parse(hour + ":" + minute + " " + amPmChoice, timeFormatter);
        return date.atTime(time);
    }

    private void populateAppointmentFields() {
        // text fields
        appointmentTitleTextField.setText(appointment.getTitle());
        appointmentLocationTextField.setText(appointment.getLocation());
        appointmentContactTextField.setText(appointment.getContact());
        appointmentUrlTextField.setText(appointment.getUrl());
        appointmentTypeTextField.setText(appointment.getType());
        appointmentDescriptionTextArea.setText(appointment.getDescription());
        // start date and time
        datePicker.setValue(appointment.getStart().toLocalDate());
        String startHour = appointment.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("hh"));
        String startMinute = appointment.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("mm"));
        String startAmPmChoice = appointment.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("a"));
        startHourTextField.setText(startHour);
        startMinuteTextField.setText(startMinute);
        startAmPmChoiceBox.setValue(startAmPmChoice);
        // end time
        String endHour = appointment.getEnd().toLocalTime().format(DateTimeFormatter.ofPattern("hh"));
        String endMinute = appointment.getEnd().toLocalTime().format(DateTimeFormatter.ofPattern("mm"));
        String endAmPmChoice = appointment.getEnd().toLocalTime().format(DateTimeFormatter.ofPattern("a"));
        endHourTextField.setText(endHour);
        endMinuteTextField.setText(endMinute);
        endAmPmChoiceBox.setValue(endAmPmChoice);
    }

    private void setAppointmentFieldOpacity(double opacity) {
        appointmentTitleTextField.setOpacity(opacity);
        appointmentLocationTextField.setOpacity(opacity);
        appointmentContactTextField.setOpacity(opacity);
        appointmentUrlTextField.setOpacity(opacity);
        appointmentTypeTextField.setOpacity(opacity);
        appointmentDescriptionTextArea.setOpacity(opacity);
        // start date and time
        datePicker.setOpacity(opacity);
        startHourTextField.setOpacity(opacity);
        startMinuteTextField.setOpacity(opacity);
        startAmPmChoiceBox.setOpacity(opacity);
        // end time
        endHourTextField.setOpacity(opacity);
        endMinuteTextField.setOpacity(opacity);
        endAmPmChoiceBox.setOpacity(opacity);
    }

    private void toggleEditAppointmentUiElements(boolean isOn) {
        // text fields
        appointmentTitleTextField.setDisable(!isOn);
        appointmentLocationTextField.setDisable(!isOn);
        appointmentContactTextField.setDisable(!isOn);
        appointmentUrlTextField.setDisable(!isOn);
        appointmentTypeTextField.setDisable(!isOn);
        appointmentDescriptionTextArea.setDisable(!isOn);
        // start date and time
        datePicker.setDisable(!isOn);
        startHourTextField.setDisable(!isOn);
        startMinuteTextField.setDisable(!isOn);
        startAmPmChoiceBox.setDisable(!isOn);
        // end time
        endHourTextField.setDisable(!isOn);
        endMinuteTextField.setDisable(!isOn);
        endAmPmChoiceBox.setDisable(!isOn);
        // buttons
        deleteAppointmentButton.setDisable(isOn);
        deleteAppointmentButton.setVisible(!isOn);
        editAppointmentButton.setDisable(isOn);
        editAppointmentButton.setVisible(!isOn);
        closeAppointmentButton.setDisable(isOn);
        closeAppointmentButton.setVisible(!isOn);
        saveAppointmentButton.setDisable(!isOn);
        saveAppointmentButton.setVisible(isOn);
        cancelEditAppointmentButton.setDisable(!isOn);
        cancelEditAppointmentButton.setVisible(isOn);
    }
}
