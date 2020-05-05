package ViewsControllers;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class CalendarView extends Pane {
    private YearMonth currentYearMonth;

    private Label title;
    private Button previous;
    private Button next;

    private GridPane calendarGridPane;
    private List<CalendarDay> calendarDays;

    private double titleBarHeight;
    private double dayNameHeight;

    public CalendarView() {
        this.currentYearMonth = YearMonth.of(LocalDate.now().getYear(), LocalDate.now().getMonth());
        this.title = new Label(LocalDate.now().getMonth().toString());
        this.setPrefWidth(755);
        this.setPrefHeight(700);
        this.titleBarHeight = 10;
        this.dayNameHeight = 20;
        buildCalendar();
    }

    private void buildCalendar() {
        // title and buttons
        previous = new Button("<<");
        previous.setOnAction(e -> previousMonth());
        next = new Button(">>");
        next.setOnAction(e -> nextMonth());
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
        // calendar
        calendarGridPane = new GridPane();
        calendarGridPane.setPrefSize(this.getPrefWidth(), this.getPrefWidth()-titleBarHeight-dayNameHeight);
        calendarGridPane.setGridLinesVisible(true);
        populate();
        // view
        VBox view = new VBox(titleBar, dayLabels, calendarGridPane);
        view.setPrefSize(this.getPrefWidth(), this.getPrefHeight());
        this.getChildren().add(view);
    }

    public void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populate();
    }

    public void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populate();
    }

    private void populate() {
        if (calendarDays != null) {
            calendarGridPane.getChildren().removeAll(calendarDays);
        }
        calendarDays = generateCalendarDays(currentYearMonth);
        for (int i = 0; i < 35; i++) {
            int col = i % 7;
            int row = i / 7;
            calendarGridPane.add(calendarDays.get(i), col, row);
        }
        title.setText(currentYearMonth.getMonth().toString() + " " + currentYearMonth.getYear());
    }

    private ArrayList<CalendarDay> generateCalendarDays(YearMonth yearMonth) {
        ArrayList<CalendarDay> allCalendarDays= new ArrayList<>(35);
        LocalDate date = yearMonth.atDay(1);
        while (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            date = date.minusDays(1);
        }
        for (int i = 0; i < 35; i++) {
            CalendarDay day = new CalendarDay(date);
            day.setPrefSize(this.getPrefWidth() / 7, (this.getPrefHeight()-titleBarHeight-dayNameHeight) / 7);
            if (!day.getDate().getMonth().equals(yearMonth.getMonth())) {
                day.setStyle("-fx-background-color: lightgray; -fx-border-color: black; -fx-border-width: 0.5");
            }
            allCalendarDays.add(day);
            date = date.plusDays(1);
        }
        return allCalendarDays;
    }

    public List<CalendarDay> getCalendarDays() {
        return calendarDays;
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

    public YearMonth getMonth() {
        return currentYearMonth;
    }

    public void setMonth(YearMonth yearMonth) {
        this.currentYearMonth = yearMonth;
        populate();
    }
}
