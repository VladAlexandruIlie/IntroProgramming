
/**
 * Write a description of class Tree here.
 *
 * @author Ilie Vlad Alexandru
 * @version 
 */
public class Forest
{
    private Tree tree1, tree2, tree3;
    
    public Forest(double growthPct1, double growthPct2, double growthPct3)
    {
       tree1 = new Tree(growthPct1);
       tree2 = new Tree(growthPct2); 
       tree3 = new Tree(growthPct3);
    }
    
    public String toString()
    {
        return "Forest("+ tree1.toString() + ", " +
                          tree2.toString() + ", " +
                          tree3.toString() + ")";
    }
    
    public void growOneYear()
    {
        tree1.growOneYear();
        tree2.growOneYear();
        tree3.growOneYear();
    }
}
