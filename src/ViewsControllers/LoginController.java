package ViewsControllers;

import DAO.UserDao;
import DAO.UserDaoImpl;
import Utils.UserSettings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
        rb = ResourceBundle.getBundle("Resources.Login", UserSettings.locale);
        populateText();
    }

    private void populateText() {
        userNameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        loginMessageLabel.setText(rb.getString("loginMessage"));

    }
    //login to program
    public void login() {
        String userName = userNameTextField.getText().trim();
        String password = passwordField.getText().trim();
        UserDao userDao = new UserDaoImpl();
        Integer userId = userDao.getUserIdByUserNameAndPassword(userName, password);
        if (userId == null) {
            displayErrorText(rb.getString("error_invalid"));
            //log the login attempt and write to log file
            loginLogger(false, userName);
            return;
        }
        //log the login attempt and write to log file
        loginLogger(true, userName);
        UserSettings.userId = userId;
        UserSettings.userName = userName;
        this.outerController.toggleUiAccessOn();
        ScheduleController scheduleController = this.outerController.launchScheduleScene();
        scheduleController.checkForUpcomingAppointments(Duration.ofMinutes(15));

    }

    private void displayErrorText(String errorMessage) {
        loginMessageLabel.setText(errorMessage);
        loginMessageLabel.setTextFill(Color.RED);
    }
    /*upon a login attempt, either failed or successful, the user, date, and time data will be logged to the file
     * "loginLogger.txt", please look for this in the root directory of the project file */

    private ZonedDateTime loginLogger(boolean success, String userName) {
        ZonedDateTime loginDateTime = ZonedDateTime.now(UserSettings.USER_TIME_ZONE);
        Path logPath = Paths.get("loginLogger.txt");
        //if the log file does not exist, then create one

        if(!Files.exists(logPath)) {
            try {
                Files.createFile(logPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //create a login entry into the log

        String entry;
        String loggedDateTime = loginDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        if(success) {
            entry = "User " + userName + " completed a successful login at " + loggedDateTime + "\n";
        } else {
            entry = "User " + userName + " failed a login attempt at " + loggedDateTime + "\n";
        }

        //write the entry into the log
        try(BufferedWriter writer = Files.newBufferedWriter(logPath, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginDateTime;
    }
}