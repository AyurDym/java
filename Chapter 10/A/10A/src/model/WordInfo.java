package model;

import java.util.Objects;

public class WordInfo {
    private String originalWord;
    private String processedWord;
    private int position;

    public WordInfo(String word, int position) {
        this.originalWord = word;
        this.processedWord = word;
        this.position = position;
    }

    public String getOriginalWord() { return originalWord; }
    public void setOriginalWord(String originalWord) { this.originalWord = originalWord; }

    public String getProcessedWord() { return processedWord; }
    public void setProcessedWord(String processedWord) { this.processedWord = processedWord; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public char getFirstChar() {
        return processedWord.length() > 0 ? processedWord.charAt(0) : '\0';
    }

    public char getLastChar() {
        return processedWord.length() > 0 ? processedWord.charAt(processedWord.length() - 1) : '\0';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordInfo wordInfo = (WordInfo) o;
        return Objects.equals(originalWord, wordInfo.originalWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalWord);
    }

    @Override
    public String toString() {
        return processedWord;
    }
}
