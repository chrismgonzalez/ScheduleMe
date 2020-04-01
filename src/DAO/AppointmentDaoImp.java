package DAO;

import DataModels.Appointment;
import Utils.DbConnection;
import Utils.UserSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AppointmentDaoImp implements AppointmentDao{

    @Override

    public ObservableList<Appointment> getAllAppointments() {
        ObservableList<AppointmentDao> allAppointments = FXCollections.observableArrayList();
        String query = "select appointmentId, customerId, userId, title, description, location, contact, type, url, start, end from appointment";
        try {
            ResultSet resultSet = DbConnection.getInstance().connection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointmentId");
                int customerId = resultSet.getInt("customerId");
                int userId = resultSet.getInt("userId");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                String contact = resultSet.getString("contact");
                String type = resultSet.getString("type");
                String url = resultSet.getString("url");
                LocalDateTime start = resultSet.getTimestamp("start").toLocalDateTime().atZone(DbConnection.DB_TIME_ZONE).withZoneSameInstant(UserSettings.USER_TIME_ZONE).toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("end").toLocalDateTime().atZone(DbConnection.DB_TIME_ZONE).withZoneSameInstant(UserSettings.USER_TIME_ZONE).toLocalDateTime();
                Appointment appointment = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, start, end);
                allAppointments.add(appointment);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
         return allAppointments;

    }
}
