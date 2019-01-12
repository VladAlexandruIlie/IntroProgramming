import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringDB {
    private static List<Pattern> myPatterns = new ArrayList<>();

    public void execute(String command) {
        // pattern for "SELECT * FROM courses ;"
        myPatterns.add(Pattern.compile("\\s?SELECT\\s\\*\\sFROM\\s(\\w{1,})\\s?\\;"));

        // pattern for "DROP TABLE students ;"
        myPatterns.add(Pattern.compile("\\s?DROP\\sTABLE\\s(\\w{1,})\\s?\\;"));

        // pattern for "CREATE TABLE teachers ( name varchar , username varchar ) ;"
        myPatterns.add(Pattern.compile("\\s?CREATE\\sTABLE\\s(\\w{1,})\\s?\\(((\\s?\\w{1,}\\s?\\w{1,}\\s?\\,?){1,})\\)\\s?\\;?"));

        // pattern for "INSERT INTO teachers VALUES ( 'Troels_Bjerre_Lund' , 'trbj' ) ;"
        myPatterns.add(Pattern.compile("\\s?INSERT\\sINTO\\s(\\w{1,})\\sVALUES\\s?\\(((\\s?((\\s?\\'(\\w.{1,})\\'?\\s?\\,?){1,})\\'?\\s?,?){1,})\\)\\s?\\;?"));

        // pattern for "SELECT name AS alias , username AS secret_email INTO superheroes FROM teachers ;"
        myPatterns.add(Pattern.compile("\\s*SELECT\\s*((\\s*(\\w*)\\s*AS\\s*(\\w*)\\s*\\,*)*)\\s*INTO\\s*(\\w*)\\s*FROM\\s*(\\w*)\\s*\\;"));

        // pattern for "SELECT * INTO joined FROM orders INNER JOIN customers ON CustomerID = ID ;"
        myPatterns.add(Pattern.compile("\\s*SELECT\\s*\\*\\s*INTO\\s*(\\w*)\\sFROM\\s*(\\w*)\\s*INNER JOIN\\s*(\\w*)\\s*ON\\s*(\\w*)\\s*\\=\\s*(\\w*)"));

        Matcher matcher1 = myPatterns.get(0).matcher(command);
        Matcher matcher2 = myPatterns.get(1).matcher(command);
        Matcher matcher3 = myPatterns.get(2).matcher(command);
        Matcher matcher4 = myPatterns.get(3).matcher(command);
        Matcher matcher5 = myPatterns.get(4).matcher(command);
        Matcher matcher6 = myPatterns.get(5).matcher(command);

        if (matcher1.find()) {
            System.out.println("It prints everything from the table called " + matcher1.group(1) );
        }
        else if (matcher2.find()){
            System.out.println("It completely removes the table called " + matcher2.group(1) + "... be careful");
        }
        else if (matcher3.find()){
            List<String> parameters = new ArrayList<String>();
            String[] parts = matcher3.group(2).replace(",", " ").split("\\s+");
            for (String s: parts){
                if (!s.equals("varchar")) parameters.add(s);
            }
            System.out.println("It creates a new table called " + matcher3.group(1) +
                               " The table has " + (parameters.size() -1 ) + " columns, called: " );
            System.out.print("[");
            for (int i=1; i<= parameters.size()-2; i++){
                System.out.print(parameters.get(i) +", ");
            }
            System.out.print(parameters.get(parameters.size()-1) + "]");
        }
        else if (matcher4.find()){
            List<String> parameters = new ArrayList<String>();
            String[] parts = matcher4.group(2).replace("'", " ").replace(",", " ").split("\\s+");
            for(String s: parts){
                parameters.add(s);
                //System.out.println(s);
            }
            System.out.println("It inserts a new row into the table called " + matcher4.group(1));
            if (parameters.size() - 1 > 1 )  System.out.println(" The row has the " + (parameters.size()-1) + " values:");
                else System.out.println(" The row has the " + (parameters.size()-1) + " value:");

            System.out.print("[");
            for (int i=1; i<parameters.size() - 1; i++){
                System.out.print(parameters.get(i) + ", ");
            }
            System.out.print(parameters.get(parameters.size() -1) + "]");
        }
        else if (matcher5.find()){
            List<String> parameters = new ArrayList<String>();
            String[] parts = matcher5.group(1).replace(","," ").split("\\s+");
            List<String> columns = new ArrayList<String>();
            List<String> rows = new ArrayList<String>();
            for (String s: parts){
                parameters.add(s);
            }
            for (int i=0; i<= parameters.size()-1; i=i+3) { rows.add(parameters.get(i)); }
            for (int i=2; i<= parameters.size()-1; i=i+3) { columns.add(parameters.get(i)); }

            System.out.println("So... It creates a new table called " + matcher5.group(5));
            System.out.println("The table has " + (columns.size()) + " columns, called:");
            System.out.print("[");
            for (int i=0; i<columns.size() - 1; i++){
                System.out.print(columns.get(i) + ", ");
            }
            System.out.println(columns.get(columns.size() -1) + "]");
            System.out.println("The new table has all the rows from the table called " + matcher5.group(6));
            System.out.println("but only the " + (rows.size()) + " columns called: ");
            System.out.print("[");
            for (int i=0; i<rows.size() - 1; i++){
                System.out.print(rows.get(i) + ", ");
            }
            System.out.print(rows.get(rows.size() -1) + "]");
            }
        else if (matcher6.find()){

            System.out.println("Wow... It creates a new table called " + matcher6.group(1));
            System.out.println("The columns are all those from the table " + matcher6.group(2) +
                                " followed by all those from " + matcher6.group(3) );
            System.out.println("The rows will be all the ways to pair a row from " + matcher6.group(2) +
                                " with a row from " + matcher6.group(3));
            System.out.println("but only keeping those where the value of " + matcher6.group(4) +
                                " is the same as the value of " + matcher6.group(5));
        }
        else System.out.println("Command not found!");
    }
}
