package ViewsControllers;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

public class CalendarDay extends AnchorPane {
    private LocalDate date;
    private Label dayOfMonth;
    public static final String DEFAULT_STYLE = "-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0.5";

    public CalendarDay(LocalDate date, Node... children) {
        super(children);
        this.date = date;
        // day of month
        String dayOfMonthString = String.valueOf(date.getDayOfMonth());
        dayOfMonth = new Label(dayOfMonthString);
        this.getChildren().add(dayOfMonth);
        AnchorPane.setTopAnchor(dayOfMonth, 5.0);
        AnchorPane.setLeftAnchor(dayOfMonth, 5.0);
        // style
        this.setStyle(CalendarDay.DEFAULT_STYLE);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
    }

    public Label getDayOfMonth() {
        return dayOfMonth;
    }
}
