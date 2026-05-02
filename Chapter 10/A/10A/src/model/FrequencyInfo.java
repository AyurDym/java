package model;

import java.util.Objects;

public class FrequencyInfo implements Comparable<FrequencyInfo> {
    private String word;
    private int frequency;

    public FrequencyInfo(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public int getFrequency() { return frequency; }
    public void setFrequency(int frequency) { this.frequency = frequency; }

    public void increment() {
        this.frequency++;
    }

    @Override
    public int compareTo(FrequencyInfo other) {
        return Integer.compare(this.frequency, other.frequency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrequencyInfo that = (FrequencyInfo) o;
        return Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    @Override
    public String toString() {
        return word + ": " + frequency;
    }
}