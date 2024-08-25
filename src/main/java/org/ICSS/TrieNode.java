package org.ICSS;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private Map<Character, TrieNode> children;
    private Double price;  // Store price at the end of prefix

    public TrieNode() {
        this.children = new HashMap<>();
        this.price = null;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

