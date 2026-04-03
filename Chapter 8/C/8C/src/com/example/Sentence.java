package com.example;

import java.util.*;
import java.util.stream.Collectors;

public class Sentence {
    private String originalText;
    private Set<Word> words;
    private int index;

    public Sentence(String text, int index) {
        this.originalText = text;
        this.index = index;
        this.words = extractWords(text);
    }

    private Set<Word> extractWords(String text) {
        // Нормализация: замена табуляций и множественных пробелов
        String normalized = text.replaceAll("\\t+", " ")
                .replaceAll("\\s+", " ")
                .trim();

        // Удаляем знаки препинания и разбиваем на слова
        String[] wordArray = normalized.replaceAll("[^a-zA-Zа-яА-Я0-9\\s]", "")
                .split("\\s+");

        Set<Word> wordSet = new HashSet<>();
        for (String w : wordArray) {
            if (!w.isEmpty() && w.length() > 0) {
                wordSet.add(new Word(w));
            }
        }

        return wordSet;
    }

    public Set<Word> getWords() {
        return words;
    }

    public int getIndex() {
        return index;
    }

    public String getOriginalText() {
        return originalText;
    }

    public boolean hasCommonWords(Sentence other) {
        Set<Word> intersection = new HashSet<>(this.words);
        intersection.retainAll(other.words);
        return !intersection.isEmpty();
    }

    @Override
    public String toString() {
        return "Предложение " + (index + 1) + ": " + originalText;
    }
}