import java.util.List;

public class Table {
    List<String> columns;
    List<Row> rows;

    public Table (List<String> title, List<Row> values){
        columns = title;
        rows = values;
    }

}
