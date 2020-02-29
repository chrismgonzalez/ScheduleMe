package ViewsControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;

import java.io.IOException;

public class OuterController {
    @FXML private Button scheduleViewButton;
    @FXML private Button customersViewButton;
    @FXML private Button reportsViewButton;
    @FXML private Button logoutViewButton;
    @FXML private TitledPane innerSceneTitledPane;

    public void initialize() {
        launchLoginScene();
    }

    public void toggleUiAccessOn() {
        scheduleViewButton.setDisable(false);
        customersViewButton.setDisable(false);
        reportsViewButton.setDisable(false);
        logoutViewButton.setDisable(false);
    }

    public void toggleUiAccessOff() {
        scheduleViewButton.setDisable(true);
        customersViewButton.setDisable(true);
        reportsViewButton.setDisable(true);
        logoutViewButton.setDisable(true);

    }

    public InnerController changeInnerScene(String fxmlPath) {
        Node innerRoot;
        InnerController innerController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            innerRoot = loader.load();
            innerController = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        innerSceneTitledPane.setContent(innerRoot);
        innerController.setOuterController(this);
        return innerController;
    }

    public LoginController launchLoginScene() {
        toggleUiAccessOff();
        String path = "LoginView.fxml";
        LoginController loginController = (LoginController) changeInnerScene(path);
        innerSceneTitledPane.setText("Login");
        return loginController;
    }

    public ScheduleController launchScheduleScene() {
        String path = "ScheduleView.fxml";
        ScheduleController scheduleController = (ScheduleController) changeInnerScene(path);
        innerSceneTitledPane.setText("Schedule");
        return scheduleController;
    }

    public CustomersController launchCustomersScene() {
        String path = "CustomersView.fxml";
        CustomersController customersController = (CustomersController) changeInnerScene(path);
        innerSceneTitledPane.setText("Customers");
        return customersController;
    }

    public ReportsController launchReportsScene() {
        String path = "ReportsView.fxml";
        ReportsController reportsController = (ReportsController) changeInnerScene(path);
        innerSceneTitledPane.setText("Reports");
        return reportsController;
    }

}
