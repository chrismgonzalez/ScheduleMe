package ViewsControllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NotificationWindow {
    private String text;

    public NotificationWindow(String text) {
        this.text = text;
    }

    public void launchAndWait() {
        Stage window = new Stage();
        // create layout
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setPadding(new Insets(5, 5, 5, 5));
        // create text label and button
        Label messageLabel = new Label(this.text);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.hide());
        layout.getChildren().addAll(messageLabel, closeButton);
        // launch window
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    public void launch() {
        Stage window = new Stage();
        // create layout
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setPadding(new Insets(5, 5, 5, 5));
        // create text label and button
        Label messageLabel = new Label(this.text);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.hide());
        layout.getChildren().addAll(messageLabel, closeButton);
        // launch window
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.initModality(Modality.NONE);
        window.show();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;


    }
}
