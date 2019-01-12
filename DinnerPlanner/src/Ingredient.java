public class Ingredient {
    private double amount;
    private String unit, description;

    public Ingredient(Double amount, String unit, String description){
        this.amount = amount;
        this.unit = unit;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }
}
