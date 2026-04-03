package com.example;

import java.util.*;

public class Lexeme {
    private List<Object> parts; // Word или Punctuation

    public Lexeme() {
        this.parts = new ArrayList<>();
    }

    public void addPart(Object part) {
        parts.add(part);
    }

    public List<Object> getParts() {
        return parts;
    }

    public List<Word> getWords() {
        List<Word> words = new ArrayList<>();
        for (Object part : parts) {
            if (part instanceof Word) {
                words.add((Word) part);
            }
        }
        return words;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Object part : parts) {
            sb.append(part.toString());
        }
        return sb.toString();
    }
}