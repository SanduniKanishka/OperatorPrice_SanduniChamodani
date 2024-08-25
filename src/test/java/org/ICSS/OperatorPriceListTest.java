package org.ICSS;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
public class OperatorPriceListTest {

    private OperatorPriceList operatorPriceList;
    private String filePath;

    @Before
    public void setUp() {
        operatorPriceList = new OperatorPriceList();
        filePath = "src/main/resources/price_list.txt";

        try {
            operatorPriceList.loadPriceListsFromFile(filePath);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        }
    }

    @Test
    public void testWhenPrefixDoesNotExist() {
        String number = "99912345678";
        String actual = operatorPriceList.findCheapestOperator(number);
        assertNull(actual, "There should be no matching operator for " + number);
    }

    @Test
    public void testWhenSameLengthPrefixesExist() {
        String number = "46732123456"; // Both Operator A and B have prefix '4673'
        String expected = "Operator A"; // Operator A has the cheaper price
        String actual = operatorPriceList.findCheapestOperator(number);

//        assertEquals(expected,actual);
        assertEquals(expected, actual, "The cheapest operator for " + number + " should be " + expected);
    }

    @Test
    public void testWhenShorterPrefixExists() {
        String number = "467123456";
        String expected = "Operator B"; // Operator B has the prefix '467' with a lower price than Operator A's '46'
        String actual = operatorPriceList.findCheapestOperator(number);

        assertEquals(expected, actual, "The cheapest operator for " + number + " should be " + expected);
    }

    @Test
    public void testWhenOnlyOneOperatorHasMatchingPrefix() {
        String number = "4487654321"; // Only Operator B has the prefix '44'
        String expected = "Operator B";
        String actual = operatorPriceList.findCheapestOperator(number);

        assertEquals(expected, actual, "The cheapest operator for " + number + " should be " + expected);
    }

    @Test
    public void testWhenNumberMatchesLongerPrefix() {
        String number = "46732123456";
        String expected = "Operator A"; // Operator A has the longer prefix '46732'
        String actual = operatorPriceList.findCheapestOperator(number);

        assertEquals(expected, actual, "The cheapest operator for " + number + " should be " + expected);
    }

    @Test
    public void testWhenNumberMatchesMultiplePrefixes() {
        String number = "4620123456";
        String expected = "Operator A"; // Multiple prefixes match but '4620' is the longest and belongs to Operator A
        String actual = operatorPriceList.findCheapestOperator(number);

        assertEquals(expected, actual, "The cheapest operator for " + number + " should be " + expected);
    }

    @Test
    public void testWhenExactPrefixMatches() {
        String number = "26812345678";
        String expected = "Operator A"; // Exact match with Operator A's prefix '268'
        String actual = operatorPriceList.findCheapestOperator(number);

        assertEquals(expected, actual, "The cheapest operator for " + number + " should be " + expected);
    }

    @Test
    public void testWhenStringIsShorterThanAnyPrefix() {
        String number = "46";  // Input number is shorter than some of the prefixes like "4620" or "46732"
        String expected = "Operator A";  // Operator A has the cheaper price for prefix "46"
        String actual = operatorPriceList.findCheapestOperator(number);
        assertEquals(expected, actual, "The cheapest operator for " + number + " should be " + expected);
    }
}
