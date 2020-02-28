package appointment.views;



import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import appointment.models.UserDB;
import appointment.utils.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class LoginController implements Initializable {

    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button loginButton;

    private String errorHeader;
    private String errorTitle;
    private String errorText;

    //login to application
    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        boolean validUser = UserDB.login(username, password);
        boolean nullCredentials = (username.equals("") || password.equals(""));

        if(validUser) {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Navigation.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Database.connect();

        } if(nullCredentials) {
            Database.disconnect();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("null credentials");
            alert.setHeaderText(errorHeader);
            alert.setContentText(errorText);
            alert.showAndWait();

        } else {
            Database.disconnect();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorTitle);
            alert.setHeaderText(errorHeader);
            alert.setContentText(errorText);
            alert.showAndWait();
        }
    }

    //get and change the locale
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("appointment.resources/login", locale);

        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        errorHeader = rb.getString("errorHeader");
        errorTitle = rb.getString("errorTitle");
        errorText = rb.getString("errorText");
    }
}




//change locale

//grab text input from username field and password field for db credentials

//alert invalid credentials
