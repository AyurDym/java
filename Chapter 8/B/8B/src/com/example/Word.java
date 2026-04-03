package com.example;

import java.util.*;

public class Word {
    private List<Symbol> symbols;

    public Word() {
        this.symbols = new ArrayList<>();
    }

    public Word(String word) {
        this.symbols = new ArrayList<>();
        for (char c : word.toCharArray()) {
            symbols.add(new Symbol(c));
        }
    }

    public void addSymbol(Symbol symbol) {
        symbols.add(symbol);
    }

    public int getLength() {
        return symbols.size();
    }

    public String getValue() {
        StringBuilder sb = new StringBuilder();
        for (Symbol s : symbols) {
            sb.append(s.getValue());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Word word = (Word) obj;
        return getValue().equalsIgnoreCase(word.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return getValue();
    }
}