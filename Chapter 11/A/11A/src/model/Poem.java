package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Poem implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String author;
    private List<Line> lines;
    private int year;

    public Poem(String title, String author) {
        this.title = title;
        this.author = author;
        this.lines = new ArrayList<>();
        this.year = 0;
    }

    public Poem(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.lines = new ArrayList<>();
    }

    // Геттеры и сеттеры
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public List<Line> getLines() { return lines; }
    public void setLines(List<Line> lines) { this.lines = lines; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    // Добавление строки
    public void addLine(String text) {
        int lineNumber = lines.size() + 1;
        lines.add(new Line(text, lineNumber));
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    // Получение строк, отсортированных по длине
    public List<Line> getLinesSortedByLength() {
        List<Line> sortedLines = new ArrayList<>(lines);
        sortedLines.sort(null); // Использует compareTo из Line
        return sortedLines;
    }

    // Получение максимальной длины строки
    public int getMaxLineLength() {
        return lines.stream()
                .mapToInt(Line::getLength)
                .max()
                .orElse(0);
    }

    // Получение минимальной длины строки
    public int getMinLineLength() {
        return lines.stream()
                .mapToInt(Line::getLength)
                .min()
                .orElse(0);
    }

    // Получение средней длины строки
    public double getAverageLineLength() {
        return lines.stream()
                .mapToInt(Line::getLength)
                .average()
                .orElse(0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========================================\n");
        sb.append("Стихотворение: \"").append(title).append("\"\n");
        sb.append("Автор: ").append(author);
        if (year > 0) {
            sb.append(" (").append(year).append(")");
        }
        sb.append("\n");
        sb.append("----------------------------------------\n");

        for (Line line : lines) {
            sb.append(line.getText()).append("\n");
        }

        sb.append("----------------------------------------\n");
        sb.append("Всего строк: ").append(lines.size()).append("\n");
        sb.append("Длина строк: от ").append(getMinLineLength())
                .append(" до ").append(getMaxLineLength())
                .append(", средняя: ").append(String.format("%.1f", getAverageLineLength()))
                .append("\n");
        sb.append("========================================\n");

        return sb.toString();
    }
}
