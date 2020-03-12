package DAO;

import DataModels.Appointment;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public interface AppointmentDao {
    ObservableList<Appointment> getAllAppointments();
    ObservableList<Appointment> getAppointmentsByUserId(int userId);
    ObservableList<Appointment> getAppointmentsByCustomerId(int customerId);
    ObservableList<Appointment> getAppointmentsOverlappingInterval(LocalDateTime timeFrameStart, LocalDateTime timeFrameEnd, int userId);
    ObservableList<Appointment> getAppointmentsStartingInterval(LocalDateTime intervalStart, LocalDateTime intervalEnd, int userId);
    boolean updateAppointment(Appointment appointment);
    boolean updateAppointment(int appointmentId, int customerId, int userId, String title, String description, String location, String contact, String url, LocalDateTime start, LocalDateTime end );
    boolean deleteAppointment (Appointment appointment);
    Appointment addAppointment(int customerId, int userId, String title, String description, String location, String contact, String url, LocalDateTime start, LocalDateTime end);
}
