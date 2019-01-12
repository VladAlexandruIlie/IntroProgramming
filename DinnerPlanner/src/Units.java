public class Units {
    final private double GALLONS_TO_ML     = 3785.41;
    final private double QUARTS_TO_ML      = 946.353;
    final private double PINTS_TO_ML       = 473.176;
    final private double CUPS_TO_ML        = 240;
    final private double FLUIDOUNCES_TO_ML = 29.57;
    final private double TABLESPOONS_TO_ML = 14.786;
    final private double TEASPONS_TO_ML    = 4.92;
    final private double DASHS_TO_ML       = 0.61;
    final private double PINCHES_TO_ML     = 0.31;

    final private double POUNDS_TO_G = 453.592 ;
    final private double OUNCES_TO_G = 28.349;

    private double factor;

    public Units(String unit){
        switch (unit){
            case "dash":            {factor = DASHS_TO_ML; break;}
            case "teaspoon":        {factor = TEASPONS_TO_ML; break;}
            case "tablespoon":      {factor = TABLESPOONS_TO_ML; break; }
            case "fluid ounce":     {factor = FLUIDOUNCES_TO_ML; break;}
            case "cup":             {factor = CUPS_TO_ML; break; }
            case "pint":            {factor = PINTS_TO_ML; break; }
            case "quart":           {factor = QUARTS_TO_ML; break; }
            case "gallon":          {factor = GALLONS_TO_ML; break; }
            case "pound":           {factor = POUNDS_TO_G; break;}
            case "ounce":           {factor = OUNCES_TO_G; break;}
            case "pinch":           {factor = PINCHES_TO_ML; break;}
        }
        //System.out.println(factor);
    }

    public double toML(double measurement){
        return factor * measurement;
    }

    public double toMG(double measurement){
        return factor  * measurement;
    }

    public double getFactor(){
        return factor;
    }

}
