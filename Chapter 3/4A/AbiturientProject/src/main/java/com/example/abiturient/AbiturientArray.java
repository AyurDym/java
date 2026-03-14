package com.example.abiturient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class AbiturientArray {
    private Abiturient[] abiturients;

    // Конструктор
    public AbiturientArray(int size) {
        this.abiturients = new Abiturient[size];
    }

    // Метод для добавления абитуриента по индексу
    public void setAbiturient(int index, Abiturient abiturient) {
        if (index >= 0 && index < abiturients.length) {
            abiturients[index] = abiturient;
        }
    }

    // Метод для получения абитуриента по индексу
    public Abiturient getAbiturient(int index) {
        if (index >= 0 && index < abiturients.length) {
            return abiturients[index];
        }
        return null;
    }

    // Метод для получения всего массива
    public Abiturient[] getAbiturients() {
        return abiturients;
    }

    // a) Список абитуриентов с неудовлетворительными оценками
    public List<Abiturient> getAbiturientsWithUnsatisfactoryGrades() {
        List<Abiturient> result = new ArrayList<>();
        for (Abiturient a : abiturients) {
            if (a != null && a.hasUnsatisfactoryGrades()) {
                result.add(a);
            }
        }
        return result;
    }

    // b) Список абитуриентов с суммой баллов выше заданной
    public List<Abiturient> getAbiturientsWithSumAbove(int minSum) {
        List<Abiturient> result = new ArrayList<>();
        for (Abiturient a : abiturients) {
            if (a != null && a.getSumGrades() > minSum) {
                result.add(a);
            }
        }
        return result;
    }

    // c) Выбрать заданное число n абитуриентов с самой высокой суммой баллов
    public List<Abiturient> getTopNAbiturients(int n) {
        // Сортируем по убыванию суммы баллов
        Abiturient[] sorted = Arrays.copyOf(abiturients, abiturients.length);
        Arrays.sort(sorted, (a1, a2) -> {
            if (a1 == null && a2 == null) return 0;
            if (a1 == null) return 1;
            if (a2 == null) return -1;
            return Integer.compare(a2.getSumGrades(), a1.getSumGrades());
        });

        List<Abiturient> result = new ArrayList<>();
        int count = 0;
        for (Abiturient a : sorted) {
            if (a != null && count < n) {
                result.add(a);
                count++;
            }
        }
        return result;
    }

    // Получить полупроходную сумму (минимальную сумму среди топ-N)
    public int getPassingScore(int n) {
        List<Abiturient> topN = getTopNAbiturients(n);
        if (topN.isEmpty()) return 0;

        // Находим минимальную сумму среди топ-N
        int minSum = topN.get(0).getSumGrades();
        for (Abiturient a : topN) {
            if (a.getSumGrades() < minSum) {
                minSum = a.getSumGrades();
            }
        }
        return minSum;
    }

    // Вывести всех абитуриентов
    public void printAll() {
        System.out.println("\n=== ПОЛНЫЙ СПИСОК АБИТУРИЕНТОВ ===");
        for (Abiturient a : abiturients) {
            if (a != null) {
                System.out.println(a);
            }
        }
    }

    // Вывести список абитуриентов
    public void printList(List<Abiturient> list, String title) {
        System.out.println("\n=== " + title + " ===");
        if (list.isEmpty()) {
            System.out.println("Список пуст");
        } else {
            for (Abiturient a : list) {
                System.out.println(a);
            }
        }
    }

    // Главный метод для демонстрации работы
    public static void main(String[] args) {
        // Создаем массив на 10 абитуриентов
        AbiturientArray array = new AbiturientArray(10);

        // Заполняем тестовыми данными
        array.setAbiturient(0, new Abiturient(1, "Иванов", "Иван", "Иванович",
                "ул. Ленина, 10", "+7(123)456-78-90", new int[]{5, 4, 5, 4, 5}));

        array.setAbiturient(1, new Abiturient(2, "Петров", "Петр", "Петрович",
                "ул. Гагарина, 5", "+7(234)567-89-01", new int[]{3, 4, 3, 4, 3}));

        array.setAbiturient(2, new Abiturient(3, "Сидоров", "Сидор", "Сидорович",
                "ул. Пушкина, 20", "+7(345)678-90-12", new int[]{5, 5, 5, 5, 5}));

        array.setAbiturient(3, new Abiturient(4, "Смирнов", "Алексей", "Алексеевич",
                "ул. Лермонтова, 15", "+7(456)789-01-23", new int[]{2, 3, 4, 3, 2}));

        array.setAbiturient(4, new Abiturient(5, "Кузнецов", "Дмитрий", "Дмитриевич",
                "ул. Толстого, 7", "+7(567)890-12-34", new int[]{4, 4, 5, 4, 4}));

        array.setAbiturient(5, new Abiturient(6, "Васильев", "Андрей", "Андреевич",
                "ул. Чехова, 3", "+7(678)901-23-45", new int[]{5, 5, 4, 5, 5}));

        array.setAbiturient(6, new Abiturient(7, "Попов", "Сергей", "Сергеевич",
                "ул. Горького, 12", "+7(789)012-34-56", new int[]{3, 3, 3, 4, 3}));

        array.setAbiturient(7, new Abiturient(8, "Новиков", "Максим", "Максимович",
                "ул. Достоевского, 8", "+7(890)123-45-67", new int[]{4, 4, 4, 4, 4}));

        array.setAbiturient(8, new Abiturient(9, "Федоров", "Артем", "Артемович",
                "ул. Тургенева, 25", "+7(901)234-56-78", new int[]{5, 3, 4, 5, 3}));

        array.setAbiturient(9, new Abiturient(10, "Морозов", "Игорь", "Игоревич",
                "ул. Гоголя, 30", "+7(012)345-67-89", new int[]{2, 2, 3, 2, 3}));

        // Выводим полный список
        array.printAll();

        // a) Список абитуриентов с неудовлетворительными оценками
        List<Abiturient> unsatisfactory = array.getAbiturientsWithUnsatisfactoryGrades();
        array.printList(unsatisfactory, "АБИТУРИЕНТЫ С НЕУДОВЛЕТВОРИТЕЛЬНЫМИ ОЦЕНКАМИ");

        // b) Список абитуриентов с суммой баллов выше 20
        List<Abiturient> aboveSum = array.getAbiturientsWithSumAbove(20);
        array.printList(aboveSum, "АБИТУРИЕНТЫ С СУММОЙ БАЛЛОВ ВЫШЕ 20");

        // c) Выбрать 3 абитуриентов с самой высокой суммой баллов
        int n = 3;
        List<Abiturient> topN = array.getTopNAbiturients(n);
        array.printList(topN, "ТОП-" + n + " АБИТУРИЕНТОВ С ВЫСОКОЙ СУММОЙ БАЛЛОВ");

        // Полупроходная сумма
        int passingScore = array.getPassingScore(n);
        System.out.println("\n=== ПОЛУПРОХОДНАЯ СУММА ===");
        System.out.println("Минимальная сумма среди топ-" + n + ": " + passingScore);

        // Абитуриенты с полупроходной суммой
        List<Abiturient> withPassingScore = new ArrayList<>();
        for (Abiturient a : array.getAbiturients()) {
            if (a != null && a.getSumGrades() >= passingScore) {
                withPassingScore.add(a);
            }
        }
        array.printList(withPassingScore, "АБИТУРИЕНТЫ С СУММОЙ БАЛЛОВ НЕ НИЖЕ " + passingScore);
    }
}