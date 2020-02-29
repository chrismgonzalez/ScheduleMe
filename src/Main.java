import Utils.DbConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ViewsControllers/OuterView.fxml"));
        primaryStage.setTitle("ScheduleMe");
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(event -> close());
        primaryStage.show();
    }

    public void close() {
        DbConnection.getInstance().close();;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
