package ViewsControllers;

import DataModels.Appointment;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class WeekDay extends AnchorPane {

    private LocalDate date;
    private List<Appointment> appointments;
    private VBox container;

    // basing sizes of visual components on num seconds in day
    private LocalTime timePointer;
    private final LocalTime DAY_START = LocalTime.of(7, 0);
    private final LocalTime DAY_END = LocalTime.of(19, 59, 59);

    public WeekDay(LocalDate date, Node... children) {
        super(children);
        this.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0.5");
        this.date = date;
        this.appointments = new ArrayList<>();
        // create container
        container = new VBox();
        container.setPrefSize(this.getPrefWidth(), this.getPrefHeight());
        container.setSpacing(2);
        container.setAlignment(Pos.BASELINE_CENTER);
        this.getChildren().add(container);
        // reset
        repopulate();
    }

    public void repopulate() {
        // clear container and reset pointer
        if (container.getChildren().size() >= 2) {
            container.getChildren().remove(1, container.getChildren().size());
        }
        timePointer = DAY_START;
        // sort appointments
        Collections.sort(appointments);
        appointments.forEach(this::addAppointment);
        addEodBuffer();
    }

    private boolean addAppointment(Appointment appointment) {
        // add transparent gap where there are no appointments
        Region buffer = new Region();
        double bufferLength = Duration.between(timePointer, appointment.getStart().toLocalTime()).getSeconds();
        bufferLength = secondsToHeight(bufferLength);
        buffer.setPrefHeight(bufferLength);
        timePointer = appointment.getStart().toLocalTime();
        // create rectangle with height proportional to appointment length
        Rectangle appRectangle = new Rectangle();
        double appointmentHeight = Duration.between(appointment.getStart().toLocalTime(), appointment.getEnd().toLocalTime()).getSeconds();
        appointmentHeight = secondsToHeight(appointmentHeight);
        appRectangle.setHeight(appointmentHeight);
        timePointer = appointment.getEnd().toLocalTime();
        // style rectangle
        appRectangle.setWidth(this.getPrefWidth()*0.95);
        appRectangle.setFill(Color.LIGHTBLUE);
        appRectangle.setStrokeWidth(2);
        // add to day
        return container.getChildren().addAll(buffer, appRectangle);
    }

    private double secondsToHeight(double seconds) {
        long secondsInDay = Duration.between(DAY_START, DAY_END).getSeconds();
        return (seconds / secondsInDay) * this.getPrefHeight();
    }

    private boolean addEodBuffer() {
        // fill remainder of day with transparent buffer
        Region postBuffer = new Region();
        double postBufferLength = Duration.between(timePointer, DAY_END).getSeconds();
        postBufferLength = secondsToHeight(postBufferLength);
        postBuffer.setPrefHeight(postBufferLength);
        return container.getChildren().add(postBuffer);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        repopulate();
    }

    @Override
    public void setPrefSize(double prefWidth, double prefHeight) {
        super.setPrefSize(prefWidth, prefHeight);
        container.setPrefSize(this.getPrefWidth(), this.getPrefHeight());
    }
}
