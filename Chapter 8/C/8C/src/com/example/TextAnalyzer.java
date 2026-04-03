package com.example;

import java.util.*;

public class TextAnalyzer {
    private List<Sentence> sentences;
    private List<List<Integer>> graph; // Граф общих слов

    public TextAnalyzer(String text) {
        this.sentences = parseSentences(text);
        this.graph = buildGraph();
    }

    private List<Sentence> parseSentences(String text) {
        // Нормализация текста
        String normalized = text.replaceAll("\\t+", " ")
                .replaceAll("\\s+", " ")
                .trim();

        // Разбиваем на предложения по .!? с сохранением знаков препинания
        String[] sentenceStrings = normalized.split("(?<=[.!?])\\s+");

        List<Sentence> result = new ArrayList<>();
        for (int i = 0; i < sentenceStrings.length; i++) {
            if (!sentenceStrings[i].trim().isEmpty()) {
                result.add(new Sentence(sentenceStrings[i].trim(), i));
            }
        }

        return result;
    }

    private List<List<Integer>> buildGraph() {
        int n = sentences.size();
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            for (int j = 0; j < n; j++) {
                if (i != j && sentences.get(i).hasCommonWords(sentences.get(j))) {
                    graph.get(i).add(j);
                }
            }
        }

        return graph;
    }

    public Set<Integer> findMinRemovalSet() {
        int n = sentences.size();
        Set<Integer> minRemoval = new HashSet<>();

        // Перебираем все возможные комбинации удаления (методом ветвей и границ)
        findAllValidSets(new HashSet<>(), 0, minRemoval);

        return minRemoval;
    }

    private void findAllValidSets(Set<Integer> removed, int start, Set<Integer> best) {
        if (isValid(removed)) {
            if (best.isEmpty() || removed.size() < best.size()) {
                best.clear();
                best.addAll(removed);
            }
            return;
        }

        // Если уже удалили больше или равно лучшему решению, прекращаем
        if (!best.isEmpty() && removed.size() >= best.size()) {
            return;
        }

        for (int i = start; i < sentences.size(); i++) {
            if (!removed.contains(i)) {
                removed.add(i);
                findAllValidSets(removed, i + 1, best);
                removed.remove(i);
            }
        }
    }

    private boolean isValid(Set<Integer> removed) {
        List<Sentence> remaining = new ArrayList<>();
        for (int i = 0; i < sentences.size(); i++) {
            if (!removed.contains(i)) {
                remaining.add(sentences.get(i));
            }
        }

        // Проверяем, что у любых двух оставшихся предложений есть общее слово
        for (int i = 0; i < remaining.size(); i++) {
            for (int j = i + 1; j < remaining.size(); j++) {
                if (!remaining.get(i).hasCommonWords(remaining.get(j))) {
                    return false;
                }
            }
        }

        return true;
    }

    public void printResult() {
        System.out.println("=" .repeat(80));
        System.out.println("АНАЛИЗ ТЕКСТА");
        System.out.println("=" .repeat(80));

        System.out.println("\nИсходные предложения:");
        for (Sentence s : sentences) {
            System.out.println(s);
            System.out.println("  Слова: " + s.getWords());
        }

        // Вывод матрицы общих слов
        System.out.println("\n" + "=" .repeat(80));
        System.out.println("МАТРИЦА ОБЩИХ СЛОВ:");
        System.out.println("=" .repeat(80));
        printCommonWordsMatrix();

        // Поиск минимального набора для удаления
        Set<Integer> toRemove = findMinRemovalSet();

        System.out.println("\n" + "=" .repeat(80));
        System.out.println("РЕЗУЛЬТАТ:");
        System.out.println("=" .repeat(80));

        if (toRemove.isEmpty()) {
            System.out.println("\n Все предложения уже удовлетворяют условию!");
            System.out.println("  У любых двух предложений есть общее слово.");
        } else {
            System.out.println("\n Необходимо удалить " + toRemove.size() + " предложение(ий):");
            for (int index : toRemove) {
                System.out.println("  • Предложение " + (index + 1) + ": " + sentences.get(index).getOriginalText());
            }
        }

        System.out.println("\n" + "=" .repeat(80));
        System.out.println("ИТОГОВЫЙ ТЕКСТ:");
        System.out.println("=" .repeat(80));

        List<Sentence> remaining = new ArrayList<>();
        for (int i = 0; i < sentences.size(); i++) {
            if (!toRemove.contains(i)) {
                remaining.add(sentences.get(i));
            }
        }

        for (Sentence s : remaining) {
            System.out.println(s.getOriginalText() + " ");
        }

        // Проверка результата
        System.out.println("\n" + "=" .repeat(80));
        System.out.println("ПРОВЕРКА УСЛОВИЯ:");
        System.out.println("=" .repeat(80));

        boolean valid = true;
        for (int i = 0; i < remaining.size(); i++) {
            for (int j = i + 1; j < remaining.size(); j++) {
                boolean hasCommon = remaining.get(i).hasCommonWords(remaining.get(j));
                System.out.printf("Предложение %d и %d: %s%n",
                        remaining.get(i).getIndex() + 1,
                        remaining.get(j).getIndex() + 1,
                        hasCommon ? "✓ есть общее слово" : "✗ НЕТ общего слова");
                if (!hasCommon) valid = false;
            }
        }

        if (valid && remaining.size() >= 2) {
            System.out.println("\n УСЛОВИЕ ВЫПОЛНЕНО!");
        } else if (remaining.size() < 2) {
            System.out.println("\n Осталось менее 2 предложений");
        }
    }

    private void printCommonWordsMatrix() {
        int n = sentences.size();

        // Заголовок
        System.out.print("     ");
        for (int i = 0; i < n; i++) {
            System.out.printf("  %2d  ", i + 1);
        }
        System.out.println();

        System.out.print("     ");
        for (int i = 0; i < n; i++) {
            System.out.print("-----");
        }
        System.out.println();

        // Матрица
        for (int i = 0; i < n; i++) {
            System.out.printf("%2d | ", i + 1);
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    System.out.print("  -  ");
                } else {
                    boolean hasCommon = sentences.get(i).hasCommonWords(sentences.get(j));
                    System.out.print(hasCommon ? "  ✓  " : "  ✗  ");
                }
            }
            System.out.println();
        }
    }
}