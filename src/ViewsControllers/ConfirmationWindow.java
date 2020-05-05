package ViewsControllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationWindow {

    private boolean result;

    public boolean display(String message) {
        Stage stage = new Stage();

        // set message text
        Label messageLabel = new Label(message);
        messageLabel.setTextAlignment(TextAlignment.CENTER);

        // create buttons
        Button yesButton = new Button("Yes");
        ButtonBar.setButtonData(yesButton, ButtonBar.ButtonData.YES);
        yesButton.setOnAction(e -> {
            result = true;
            stage.close();
        });
        Button noButton = new Button("No");
        ButtonBar.setButtonData(noButton, ButtonBar.ButtonData.NO);
        noButton.setOnAction(e -> {
            result = false;
            stage.close();
        });
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(yesButton, noButton);

        // create layout
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setPadding(new Insets(5, 5, 5, 5));
        layout.getChildren().addAll(messageLabel, buttonBar);

        // launch window
        Scene errorScene = new Scene(layout);
        stage.setScene(errorScene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        return result;
    }

}
