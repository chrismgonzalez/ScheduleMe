package ViewsControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML Label userNameLabel;
    @FXML TextField appointmentTitleTextField;
    @FXML DatePicker datePicker;
    //TODO


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
