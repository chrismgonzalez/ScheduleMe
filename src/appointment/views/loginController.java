package appointment.views;


import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class loginController implements Initializable {

    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private AnchorPane errorPane;
    @FXML
    private Label mainMessage;
    @FXML
    private Label languageMessage;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button loginButton;

    private String errorHeader;
    private String errorTitle;
    private String errorText;

    @FXML
    public void handleLogin() {
        System.out.println("Logged In!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();
        System.out.println(locale);
        rb = ResourceBundle.getBundle("appointment.resources/login", locale);

        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        mainMessage.setText(rb.getString("message"));
        languageMessage.setText(rb.getString("language"));
        errorHeader = rb.getString("errorHeader");
        errorTitle = rb.getString("errorTitle");
        errorText = rb.getString("errorText");
    }
}




//change locale

//grab text input from username field and password field for db credentials

//alert invalid credentials
