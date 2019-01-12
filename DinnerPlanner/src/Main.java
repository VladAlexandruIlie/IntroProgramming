import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Main {
    private static List<Ingredient> groceryList = new ArrayList<>();
    private static Map<String, String> weeklyRecipes = new HashMap<String, String>();
    private static Map<String, Integer> recipeScore = new TreeMap<String, Integer>();
    private static final String validCommands[] = {
            "show ingredients", "add ingredients", "show grocery list", "set servings",
            "assign recipe to weekday", "rate dish", "show ratings", "show weekly plan" ,
            "random dinner", "help", "quit"};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("DinnerPlanner");
        System.out.println("Type 'help' for a list of commands.");

        while (sc.hasNext()){
            String line = sc.nextLine();

            boolean ok = okCommand(line);
            if (ok) {
                switch (line) {
                    case "show ingredients":  { showIngredients(); break; }
                    case "add ingredients":   { addIngredients();break; }
                    case "show grocery list": { showGroceryList();break; }
                    case "set servings":      { setServings();break; }
                    case "help":              { help();break; }
                    case "assign recipe to weekday": {assignRecipeToWeekday(); break;}
                    case "show weekly plan":  { showWeeklyPlan(); break;}
                    case "rate dish":         { rateDish(); break;}
                    case "show ratings":      { showRatings(); break;}
                    case "random dinner":     { randomDinner(); break;}
                    case "quit": System.exit(0);
                }
            }
            else {
                System.out.print("Invalid command!");
                help();
            }
        }
    }

    private static void randomDinner() {
        File f = new File("recipes/");
        ArrayList<String> allRecipeNames= new ArrayList<>(Arrays.asList(f.list()));
//        for (String s : allRecipeNames){
//            //System.out.println(s);
//        }
        Random rng = new Random();
        int index = rng.nextInt(allRecipeNames.size());
        System.out.println("Today you are eating: " + allRecipeNames.get(index));
    }

    private static void showRatings() {
       Map<String, Integer> sortedMap = sortByValue(recipeScore);
        for (String s: sortedMap.keySet()){
            System.out.println("Recipe " + s + " assigned to: " + sortedMap.get(s));
        }
    }

    private static Map<String,Integer> sortByValue(Map<String, Integer> recipeScore) {
    List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(recipeScore.entrySet());
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return (o2.getValue()).compareTo(o1.getValue());
        }
    });
    Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
    for (Map.Entry<String,Integer> entry : list){
        sortedMap.put(entry.getKey(), entry.getValue());
    }
    return sortedMap;
    }

    private static void rateDish() {
        System.out.println("Select a dish to rate: ");
        Scanner sc1 = new Scanner(System.in);
        String line = "";
        while (sc1.hasNext()) {
            String line2 = sc1.nextLine();
            String path = "recipes/" + line2;
            try {
                DinnerPlanner dinner = new DinnerPlanner(path);
                boolean sw = dinner.checkState();
                line = line2;
                break;
            } catch (NullPointerException e) {
                System.out.println("file does not exist!");
            }
        }
        System.out.println("Select a score for recipe: " + line);
        int score = Integer.parseInt(sc1.nextLine());
        System.out.println("recipe: " + line + " has been rated with: " + score);
        recipeScore.put(line, score);
    }

    private static void showWeeklyPlan() {
        for (String s: weeklyRecipes.keySet()){
            System.out.println("Recipe " + weeklyRecipes.get(s) + " assigned to: " + s);
        }
    }

    private static void assignRecipeToWeekday() {
        System.out.println("Select a weekday to assign the recipe to: ");
        Scanner sc1 = new Scanner(System.in);
        String weekday = sc1.nextLine();

        System.out.println("Select a recipe to assign to " + weekday);
        String line = null;
        while (sc1.hasNext()) {
            String line2 = sc1.nextLine();
            String path = "recipes/" + line2;
            try {
                DinnerPlanner dinner = new DinnerPlanner(path);
                dinner.checkState();
                line = line2;
                break;
            }
            catch (NullPointerException e){
                System.out.println("file does not exist!");
            }
        }

        if (!weeklyRecipes.keySet().contains(weekday)) {
             weeklyRecipes.put(weekday, line);
             System.out.println("Recipe : " + line + " has been assigned to " + weekday );
        }
        else System.out.println("day already planned!");
    }

    private static void showIngredients() {
        System.out.println("Select a recipe to show: ");
        Scanner sc1 = new Scanner(System.in);
        while (sc1.hasNext()) {
            String line = sc1.nextLine();
            String path = "recipes/" + line;
            try {
                DinnerPlanner dinner = new DinnerPlanner(path);
                dinner.printIngredients();
                break;
            }
            catch (NullPointerException e){
                //System.out.println("file does not exist!");
            }
        }
    }

    private static void addIngredients() {
        System.out.println("Select a recipe to add all ingredients to the grocery list: ");
        Scanner sc1 = new Scanner(System.in);
        while (sc1.hasNext()) {
            String line = sc1.nextLine();
            String path = "recipes/" + line;
            try {
                DinnerPlanner dinner = new DinnerPlanner(path);
                dinner.addIngredients(groceryList, dinner.getIngredients());
                System.out.println("Grocery list updated.");
                break;
            }
            catch (NullPointerException e){
                System.out.println("file does not exist!");
            }
        }
    }

    public static void showGroceryList(){
        System.out.println("Groceries: ");
        NumberFormat nf = new DecimalFormat("##.##");
        for (Ingredient i : groceryList){
            System.out.printf("%-5s %s %s \n", nf.format(i.getAmount()), i.getUnit(), i.getDescription());
        }
    }

    private static void setServings() {
        System.out.println("Select a recipe to set the number of servings and add it to the grocery list");
        Scanner sc1 = new Scanner(System.in);
        while (sc1.hasNext()) {
            String line = sc1.nextLine();
            String path = "recipes/" + line;
            try {
                DinnerPlanner dinner = new DinnerPlanner(path);
                System.out.println("Number of servings: " + dinner.getServings());
                System.out.println("Insert the desired number of servings: ");
                Double servs = Double.parseDouble(sc1.nextLine());
                dinner.setServings(servs);
                System.out.println("Servings set");
                dinner.printIngredients();
                dinner.addIngredients(groceryList,dinner.getIngredients());
                System.out.println("Grocery list updated.");
                break;
            } catch (NullPointerException e) {
                System.out.println("file does not exist!");
            }
        }
    }

    private static void help() {
        System.out.print(" Valid commands are: \n");
        for(String command : validCommands) {
            System.out.print("\""+command + "\" ");
        }
        System.out.println();
    }

    public static boolean okCommand(String aString) {
        if(aString != null){
            for (String s: validCommands){
                if (s.equals(aString)) return true;
            }
        }
        return false;
    }
}




