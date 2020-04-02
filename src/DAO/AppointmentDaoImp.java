package DAO;

import DataModels.Appointment;
import Utils.DbConnection;
import Utils.UserSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDaoImp implements AppointmentDao{

    @Override
    public ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
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

    @Override
    public ObservableList<Appointment> getAppointmentsByUserId(int userId) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String query = "select appointmentId, customerId, title, description, location, contact, type, url, start, end from appointment " + "where userId = ?";

        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setString(1, String.valueOf(userId));
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointmentId");
                int customerId = resultSet.getInt("customerId");
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
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    @Override
    public ObservableList<Appointment> getAppointmentsByCustomerId(int customerId) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String query = "select appointmentId, userId, title, description, location, contact, type, url, start, end from appointment " +
                "where customerId = ?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setString(1, String.valueOf(customerId));
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointmentId");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    @Override
    public ObservableList<Appointment> getAppointmentsOverlappingInterval(LocalDateTime intervalStart, LocalDateTime intervalEnd, int userId) {
        // two intervals overlap if (StartA <= EndB) and (EndA >= StartB)
        // proof: https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap/325964#325964
        // I use < and > instead of <= and >= in order to allow exact edge overlap. E.g. an appointment can end at 2pm and another can start at 2pm.
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();;
        String query = "select appointmentId, customerId, title, description, location, contact, type, url, start, end from appointment " +
                "where userId = ? and start < ? and end > ?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setInt(1, userId);
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(intervalEnd.atZone(UserSettings.USER_TIME_ZONE).withZoneSameInstant(DbConnection.DB_TIME_ZONE).toLocalDateTime()));
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(intervalStart.atZone(UserSettings.USER_TIME_ZONE).withZoneSameInstant(DbConnection.DB_TIME_ZONE).toLocalDateTime()));
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointmentId");
                int customerId = resultSet.getInt("customerId");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                String contact = resultSet.getString("contact");
                String type = resultSet.getString("type");
                String url = resultSet.getString("url");
                LocalDateTime start = resultSet.getTimestamp("start").toLocalDateTime().atZone(DbConnection.DB_TIME_ZONE).withZoneSameInstant(UserSettings.USER_TIME_ZONE).toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("end").toLocalDateTime().atZone(DbConnection.DB_TIME_ZONE).withZoneSameInstant(UserSettings.USER_TIME_ZONE).toLocalDateTime();
                Appointment appointment = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, start, end);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }


    @Override
    public ObservableList<Appointment> getAppointmentsStartingInInterval(LocalDateTime intervalStart, LocalDateTime intervalEnd, int userId) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();;
        String query = "select appointmentId, customerId, title, description, location, contact, type, url, start, end from appointment " +
                "where userId = ? and start >= ? and start <= ?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setInt(1, userId);
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(intervalStart.atZone(UserSettings.USER_TIME_ZONE).withZoneSameInstant(DbConnection.DB_TIME_ZONE).toLocalDateTime()));
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(intervalEnd.atZone(UserSettings.USER_TIME_ZONE).withZoneSameInstant(DbConnection.DB_TIME_ZONE).toLocalDateTime()));
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointmentId");
                int customerId = resultSet.getInt("customerId");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                String contact = resultSet.getString("contact");
                String type = resultSet.getString("type");
                String url = resultSet.getString("url");
                LocalDateTime start = resultSet.getTimestamp("start").toLocalDateTime().atZone(DbConnection.DB_TIME_ZONE).withZoneSameInstant(UserSettings.USER_TIME_ZONE).toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("end").toLocalDateTime().atZone(DbConnection.DB_TIME_ZONE).withZoneSameInstant(UserSettings.USER_TIME_ZONE).toLocalDateTime();
                Appointment appointment = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, start, end);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        boolean updated = false;
        String query = "update appointment set customerId=?, userId=?, title=?, description=?, location=?, contact=?, type=?, url=?, start=?, end=?, lastUpdate=now(), lastUpdateBy=? " +
                "where appointmentId=?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setInt(1, appointment.getCustomerId());
            ps.setInt(2, appointment.getUserId());
            ps.setString(3, appointment.getTitle());
            ps.setString(4, appointment.getDescription());
            ps.setString(5, appointment.getLocation());
            ps.setString(6, appointment.getContact());
            ps.setString(7, appointment.getType());
            ps.setString(8, appointment.getUrl());
            ps.setTimestamp(9, java.sql.Timestamp.valueOf(appointment.getStart().atZone(UserSettings.USER_TIME_ZONE).withZoneSameInstant(DbConnection.DB_TIME_ZONE).toLocalDateTime()));
            ps.setTimestamp(10, java.sql.Timestamp.valueOf(appointment.getEnd().atZone(UserSettings.USER_TIME_ZONE).withZoneSameInstant(DbConnection.DB_TIME_ZONE).toLocalDateTime()));
            ps.setString(11, UserSettings.userName);
            ps.setInt(12, appointment.getAppointmentId());
            updated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

    @Override
    public boolean updateAppointment(int appointmentId, int customerId, int userId, String title, String description, String location, String contact, String type, String url, LocalDateTime start, LocalDateTime end) {
        boolean updated = false;
        String query = "update appointment set customerId=?, userId=?, title=?, description=?, location=?, contact=?, type=?, url=?, start=?, end=?, lastUpdate=now(), lastUpdateBy=? " +
                "where appointmentId=?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setInt(1, customerId);
            ps.setInt(2, userId);
            ps.setString(3, title);
            ps.setString(4, description);
            ps.setString(5, location);
            ps.setString(6, contact);
            ps.setString(7, type);
            ps.setString(8, url);
            ps.setTimestamp(9, java.sql.Timestamp.valueOf(start.atZone(UserSettings.USER_TIME_ZONE).withZoneSameInstant(DbConnection.DB_TIME_ZONE).toLocalDateTime()));
            ps.setTimestamp(10, java.sql.Timestamp.valueOf(end.atZone(UserSettings.USER_TIME_ZONE).withZoneSameInstant(DbConnection.DB_TIME_ZONE).toLocalDateTime()));
            ps.setString(11, UserSettings.userName);
            ps.setInt(12, appointmentId);
            updated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

    @Override
    public boolean deleteAppointment(Appointment appointment) {
        String query = "delete from appointment where appointmentId = ?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setInt(1, appointment.getAppointmentId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Appointment addAppointment(int customerId, int userId, String title, String description, String location, String contact, String type, String url, LocalDateTime start, LocalDateTime end) {
        Appointment appointment = null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String query = "insert into appointment (appointmentId, customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, now(), ?)";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            int appointmentId = getNextPrimaryKey();
            ps.setInt(1, appointmentId);
            ps.setInt(2, customerId);
            ps.setInt(3, userId);
            ps.setString(4, title);
            ps.setString(5, description);
            ps.setString(6, location);
            ps.setString(7, contact);
            ps.setString(8, type);
            ps.setString(9, url);
            ps.setTimestamp(10, java.sql.Timestamp.valueOf(start.atZone(UserSettings.USER_TIME_ZONE).withZoneSameInstant(DbConnection.DB_TIME_ZONE).toLocalDateTime()));
            ps.setTimestamp(11, java.sql.Timestamp.valueOf(end.atZone(UserSettings.USER_TIME_ZONE).withZoneSameInstant(DbConnection.DB_TIME_ZONE).toLocalDateTime()));
            ps.setString(12, UserSettings.userName);
            ps.setString(13, UserSettings.userName);
            ps.executeUpdate();
            appointment = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, start, end);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointment;
    }

    private int getNextPrimaryKey() {
        int nextPk = -1;
        String query = "select max(appointmentId) from appointment";
        try {
            ResultSet resultSet = DbConnection.getInstance().connection().createStatement().executeQuery(query);
            if (resultSet.next()) {
                nextPk = resultSet.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getSQLState());
            System.out.println(e.getErrorCode());
        }
        return nextPk;
    }
}
