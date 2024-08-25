package org.ICSS;

public class Trie {
    private TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String prefix, double price) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            node.getChildren().putIfAbsent(ch, new TrieNode());
            node = node.getChildren().get(ch);
        }
        node.setPrice(price);  // Set the price at the end of the prefix
    }

    public static class Match {
        public String prefix;
        public double price;

        public Match(String prefix, double price) {
            this.prefix = prefix;
            this.price = price;
        }
    }

    public Match findLongestMatchingPrefixPrice(String number) {
        TrieNode node = root;
        String longestPrefix = null;
        Double price = null;

        StringBuilder currentPrefix = new StringBuilder();

        for (char ch : number.toCharArray()) {
            if (node.getChildren().containsKey(ch)) {
                currentPrefix.append(ch);
                node = node.getChildren().get(ch);
                if (node.getPrice() != null) {
                    longestPrefix = currentPrefix.toString();
                    price = node.getPrice();
                }
            } else {
                break;
            }
        }

        return longestPrefix != null ? new Match(longestPrefix, price) : null;
    }
}



