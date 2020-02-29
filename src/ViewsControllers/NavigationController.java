package ViewsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavigationController implements Initializable {
    @FXML
    public void handleEditCustomer() {
        System.out.println("edit customer button clicked!");
    }

    @FXML
    public void handleAddCustomer() {
        System.out.println("add customer button clicked!");
    }

    @FXML
    public void handleAddAppointment() {
        System.out.println("add appointment button clicked!");
    }

    @FXML
    public void handleEditAppointment() {
        System.out.println("edit appointment button clicked!");
    }

    @FXML
    public void handleAppointmentReport() {
        System.out.println("appointment report button clicked!");
    }

    @FXML
    public void handleClientSchedule() {
        System.out.println("client schedule button clicked!");
    }

    @FXML
    public void handleDBLog() {
        System.out.println("DB log button clicked!");
    }




    @FXML
    public void handleLogout(ActionEvent e) throws IOException {
        ((Node) (e.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {}
}
