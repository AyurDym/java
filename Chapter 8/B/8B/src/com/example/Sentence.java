package com.example;

import java.util.*;

public class Sentence {
    private List<Lexeme> lexemes;
    private Punctuation endingPunctuation;

    public Sentence() {
        this.lexemes = new ArrayList<>();
    }

    public void addLexeme(Lexeme lexeme) {
        lexemes.add(lexeme);
    }

    public void setEndingPunctuation(Punctuation punctuation) {
        this.endingPunctuation = punctuation;
    }

    public boolean isInterrogative() {
        return endingPunctuation != null && endingPunctuation.isQuestionMark();
    }

    public List<Word> getAllWords() {
        List<Word> words = new ArrayList<>();
        for (Lexeme lexeme : lexemes) {
            words.addAll(lexeme.getWords());
        }
        return words;
    }

    public Set<Word> getUniqueWordsOfLength(int length) {
        Set<Word> uniqueWords = new HashSet<>();
        for (Word word : getAllWords()) {
            if (word.getLength() == length) {
                uniqueWords.add(word);
            }
        }
        return uniqueWords;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lexemes.size(); i++) {
            sb.append(lexemes.get(i).toString());
            if (i < lexemes.size() - 1) {
                sb.append(" ");
            }
        }
        if (endingPunctuation != null) {
            sb.append(endingPunctuation.toString());
        }
        return sb.toString();
    }
}