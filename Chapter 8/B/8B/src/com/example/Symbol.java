package com.example;

public class Symbol {
    private char value;

    public Symbol(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public boolean isLetter() {
        return Character.isLetter(value);
    }

    public boolean isDigit() {
        return Character.isDigit(value);
    }

    public boolean isSpace() {
        return Character.isWhitespace(value);
    }

    public boolean isPunctuation() {
        return !isLetter() && !isDigit() && !isSpace();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
