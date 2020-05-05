package ViewsControllers;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class WeekView extends Pane {

    private LocalDate firstDayOfWeek;

    private Label title;
    private Button previous;
    private Button next;

    private GridPane weekGridPane;
    private List<WeekDay> weekDays;

    private double titleBarHeight;
    private double dayNameHeight;

    public WeekView() {
        LocalDate date = LocalDate.now();
        while (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            date = date.minusDays(1);
        }
        this.firstDayOfWeek = date;
        this.title = new Label(LocalDate.now().getMonth().toString());
        this.setPrefWidth(740);
        this.setPrefHeight(700);
        this.titleBarHeight = 10;
        this.dayNameHeight = 20;
        buildWeek();
    }

    private void buildWeek() {
        // title and buttons
        previous = new Button("<<");
        previous.setOnAction(e -> previousWeek());
        next = new Button(">>");
        next.setOnAction(e -> nextWeek());
        HBox titleBar = new HBox(previous, title, next);
        titleBar.setSpacing(10);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setPrefSize(this.getPrefWidth(), titleBarHeight);
        // day of week names
        String[] dayNames = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefSize( this.getPrefWidth() / 7, dayNameHeight);
        dayLabels.setAlignment(Pos.CENTER);
        for (int i = 0; i < dayNames.length; i++) {
            Label dayName = new Label(dayNames[i]);
            dayName.setPrefSize( this.getPrefWidth() / 7, dayNameHeight);
            dayName.setTextAlignment(TextAlignment.CENTER);
            dayName.setAlignment(Pos.CENTER);
            dayLabels.add(dayName, i, 0);
        }
        // week
        weekGridPane = new GridPane();
        weekGridPane.setPrefSize(this.getPrefWidth(), this.getPrefHeight()-titleBarHeight-dayNameHeight);
        weekGridPane.setGridLinesVisible(true);
        populate();
        // view
        VBox view = new VBox(titleBar, dayLabels, weekGridPane);
        view.setPrefSize(this.getPrefWidth(), this.getPrefHeight());
        this.getChildren().add(view);
    }

    public void nextWeek() {
        firstDayOfWeek = firstDayOfWeek.plusWeeks(1);
        populate();
    }

    public void previousWeek() {
        firstDayOfWeek = firstDayOfWeek.minusWeeks(1);
        populate();
    }

    private void populate() {
        if (weekDays != null) {
            weekGridPane.getChildren().removeAll(weekDays);
        }
        weekDays = generateWeekDays();
        for (int i = 0; i < 7; i++) {
            weekGridPane.add(weekDays.get(i), i, 0);
        }
        title.setText(firstDayOfWeek.toString() + " to " + firstDayOfWeek.plusWeeks(1).toString());
    }

    private ArrayList<WeekDay> generateWeekDays() {
        ArrayList<WeekDay> allWeekDays= new ArrayList<>(7);
        LocalDate date = firstDayOfWeek;
        for (int i = 0; i < 7; i++) {
            WeekDay day = new WeekDay(date);
            day.setPrefSize(this.getPrefWidth() / 7, this.getPrefHeight()-titleBarHeight-dayNameHeight);
            allWeekDays.add(day);
            date = date.plusDays(1);
        }
        return allWeekDays;
    }

    public List<WeekDay> getWeekDays() {
        return weekDays;
    }

    public Label getTitle() {
        return title;
    }

    public Button getPrevious() {
        return previous;
    }

    public Button getNext() {
        return next;
    }

    public LocalDate getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(LocalDate firstDayOfWeek) {
        LocalDate date = firstDayOfWeek;
        while (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            date = date.minusDays(1);
        }
        this.firstDayOfWeek = date;
        populate();
    }

}
