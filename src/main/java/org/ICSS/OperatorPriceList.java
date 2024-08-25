package org.ICSS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OperatorPriceList {
    private Map<String, Trie> operatorTries;

    public OperatorPriceList() {
        this.operatorTries = new HashMap<>();
    }

    public void addOperator(String operator, Map<String, Double> prefixes) {
        Trie trie = new Trie();
        for (Map.Entry<String, Double> entry : prefixes.entrySet()) {
            trie.insert(entry.getKey(), entry.getValue());
        }
        operatorTries.put(operator, trie);
    }

    public void loadPriceListsFromFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        String currentOperator = null;
        Map<String, Double> currentPrefixes = null;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("Operator")) {
                if (currentOperator != null && currentPrefixes != null) {
                    addOperator(currentOperator, currentPrefixes);
                }
                currentOperator = line.replace(":", "");
                currentPrefixes = new HashMap<>();
            } else {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    String prefix = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    if (currentPrefixes != null) {
                        currentPrefixes.put(prefix, price);
                    }
                }
            }
        }

        // Add the last operator in the file
        if (currentOperator != null && currentPrefixes != null) {
            addOperator(currentOperator, currentPrefixes);
        }

        scanner.close();
    }

    public String findCheapestOperator(String number) {
        String cheapestOperator = null;
        Double minPrice = Double.MAX_VALUE;
        int maxPrefixLength = 0;

        for (Map.Entry<String, Trie> entry : operatorTries.entrySet()) {
            String operator = entry.getKey();
            Trie trie = entry.getValue();
            Trie.Match match = trie.findLongestMatchingPrefixPrice(number);

            if (match != null) {
                int prefixLength = match.prefix.length();
                double price = match.price;

                // Prioritize the longest prefix match, and then the lowest price
                if (prefixLength > maxPrefixLength ||
                        (prefixLength == maxPrefixLength && price < minPrice)) {
                    maxPrefixLength = prefixLength;
                    minPrice = price;
                    cheapestOperator = operator;
                }
            }
        }

        return cheapestOperator;
    }
}
