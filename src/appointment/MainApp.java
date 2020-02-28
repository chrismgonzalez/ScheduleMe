package appointment;

import appointment.utils.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/Login.fxml"));
        stage.setTitle("ScheduleMe");
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {

        Database.connect();
        launch(args);
        Database.disconnect();
    }
}
