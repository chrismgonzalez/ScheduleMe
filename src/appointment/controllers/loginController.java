package appointment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class loginController {

    @FXML private TextField usernametxt;
    @FXML private TextField passwordtxt;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Button loginButton;


    @FXML
    public void initialize() {
        loginButton.setDisable(false);
    }

    @FXML
    public void handleLogin() {
        System.out.println("Logged In!");
    }

}



//change locale

//grab text input from username field and password field for db credentials

//alert invalid credentials
