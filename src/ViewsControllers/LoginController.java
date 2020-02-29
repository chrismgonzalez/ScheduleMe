package ViewsControllers;

import DAO.UserDao;
import DAO.UserDaoImpl;
import Utils.UserSettings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ResourceBundle;

public class LoginController extends InnerController {
    @FXML private Label userNameLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField userNameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label loginMessageLabel;

    private ResourceBundle rb;

    public void initialize() {
        rb = ResourceBundle.getBundle("Resources.login", UserSettings.locale);
        populateText();
    }

    private void populateText() {
        userNameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
    }

    public void login() {
        String userName = userNameTextField.getText().trim();
        String password = passwordField.getText().trim();
        UserDao userDao = new UserDaoImpl();
        Integer userId = userDao.getUserIdByUserNameAndPassword(userName, password);
        if (userId == null) {
            displayErrorText(rb.getString("error_invalid"));
            return;
        }
        UserSettings.userId = userId;
        UserSettings.userName = userName;
        this.outerController.toggleUiAccessOn();

    }

    private void displayErrorText(String errorMessage) {
        loginMessageLabel.setText(errorMessage);
        loginMessageLabel.setTextFill(Color.RED);
    }
}