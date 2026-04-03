package com.example;

import java.util.*;
import java.util.stream.Collectors;

public class AbiturientDatabase {
    private List<Abiturient> abiturients;

    // Конструкторы
    public AbiturientDatabase() {
        this.abiturients = new ArrayList<>();
    }

    public AbiturientDatabase(List<Abiturient> abiturients) {
        this.abiturients = new ArrayList<>(abiturients);
    }

    public AbiturientDatabase(Abiturient[] abiturients) {
        this.abiturients = new ArrayList<>(Arrays.asList(abiturients));
    }

    // Добавление абитуриента
    public void addAbiturient(Abiturient abiturient) {
        abiturients.add(abiturient);
    }

    public void addAbiturient(int id, String lastName, String firstName,
                              String patronymic, String address,
                              String phone, int[] grades) {
        abiturients.add(new Abiturient(id, lastName, firstName, patronymic,
                address, phone, grades));
    }

    // Геттер
    public List<Abiturient> getAllAbiturients() {
        return new ArrayList<>(abiturients);
    }

    // a) список абитуриентов, имеющих неудовлетворительные оценки
    public List<Abiturient> getAbiturientsWithUnsatisfactoryGrades() {
        return abiturients.stream()
                .filter(Abiturient::hasUnsatisfactoryGrades)
                .collect(Collectors.toList());
    }

    // b) список абитуриентов, у которых сумма баллов выше заданной
    public List<Abiturient> getAbiturientsWithSumAbove(int minSum) {
        return abiturients.stream()
                .filter(a -> a.getSumGrades() > minSum)
                .sorted(Comparator.comparingInt(Abiturient::getSumGrades).reversed())
                .collect(Collectors.toList());
    }

    // c) выбрать заданное число n абитуриентов, имеющих самую высокую сумму баллов
    public List<Abiturient> getTopNAbiturients(int n) {
        if (n <= 0) return new ArrayList<>();

        return abiturients.stream()
                .sorted(Comparator.comparingInt(Abiturient::getSumGrades).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    // Получить полупроходную сумму (минимальная сумма среди топ-n)
    public int getThresholdSumForTopN(int n) {
        if (n <= 0 || n > abiturients.size()) return 0;

        List<Abiturient> topN = getTopNAbiturients(n);
        if (topN.isEmpty()) return 0;

        return topN.get(topN.size() - 1).getSumGrades();
    }

    // Список абитуриентов, имеющих полупроходную сумму (такую же, как у последнего в топ-n)
    public List<Abiturient> getAbiturientsWithThresholdSum(int n) {
        int threshold = getThresholdSumForTopN(n);
        if (threshold == 0) return new ArrayList<>();

        return abiturients.stream()
                .filter(a -> a.getSumGrades() == threshold)
                .sorted(Comparator.comparingInt(Abiturient::getSumGrades).reversed())
                .collect(Collectors.toList());
    }

    // Полный список абитуриентов, имеющих полупроходную сумму (включая дополнительных)
    public void printTopNAndThreshold(int n) {
        List<Abiturient> topN = getTopNAbiturients(n);
        List<Abiturient> thresholdAbiturients = getAbiturientsWithThresholdSum(n);

        System.out.println("\n" + "=".repeat(80));
        System.out.println("ТОП-" + n + " АБИТУРИЕНТОВ С САМОЙ ВЫСОКОЙ СУММОЙ БАЛЛОВ:");
        System.out.println("=".repeat(80));

        if (topN.isEmpty()) {
            System.out.println("Недостаточно абитуриентов.");
        } else {
            for (int i = 0; i < topN.size(); i++) {
                System.out.printf("%d. %s (Сумма: %d)%n",
                        i + 1, topN.get(i).getFullName(), topN.get(i).getSumGrades());
            }
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("АБИТУРИЕНТЫ С ПОЛУПРОХОДНОЙ СУММОЙ (" +
                (thresholdAbiturients.isEmpty() ? "нет" :
                        "сумма = " + thresholdAbiturients.get(0).getSumGrades()) + "):");
        System.out.println("=".repeat(80));

        if (thresholdAbiturients.isEmpty()) {
            System.out.println("Нет абитуриентов с полупроходной суммой.");
        } else {
            for (Abiturient a : thresholdAbiturients) {
                System.out.printf("• %s (Сумма: %d)%n",
                        a.getFullName(), a.getSumGrades());
            }
        }
    }

    // Вывод всех абитуриентов
    public void printAllAbiturients() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ПОЛНЫЙ СПИСОК АБИТУРИЕНТОВ:");
        System.out.println("=".repeat(80));

        if (abiturients.isEmpty()) {
            System.out.println("Список пуст.");
        } else {
            for (Abiturient a : abiturients) {
                System.out.println(a);
            }
        }
    }

    // Сортировка по сумме баллов
    public void sortBySumGrades() {
        abiturients.sort(Comparator.comparingInt(Abiturient::getSumGrades).reversed());
    }

    // Сортировка по фамилии
    public void sortByLastName() {
        abiturients.sort(Comparator.comparing(Abiturient::getLastName));
    }

    // Получить статистику (ИСПРАВЛЕННЫЙ МЕТОД)
    public void printStatistics() {
        if (abiturients.isEmpty()) {
            System.out.println("Нет данных для статистики.");
            return;
        }

        // Используем IntSummaryStatistics вместо DoubleSummaryStatistics
        IntSummaryStatistics stats = abiturients.stream()
                .mapToInt(Abiturient::getSumGrades)
                .summaryStatistics();

        System.out.println("\n" + "=".repeat(80));
        System.out.println("СТАТИСТИКА ПО АБИТУРИЕНТАМ:");
        System.out.println("=".repeat(80));
        System.out.printf("Всего абитуриентов: %d%n", abiturients.size());
        System.out.printf("Минимальная сумма баллов: %d%n", stats.getMin());
        System.out.printf("Максимальная сумма баллов: %d%n", stats.getMax());
        System.out.printf("Средняя сумма баллов: %.2f%n", stats.getAverage());
        System.out.printf("Общая сумма баллов: %d%n", stats.getSum());
    }
}