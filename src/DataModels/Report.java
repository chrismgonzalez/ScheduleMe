package DataModels;

import java.util.List;


public class Report {

    private final List<String> columnNames;
    private final List<List<Object>> data;

    public Report(List<String> columnNames, List<List<Object>> data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    public int getNumColumns() {
        return columnNames.size();
    }

    public String getColumnName (int index) {
        return columnNames.get(index);
    }

    public List<List<Object>> getData() {
        return data;
    }
}
