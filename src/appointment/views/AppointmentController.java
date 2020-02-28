package appointment.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

    @FXML
    public void handleAddAppointment() {
        System.out.println("Add appointment button clicked!");
    }

    @FXML
    public void handleEditAppointment() {
        System.out.println("add customer button clicked!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {}
}
