
/**
 * Write a description of class Tree here.
 *
 * @author Ilie Vlad Alexandru
 * @version 
 */
public class Tree
{
    private int age;
    private double height;
    private double growthPct;
    
    public Tree(double growthPct)
    {
        age = 1;
        height = 0.25;
        this.growthPct = growthPct;
    }

    public String toString ()
    {
        return "Tree(age = " + age + ", height = " + height + ")";
    }
    
    public void growOneYear()
    {
        if (height < 20 )
            {
                age ++;
                if (height * (1 + growthPct / 100) > 20) 
                    {
                    height = 20;
                    }
                  else 
                    {
                    height = height * (1 + growthPct / 100);
                    }
            } 
            else 
            {
                age ++;
            }
    }
}
