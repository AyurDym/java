package model;

import java.util.*;

public class TextAnalysisResult {
    private List<String> originalLines;
    private List<String> processedLines;
    private Map<Character, Integer> letterFrequency;
    private Map<String, Integer> wordFrequency;
    private List<FrequencyInfo> sortedWordFrequency;
    private List<String> wordsStartingWithVowel;
    private List<String[]> wordChains;
    private Map<String, Integer> maxDigitSequences;

    public TextAnalysisResult() {
        this.originalLines = new ArrayList<>();
        this.processedLines = new ArrayList<>();
        this.letterFrequency = new HashMap<>();
        this.wordFrequency = new HashMap<>();
        this.sortedWordFrequency = new ArrayList<>();
        this.wordsStartingWithVowel = new ArrayList<>();
        this.wordChains = new ArrayList<>();
        this.maxDigitSequences = new HashMap<>();
    }

    // Геттеры и сеттеры
    public List<String> getOriginalLines() { return originalLines; }
    public void setOriginalLines(List<String> originalLines) { this.originalLines = originalLines; }

    public List<String> getProcessedLines() { return processedLines; }
    public void setProcessedLines(List<String> processedLines) { this.processedLines = processedLines; }

    public Map<Character, Integer> getLetterFrequency() { return letterFrequency; }
    public void setLetterFrequency(Map<Character, Integer> letterFrequency) { this.letterFrequency = letterFrequency; }

    public Map<String, Integer> getWordFrequency() { return wordFrequency; }
    public void setWordFrequency(Map<String, Integer> wordFrequency) { this.wordFrequency = wordFrequency; }

    public List<FrequencyInfo> getSortedWordFrequency() { return sortedWordFrequency; }
    public void setSortedWordFrequency(List<FrequencyInfo> sortedWordFrequency) { this.sortedWordFrequency = sortedWordFrequency; }

    public List<String> getWordsStartingWithVowel() { return wordsStartingWithVowel; }
    public void setWordsStartingWithVowel(List<String> wordsStartingWithVowel) { this.wordsStartingWithVowel = wordsStartingWithVowel; }

    public List<String[]> getWordChains() { return wordChains; }
    public void setWordChains(List<String[]> wordChains) { this.wordChains = wordChains; }

    public Map<String, Integer> getMaxDigitSequences() { return maxDigitSequences; }
    public void setMaxDigitSequences(Map<String, Integer> maxDigitSequences) { this.maxDigitSequences = maxDigitSequences; }
}
