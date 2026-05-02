package service;

import model.Line;
import model.Poem;
import java.util.List;
import java.util.ArrayList;

public class LineSorter {

    // Сортировка строк стихотворения по возрастанию длины
    public List<Line> sortLinesByLengthAscending(Poem poem) {
        List<Line> sortedLines = new ArrayList<>(poem.getLines());
        sortedLines.sort((line1, line2) ->
                Integer.compare(line1.getLength(), line2.getLength())
        );
        return sortedLines;
    }

    // Сортировка строк стихотворения по убыванию длины
    public List<Line> sortLinesByLengthDescending(Poem poem) {
        List<Line> sortedLines = new ArrayList<>(poem.getLines());
        sortedLines.sort((line1, line2) ->
                Integer.compare(line2.getLength(), line1.getLength())
        );
        return sortedLines;
    }

    // Вывод отсортированных строк
    public void printSortedLines(Poem poem, boolean ascending) {
        List<Line> sortedLines = ascending ?
                sortLinesByLengthAscending(poem) :
                sortLinesByLengthDescending(poem);

        System.out.println("\n--- Строки стихотворения \"" + poem.getTitle() +
                "\" (сортировка по " + (ascending ? "возрастанию" : "убыванию") + " длины) ---");

        for (Line line : sortedLines) {
            System.out.printf("%-3d | %s\n", line.getLength(), line.getText());
        }
    }

    // Сравнение длины строк двух стихотворений
    public void comparePoemsLength(Poem poem1, Poem poem2) {
        System.out.println("\n--- Сравнение стихотворений ---");
        System.out.printf("\"%s\" - средняя длина строки: %.1f\n",
                poem1.getTitle(), poem1.getAverageLineLength());
        System.out.printf("\"%s\" - средняя длина строки: %.1f\n",
                poem2.getTitle(), poem2.getAverageLineLength());

        if (poem1.getAverageLineLength() > poem2.getAverageLineLength()) {
            System.out.println("Строки в \"" + poem1.getTitle() + "\" в среднем длиннее");
        } else if (poem1.getAverageLineLength() < poem2.getAverageLineLength()) {
            System.out.println("Строки в \"" + poem2.getTitle() + "\" в среднем длиннее");
        } else {
            System.out.println("Средняя длина строк одинакова");
        }
    }
}
