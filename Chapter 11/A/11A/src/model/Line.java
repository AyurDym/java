package model;

import java.io.Serializable;
import java.util.Objects;

public class Line implements Comparable<Line>, Serializable {
    private static final long serialVersionUID = 1L;

    private String text;
    private int lineNumber;
    private int length;

    public Line(String text, int lineNumber) {
        this.text = text;
        this.lineNumber = lineNumber;
        this.length = text.length();
    }

    public String getText() { return text; }
    public void setText(String text) {
        this.text = text;
        this.length = text.length();
    }

    public int getLineNumber() { return lineNumber; }
    public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }

    public int getLength() { return length; }

    @Override
    public int compareTo(Line other) {
        // Сортировка по длине строки
        return Integer.compare(this.length, other.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return lineNumber == line.lineNumber &&
                Objects.equals(text, line.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, lineNumber);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (длина: %d)", lineNumber, text, length);
    }
}
