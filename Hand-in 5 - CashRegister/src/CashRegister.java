import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

public class CashRegister {
    private Map<String, List<String>> prices;
    private Map<String, List<String>> discounts;

    public CashRegister(String priceFilename, String discountsFilename) {
        String pricesFilePath = "C:\\Users\\iliev\\Desktop\\CaseRegister\\data\\" + priceFilename;
        String discountsFilePath = "C:\\Users\\iliev\\Desktop\\CaseRegister\\data\\" + discountsFilename;
        String line;
        this.prices = new HashMap<>();
        this.discounts = new HashMap<>();
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(priceFilename);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                List<String> entry = new ArrayList<String>();
                for (String s : parts){entry.add(s);}
                String barcode = entry.get(0);
                List<String> description = new ArrayList<String>();
                for (int i =1; i<=4 ; i++){
                    description.add(entry.get(i));
                }
                prices.put(barcode,description);
            }
            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + priceFilename + "'");
        }
        catch(IOException ex) {
            System.out.println( "Error reading file '" + priceFilename + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(discountsFilename);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                List<String> entry = new ArrayList<String>();
                for(String s: parts) {entry.add(s);}
                String barcode = entry.get(0);
                List<String> description = new ArrayList<String>();
                for (int i =1 ;i<=3; i++ ){
                    description.add(entry.get(i));
                }
                discounts.put(barcode,description);
                //System.out.println(line);
            }
            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + discountsFilename + "'");
        }
        catch(IOException ex) {
            System.out.println( "Error reading file '" + discountsFilename + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        /*
        //Check if the map was created correctly
        for (String barcode: prices.keySet()){
            System.out.print("K: " + barcode + " -> V: ");
            for (String s : prices.get(barcode)){
                System.out.print(s + ", ");
            }
            System.out.println(" ");
        }
        for (String barcode: discounts.keySet()){
            System.out.print("K: " + barcode + " -> V: ");
            for (String s : discounts.get(barcode)){
                System.out.print(s + ", ");
            }
            System.out.println(" ");
        }
        */
    }

    public void printReceipt(String barcodeFilename){
        String barcodeFilePath = "C:\\Users\\iliev\\Desktop\\CaseRegister\\data\\" + barcodeFilename;
        List<String> barcodes = new ArrayList<String>();
        List<String> categories = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        Map<String, Integer> itemsBought = new HashMap<String, Integer>();
        double total = 0, totalDiscount = 0;
        try
        {
            String line;
            FileReader fileReader = new FileReader(barcodeFilename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null){
                barcodes.add(line);
            }
        }
        catch (FileNotFoundException ex){
            System.out.println("Unable to open file: " + barcodeFilename);
        }
        catch (IOException e){
            System.out.println("Unable to read the file: " + barcodeFilename);
        }


        for (int i= 0; i< barcodes.size() ; i++  ) {
            String itemCode = barcodes.get(i);
            String name = prices.get(itemCode).get(1);
            String category = prices.get(itemCode).get(0);
            int number = 1;
            for (int j=0 ; j< barcodes.size() ; j++)
            {
                if (i != j && barcodes.get(i).equals(barcodes.get(j))) number ++;
            }
            if (!itemsBought.containsKey(barcodes.get(i))) {
                itemsBought.put(itemCode, number);
            }
            if (!categories.contains(category)) {
                categories.add(category);
                }
            if (!names.contains(name)){
                names.add(name);
            }
        }
        categories.sort(String.CASE_INSENSITIVE_ORDER);
        names.sort(String.CASE_INSENSITIVE_ORDER);

        for(int i =0; i< categories.size(); i++ ) {
            String catname = "* " + categories.get(i) + " *";
            int width = 37;

            int padSize = width - catname.length();
            if (catname.length()%2 == 1 ) {padSize = padSize-1;}
            int padStart = catname.length() + padSize / 2;
            catname = String.format("%" + padStart + "s", catname);
            System.out.println("");
            System.out.println(catname);

            for (int j=0 ; j< names.size() ; j++ )
            {
                String productName = names.get(j);

                for (String s : itemsBought.keySet()) {
                    String itemName = prices.get(s).get(1);
                    String category = prices.get(s).get(0);

                    if (itemName.equals(productName) && category.equals(categories.get(i))) {
                        int quantity = itemsBought.get(s);
                        String krr = prices.get(s).get(2);
                        String ore = prices.get(s).get(3);
                        int kr = Integer.parseInt(krr);
                        int or = Integer.parseInt(ore);
                        double cost = kr + or / Math.pow(10, ore.length());

                        String finalcost = String.format("%.2f", quantity * cost);
                        finalcost = finalcost.replace(".", ",");

                        String price = prices.get(s).get(2) + "," + prices.get(s).get(3);

                        String finalcost2 = "";
                        boolean ok = false;
                        Integer limit = 0, kr2 = 0, or2 = 0;
                        double cost2 = 0;
                        if ((discounts.keySet()).contains(s)) {
                            ok = true;
                            limit = Integer.parseInt(discounts.get(s).get(0));
                            kr2 = Integer.parseInt(discounts.get(s).get(1));
                            or2 = Integer.parseInt(discounts.get(s).get(2));
                            cost2 = quantity * (cost - (kr2 + or2 / Math.pow(10, discounts.get(s).get(2).length())));

                            finalcost2 = String.format("%.2f", cost2);
                            finalcost2 = finalcost2.replace(".", ",");
                            //System.out.println(finalcost2);
                        }

                        if (quantity == 1 && !ok && categories.get(i).equals(category)) {
                            System.out.printf("%-29s%8s\n", itemName, finalcost);
                            total = total + cost;
                        } else if (quantity == 1 && ok && categories.get(i).equals(category)) {
                            System.out.printf("%-29s%8s\n", itemName, finalcost);
                            if (quantity >= limit) {
                                System.out.printf("%s %31s-\n", "RABAT", finalcost2);

                                totalDiscount = totalDiscount + cost2;
                            }
                            total = total + cost;
                        } else if (quantity > 1 && !ok && categories.get(i).equals(category)) {
                            System.out.printf("%s\n  %-25s%10s\n", itemName, quantity + " x " + price, finalcost);
                            total = total + cost * quantity;
                        } else if (quantity > 1 && ok && categories.get(i).equals(category)) {
                            if (quantity >= limit) {
                                System.out.printf("%s\n  %-27s%8s\n", itemName, quantity + " x " + price, finalcost);
                                System.out.printf("%-29s%8s-\n", "RABAT", finalcost2);
                                total = total + cost * quantity;
                                totalDiscount = totalDiscount + cost2;
                            } else {
                                System.out.printf("%-38s \n  %-34s %s\n", itemName, quantity + " x " + price, finalcost);
                                total = total + cost * quantity;
                            }
                        }
                    }

                }
            }
        }

        System.out.println("\n-------------------------------------\n");
        System.out.printf("%-18s%19s\n\n" ,"SUBTOT", String.format("%.2f", total).replace(".",","));
        if (totalDiscount>0) {
            System.out.printf("%-18s%19s\n\n" ,"RABAT", String.format("%.2f", totalDiscount).replace(".",","));
        }
        System.out.printf("%-18s%19s\n\n" ,"TOTAL", String.format("%.2f", (total - totalDiscount)).replace(".",","));
        Integer total2 = (int)total;
        Integer totalDiscounts2 = (int)totalDiscount;
        Integer markers = (total2 -totalDiscounts2)/50;
        System.out.println("KØBET HAR UDLØST " + markers + " MÆRKER\n");
        System.out.printf("%-18s%19s\n\n" ,"MOMS UDGØR" ,String.format("%.2f", (total-totalDiscount)*0.2).replace(".",","));

        }

    }

