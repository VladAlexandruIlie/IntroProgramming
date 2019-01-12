import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringDB {
    // pattern for "SELECT * FROM courses ;"
    private static final Pattern SELECTALL = Pattern.compile("\\s?SELECT\\s?\\*\\s?FROM\\s?(\\w{1,})\\s?\\;");
    // pattern for "DROP TABLE students ;"
    private static final Pattern DROP = Pattern.compile("\\s?DROP\\sTABLE\\s(\\w{1,})\\s?\\;");
    // pattern for "CREATE TABLE teachers ( name varchar , username varchar ) ;"
    private static final Pattern CREATE = Pattern.compile("\\s?CREATE\\sTABLE\\s(\\w{1,})\\s?\\(((\\s?\\w{1,}\\s?\\w{1,}\\s?\\,?){1,})\\)\\s?\\;?");
    // pattern for "INSERT INTO teachers VALUES ( 'Troels_Bjerre_Lund' , 'trbj' ) ;"
    private static final Pattern INSERT = Pattern.compile("\\s?INSERT\\sINTO\\s(\\w{1,})\\sVALUES\\s?\\(((\\s?((\\s?\\'(\\w.{1,})\\'?\\s?\\,?){1,})\\'?\\s?,?){1,})\\)\\s?\\;?");
    // pattern for "SELECT name AS alias , username AS secret_email INTO superheroes FROM teachers ;"
    private static final Pattern SELECTASINTO = Pattern.compile("(?i)" + "\\s*SELECT\\s*((\\s*(\\w*)\\s*AS\\s*(\\w*)\\s*\\,*)*)\\s*INTO\\s*(\\w*)\\s*FROM\\s*(\\w*)\\s*\\;");
    // pattern for "SELECT * INTO joined FROM orders INNER JOIN customers ON CustomerID = ID ;"
    private static final Pattern INNERJOIN = Pattern.compile("(?i)" + "\\s*SELECT\\s*\\*\\s*INTO\\s*(\\w*)\\sFROM\\s*(\\w*)\\s*INNER JOIN\\s*(\\w*)\\s*ON\\s*(\\w*)\\s*\\=\\s*(\\w*)");

    private Map<String, Table> tables = new HashMap<String, Table>();

    public void execute(String command) {
        Matcher query;

        if ((query = SELECTALL.matcher(command)).matches()) {
            String title = query.group(1);
            boolean ok = true;
            if (tables.get(title) == null) {
                ok = false;
                System.out.println("ERROR:  table \"" + title + "\" does not exist");
            }
            if (ok) {
                Table table = tables.get(title);
                List<String> header = table.columns;
                List<Row> rows = table.rows;
                DBHelper.printTable(header, rows);
                //System.out.println("");
            }
        } else if ((query = DROP.matcher(command)).matches()) {
            String title = query.group(1);
            boolean ok = true;
            if (tables.get(title) == null) {
                System.out.println("ERROR:  table \"" + title + "\" does not exist");
                ok = false;
            }
            if (ok) {
                tables.remove(title);
                System.out.println("DROP TABLE");
            }
        } else if ((query = CREATE.matcher(command)).matches()) {
            List<String> parameters = new ArrayList<String>();
            String[] parts = query.group(2).replace(",", " ").split("\\s+");
            for (String s : parts) {
                if (!s.equals("varchar")) parameters.add(s);
            }
            parameters.remove(0);
            boolean ok = true;
            for (int i = 0; i <= parameters.size() - 1 && ok; i++) {
                for (int j = 0; j <= parameters.size() - 1 && ok; j++) {
                    if (parameters.get(i).equals(parameters.get(j)) && i != j) {
                        System.out.println("ERROR:  column \"" + parameters.get(i) + "\" specified more than once");
                        ok = false;
                    }
                }
            }
            if (ok) {
                String title = query.group(1);
                List<Row> rows = new ArrayList<Row>();
                Table table = new Table(parameters, rows);
                //DBHelper.printTable(table.columns, table.rows);
                System.out.println("CREATE TABLE");
                tables.put(title, table);
            }
        } else if ((query = INSERT.matcher(command)).matches()) {
            List<String> parameters = new ArrayList<String>();
            String[] parts = query.group(2).split("\'");
            int i = 0;
            for (String s : parts) {
                parameters.add(s);
                //System.out.println(i + ": " + s);
                i++;
            }
            for (i = 0; i <= parameters.size(); i++) {
                if (parameters.get(i).equals(" , ")) parameters.remove(i);
                if (parameters.get(i).equals(", ")) parameters.remove(i);
                if (parameters.get(i).equals(",")) parameters.remove(i);
                else if (parameters.get(i).equals(" ")) parameters.remove(i);
            }

            String title = query.group(1);
            boolean ok = true;
            if (tables.get(title) == null) {
                System.out.println("ERROR:  table \"" + title + "\" does not exist");
                ok = false;
            }
            if (ok) {
                Table table = tables.get(title);
                List<Row> rows = table.rows;
                Row newEntry = new Row(parameters);
                rows.add(newEntry);
                table.rows = rows;
                tables.remove(title);
                tables.put(title, table);
                //DBHelper.printTable(table.columns, table.rows);
                System.out.println("INSERT 0 " + 1);
            }
        }
        else if ((query = SELECTASINTO.matcher(command)).matches()) {
            List<String> parameters = new ArrayList<String>();
            String[] parts = query.group(1).replace(",", " ").split("\\s+");
            List<String> columns = new ArrayList<String>();
            List<String> qrows = new ArrayList<String>();

            for (String s : parts) {
                parameters.add(s);
            }
            for (int i = 0; i <= parameters.size() - 1; i = i + 3) {
                qrows.add(parameters.get(i));
            }
            for (int i = 2; i <= parameters.size() - 1; i = i + 3) {
                columns.add(parameters.get(i));
            }

            String newTitle = query.group(5);
            String title = query.group(6);
            Table table = tables.get(title);

            boolean ok = true;
            for (int i = 0; i <= columns.size() - 1 && ok; i++) {
                for (int j = 0; j <= columns.size() - 1 && ok; j++) {
                    if (columns.get(i).equals(columns.get(j)) && i != j) {
                        System.out.println("ERROR: column \"" + columns.get(i) + "\" specified more than once");
                        ok = false;
                    }
                }
            }
            if (ok) {
                List<Row> entries = new ArrayList<Row>();
                List<String> columnpos = new ArrayList<String>();

                for (int j = 0; j <= columns.size() - 1; j++) {
                    columnpos.add(qrows.get(j));
                }
                for (int i =0; i< table.rows.size();i++) {
                    ArrayList<String> row = new ArrayList<String>();
                    for (int l = 0 ; l < columnpos.size(); l++ ) {
                        for (int k = 0; k < table.columns.size(); k++) {
                            if (columnpos.get(l).equals(table.columns.get(k))) {
                                row.add(table.rows.get(i).entries.get(k));
                            }
                        }
                    }
                    Row newRow = new Row(row);
                    entries.add(newRow);
                }
                Table newTable = new Table(columns, entries);
                tables.put(newTitle,newTable);
                System.out.println("SELECT " + newTable.rows.size());
            }
        } else if ((query = INNERJOIN.matcher(command)).matches()) {
            String title1 = query.group(2);
            String title2 = query.group(3);

            String newtTitle = query.group(1);
            List<String> newcol = new ArrayList<String>();
            List<Row> newRows = new ArrayList<Row>();

            Table table1 = tables.get(title1);
            Table table2 = tables.get(title2);

            List<String> col1 = new ArrayList<String>();
            List<Row> rows1 = new ArrayList<Row>();


            List<String> col2 = new ArrayList<String>();
            List<Row> rows2 = new ArrayList<Row>();



            for (String s: col1)
            newcol.add(table1.columns.toString());
            newcol.add(query.group(3));

            Table newtable  = new Table(newcol,newRows);
            System.out.println("Wow... It creates a new table called " + query.group(1));
            System.out.println("The columns are all those from the table " + query.group(2) +
                    " followed by all those from " + query.group(3));
            System.out.println("The rows will be all the ways to pair a row from " + query.group(2) +
                    " with a row from " + query.group(3));
            System.out.println("but only keeping those where the value of " + query.group(4) +
                    " is the same as the value of " + query.group(5));
        } else System.out.println("Command not found!");

    }
}


