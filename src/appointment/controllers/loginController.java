package appointment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class loginController {

    @FXML private TextField userNameField;
    @FXML private TextField passwordField;
    @FXML private Label userNameLabel;
    @FXML private Label passwordLabel;
    @FXML private Button loginButton;


    @FXML
    public void initialize() {
        loginButton.setDisable(true);
    }


    //disable login button if text fields are empty
    @FXML
    public void handleKeyReleased() {
        String userNameText = userNameField.getText();
        String passwordText = passwordField.getText();

        boolean disableLoginButton = (userNameText.isEmpty() || userNameText.trim().isEmpty()) || (passwordText.isEmpty() || passwordText.trim().isEmpty());

        loginButton.setDisable(disableLoginButton);


    }
    @FXML
    public void onLogin() {
        System.out.println("Logged In!");
    }


}



//change locale

//grab text input from username field and password field for db credentials

//alert invalid credentials
