package Utils;

import DAO.AppointmentDao;
import DAO.AppointmentDaoImp;
import DataModels.Appointment;
import javafx.collections.ObservableList;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class InputValidator {
    //input validation
    public static void validateCustomerInput(String customerName, String phone, String addressLine1, String addressLine2, String cityName, String postalCode, String countryName) throws IllegalArgumentException {
        //address line 2 does not have validation, but can be added at a future time
        if (customerName.equals("") || phone.equals("") || addressLine1.equals("") || cityName.equals("") || postalCode.equals("") || countryName.equals("")) {
            throw new IllegalArgumentException("The data you have entered is invalid or incomplete.");
        }
    }

    public static String validateHourInput(String hour) throws IllegalArgumentException {
        String validHourString = hour.length() == 1 ? "0" + hour : hour;
        try {
            int hourInt = Integer.parseInt(validHourString);
            if (hourInt < 1 || hourInt > 12) {
                throw new IllegalArgumentException();
            }
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Appointment start or end time is invalid!");
        }
        return validHourString;
    }

    public static String validateMinuteInput(String minute) throws IllegalArgumentException {
        String validMinuteString = minute;
        validMinuteString = validMinuteString.length() == 0 ? "00" : validMinuteString;
        validMinuteString = validMinuteString.length() == 1 ? "0" + validMinuteString : validMinuteString;
        try {
            int minuteInt = Integer.parseInt(validMinuteString);
            if (minuteInt < 0 || minuteInt >= 60) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Appointment start or end time is invalid.");
        }
        return validMinuteString;
    }
    //validate no overlapping appointments
    public static void validateAppointmentDateTimeLogic(LocalDateTime appointmentStart, LocalDateTime appointmentEnd) throws IllegalArgumentException {
        // ensure appointment starts before it ends
        if (appointmentStart.isAfter(appointmentEnd)) {
            throw new IllegalArgumentException("Appointment must start before it ends.");
        }
        // prevent scheduling an appointment outside business hours
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime businessDayStart = LocalTime.parse("08:00 AM", timeFormatter);
        LocalTime businessDayEnd = LocalTime.parse("07:00 PM", timeFormatter);
        if (appointmentStart.toLocalTime().isBefore(businessDayStart) ||
                appointmentEnd.toLocalTime().isAfter(businessDayEnd) ||
                appointmentStart.getDayOfWeek() == DayOfWeek.SATURDAY ||
                appointmentStart.getDayOfWeek() == DayOfWeek.SUNDAY) {
            DateTimeFormatter outputTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
            String outputDayStart = businessDayStart.format(outputTimeFormatter);
            String outputDayEnd = businessDayEnd.format(outputTimeFormatter);
            throw new IllegalArgumentException("Appointments must be scheduled on weekdays between " + outputDayStart + " and " + outputDayEnd + ".");
        }
        // prevent scheduling overlapping appointments
        AppointmentDao appointmentDao = new AppointmentDaoImp();
        ObservableList<Appointment> appointmentsWithin = appointmentDao.getAppointmentsOverlappingInterval(appointmentStart, appointmentEnd, UserSettings.userId);
        if (appointmentsWithin.size() > 0) {
            throw new IllegalArgumentException("Another appointment is already scheduled during this time. Please schedule appointment for a different date or time.");
        }
    }
    //invalid login text
    public static boolean invalidLoginText(String text) {
        return text == null || text.equals("") || text.contains("'") || text.contains("\"");
    }
}
