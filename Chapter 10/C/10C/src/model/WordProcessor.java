package model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordProcessor {

    // Метод для преобразования слова: если длина > 2, то в верхний регистр
    public static String processWord(String word) {
        if (word.length() > 2) {
            return word.toUpperCase();
        }
        return word;
    }

    // Метод для обработки строки с сохранением структуры (пробелы, знаки препинания)
    public static String processLine(String line) {
        if (line == null || line.isEmpty()) {
            return line;
        }

        // Регулярное выражение для поиска слов (последовательности букв)
        Pattern pattern = Pattern.compile("\\b\\p{L}+\\b");
        Matcher matcher = pattern.matcher(line);

        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String word = matcher.group();
            String processedWord = processWord(word);
            matcher.appendReplacement(result, Matcher.quoteReplacement(processedWord));
        }
        matcher.appendTail(result);

        return result.toString();
    }

    // Метод для обработки всего текста
    public static List<String> processText(List<String> lines) {
        List<String> processedLines = new ArrayList<>();
        for (String line : lines) {
            processedLines.add(processLine(line));
        }
        return processedLines;
    }
}
