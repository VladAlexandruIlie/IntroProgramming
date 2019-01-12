public class Main {

    public static void main(String[] args) {
        //System.out.println("Hello World!");

        CashRegister cashRegister = new CashRegister("prices.txt", "discounts.txt");
        cashRegister.printReceipt("bar3.txt");
    }
}
