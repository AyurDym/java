package com.example;

import java.util.*;

public class Paragraph {
    private List<Sentence> sentences;

    public Paragraph() {
        this.sentences = new ArrayList<>();
    }

    public void addSentence(Sentence sentence) {
        sentences.add(sentence);
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public List<Sentence> getInterrogativeSentences() {
        List<Sentence> interrogative = new ArrayList<>();
        for (Sentence sentence : sentences) {
            if (sentence.isInterrogative()) {
                interrogative.add(sentence);
            }
        }
        return interrogative;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sentence sentence : sentences) {
            sb.append(sentence.toString());
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}