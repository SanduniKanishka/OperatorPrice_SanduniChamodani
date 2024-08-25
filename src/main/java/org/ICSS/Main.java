package org.ICSS;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        OperatorPriceList operatorPriceList = new OperatorPriceList();
        String filePath = "src/main/resources/price_list.txt";

        try {
            operatorPriceList.loadPriceListsFromFile(filePath);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the telephone number:");
        String inputNumber = scanner.nextLine();

        String cheapestOperator = operatorPriceList.findCheapestOperator(inputNumber);

        if (cheapestOperator != null) {
            System.out.println("The cheapest operator for " + inputNumber + " is: " + cheapestOperator);
        } else {
            System.out.println("No operator found for " + inputNumber);
        }
    }
}
