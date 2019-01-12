import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DinnerPlanner {
    private final Pattern ingredientPattern = Pattern.compile("(\\d?.?\\d{1,}) (-?\\w*) (\\D*)");
    private String name, author, description, time, servings, calories;
    private List<Ingredient> ingredients;
    private int stage =1;


    public DinnerPlanner(String recipe) {
        String line;
        try {
            FileReader fileReader = new FileReader(recipe);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line=bufferedReader.readLine()) != null){
                // Storing the information part of the recipe
                if (stage == 1) {
                    if (line.startsWith("Name: ")) this.name = line.substring(6);
                    if (line.startsWith("Author: ")) this.author = line.substring(6);
                    if (line.startsWith("Description: ")) this.description = line.substring(12);
                    if (line.startsWith("Time: ")) this.time = line.substring(7);
                    if (line.startsWith("Servings: ")) this.servings = line.substring(10);
                    if (line.startsWith("Calories: ")) this.calories = line.substring(10);
                }
                // Storing the ingredients
                if (line.startsWith("Ingredients:")) {
                    this.ingredients = new ArrayList<Ingredient>();
                    stage =2;
                }
                Matcher matcher = ingredientPattern.matcher(line);
                if (matcher.find() && stage  == 2){
                    Double amount = Double.parseDouble(matcher.group(1));
                    //System.out.println(amount);
                    String unit = matcher.group(2).trim();

                    if (unit.equals("pound") || unit.equals("ounce")) {
                        Units newUnit = new Units(unit);
                        double newAmount = newUnit.getFactor() * amount;
                        newAmount = 10*(Math.ceil(Math.abs(newAmount/10)));
                        String newunit = "G";
                        String descript = matcher.group(3);
                        Ingredient ingredient = new Ingredient(newAmount, newunit, descript);
                        ingredients.add(ingredient);
                    }
                    else if(unit.equals("gallon") || unit.equals("quart") || unit.equals("pint") || unit.equals("cup")
                            || unit.equals("fluid ounce") || unit.equals("tablespoon") || unit.equals("teaspoon")
                            || unit.equals("dash") || unit.equals("pinch")){
                        Units newUnit = new Units(unit);
                        double newAmount = newUnit.getFactor() * amount;
                        newAmount = 10*(Math.ceil(Math.abs(newAmount/10)));
                        //System.out.println(newAmmount);
                        String newunit = "ML";
                        String descript = matcher.group(3);
                        Ingredient ingredient = new Ingredient(newAmount, newunit, descript);
                        ingredients.add(ingredient);
                    }
                    else {
                        String descript = matcher.group(3);
                        Ingredient ingredient = new Ingredient(amount, unit, descript);
                        ingredients.add(ingredient);
                    }
                }

                // Storing the description of the recipe
                if (line.startsWith("Directions:")) {
                    stage = 3;
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("unable to open file: '" + recipe + "'");
        } catch (IOException e ) {
            System.out.println("Input error!");
        }
    }

    public void setServings(double portions){
        List<Ingredient> newAmmountIngredients = new ArrayList<Ingredient>();
        double ratio = ( portions / Double.parseDouble(servings) );
        //System.out.println(ratio);
        for (Ingredient i : ingredients){
            Ingredient newAmountIngredient = new Ingredient(i.getAmount() * ratio , i.getUnit(),i.getDescription() );
            newAmmountIngredients.add(newAmountIngredient);
        }

        ingredients = new ArrayList<Ingredient>();
        ingredients.addAll(newAmmountIngredients);
    }


    public void addIngredients(List<Ingredient> groceryList, List<Ingredient> recipeIngredients){
        List<Ingredient> allIngredients = new ArrayList<Ingredient>();
        allIngredients.addAll(groceryList);

        for (int i =0 ; i < recipeIngredients.size() ; i++ ){
            Ingredient newIngredient = recipeIngredients.get(i);
            boolean ok = false;
            for (int j = 0 ;j< allIngredients.size(); j++){
                Ingredient  oldIngredient = allIngredients.get(j);
                if (oldIngredient.getDescription().equals(newIngredient.getDescription())){
                    ok = true;
                    groceryList.remove(oldIngredient);
                    double existingAmount = oldIngredient.getAmount();
                    double newAmount = newIngredient.getAmount();
                    double totalAmount = existingAmount + newAmount;
                    Ingredient finalIngredient = new Ingredient(totalAmount, oldIngredient.getUnit(), oldIngredient.getDescription());

                    groceryList.add(finalIngredient);
                }
            }
            if (!ok) {
                groceryList.add(newIngredient);
            }
        }
    }

    public double getServings(){
        return Double.parseDouble(servings);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public boolean checkState(){
        return !ingredients.isEmpty();
    }

    public void printIngredients(){
        NumberFormat nf = new DecimalFormat("##.##");
        for (Ingredient i : ingredients){
            System.out.printf("%-5s %s %s \n", nf.format(i.getAmount()), i.getUnit(), i.getDescription());
        }
        System.out.println();
    }
    public void printRecipeInformation(){
        System.out.printf("Name: %s\nAuthor: %s\nDescription: %s\nTime: %s\nServings: %s\nCalories: %s\n\n", name,author,description,time,servings,calories);
    }
}
