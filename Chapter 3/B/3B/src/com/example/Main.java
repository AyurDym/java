package com.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("РАБОТА С ПОЛИНОМАМИ");
        System.out.println("=".repeat(80));

        // Демонстрация создания полиномов разными способами
        demonstrateCreation();

        // Демонстрация арифметических операций
        demonstrateArithmetic();

        // Основное задание: массив из m полиномов и их сумма
        demonstrateArraySum();

        // Дополнительно: список полиномов
        demonstrateListSum();

        // Дополнительно: множество полиномов
        demonstrateSetSum();

        // Дополнительные операции
        demonstrateAdditionalOperations();
    }

    private static void demonstrateCreation() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("1. СОЗДАНИЕ ПОЛИНОМОВ РАЗЛИЧНЫМИ КОНСТРУКТОРАМИ");
        System.out.println("=".repeat(80));

        // Конструктор 1: из массива коэффициентов
        Polynomial p1 = new Polynomial(new double[]{5, -3, 2}); // 5 - 3x + 2x^2
        System.out.println("Конструктор 1 (из массива): " + p1);

        // Конструктор 2: из Map
        Map<Integer, Double> coeffs = new HashMap<>();
        coeffs.put(0, 3.0);
        coeffs.put(2, 4.0);
        coeffs.put(3, 1.0);
        Polynomial p2 = new Polynomial(coeffs);
        System.out.println("Конструктор 2 (из Map): " + p2);

        // Конструктор 3: один член
        Polynomial p3 = new Polynomial(4, 2.5);
        System.out.println("Конструктор 3 (один член): " + p3);

        // Конструктор 4: копирование
        Polynomial p4 = new Polynomial(p1);
        System.out.println("Конструктор 4 (копия p1): " + p4);

        // Конструктор 5: из строки
        Polynomial p5 = new Polynomial("2x^3 + 4x^2 - 3x + 5");
        System.out.println("Конструктор 5 (из строки): " + p5);

        // Проверка равенства
        System.out.println("\np1 равен p4? " + p1.equals(p4));
    }

    private static void demonstrateArithmetic() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("2. АРИФМЕТИЧЕСКИЕ ОПЕРАЦИИ С ПОЛИНОМАМИ");
        System.out.println("=".repeat(80));

        Polynomial a = new Polynomial(new double[]{1, 2, 1}); // 1 + 2x + x^2
        Polynomial b = new Polynomial(new double[]{1, -1});    // 1 - x

        System.out.println("A(x) = " + a);
        System.out.println("B(x) = " + b);

        // Сложение
        Polynomial sum = a.add(b);
        System.out.println("\nСложение A(x) + B(x) = " + sum);

        // Вычитание
        Polynomial diff = a.subtract(b);
        System.out.println("Вычитание A(x) - B(x) = " + diff);

        // Умножение
        Polynomial product = a.multiply(b);
        System.out.println("Умножение A(x) * B(x) = " + product);

        // Деление
        System.out.println("\nДеление A(x) / B(x):");
        Polynomial[] division = a.divide(b);
        System.out.println("  Частное: " + division[0]);
        System.out.println("  Остаток: " + division[1]);

        // Проверка: A = B * Q + R
        Polynomial check = b.multiply(division[0]).add(division[1]);
        System.out.println("  Проверка: B * Q + R = " + check);
        System.out.println("  Результат совпадает с A: " + a.equals(check));

        // Операции с числами
        System.out.println("\nОперации с числами:");
        System.out.println("A(x) + 5 = " + a.add(5));
        System.out.println("A(x) * 3 = " + a.multiply(3));

        // Вычисление значения
        double x = 2;
        System.out.println("\nЗначение A(2) = " + a.evaluate(x));
    }

    private static void demonstrateArraySum() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("3. МАССИВ ПОЛИНОМОВ И ИХ СУММА (ОСНОВНОЕ ЗАДАНИЕ)");
        System.out.println("=".repeat(80));

        // Создаем массив из m полиномов
        int m = 5;
        Polynomial[] polynomials = new Polynomial[m];

        polynomials[0] = new Polynomial(new double[]{2, 3, 1});     // 2 + 3x + x^2
        polynomials[1] = new Polynomial(new double[]{1, -1, 2});   // 1 - x + 2x^2
        polynomials[2] = new Polynomial(new double[]{-1, 4});      // -1 + 4x
        polynomials[3] = new Polynomial(3, 2);                      // 2x^3
        polynomials[4] = new Polynomial("x^2 + 3x - 5");            // x^2 + 3x - 5

        // Выводим массив полиномов
        System.out.println("\nМассив из " + m + " полиномов:");
        for (int i = 0; i < polynomials.length; i++) {
            System.out.printf("P%d(x) = %s%n", i + 1, polynomials[i]);
        }

        // Находим сумму всех полиномов в массиве
        Polynomial sum = PolynomialUtils.sumOfPolynomials(polynomials);

        System.out.println("\n" + "-".repeat(80));
        System.out.println("СУММА ВСЕХ ПОЛИНОМОВ МАССИВА:");
        System.out.println("-".repeat(80));
        System.out.println("S(x) = " + sum);

        // Пошаговое вычисление суммы для проверки
        System.out.println("\nПошаговое вычисление суммы:");
        Polynomial stepSum = new Polynomial(polynomials[0]);
        System.out.printf("Шаг 1: S = %s%n", stepSum);

        for (int i = 1; i < polynomials.length; i++) {
            stepSum = stepSum.add(polynomials[i]);
            System.out.printf("Шаг %d: S = %s (добавили P%d)%n",
                    i + 1, stepSum, i + 1);
        }
    }

    private static void demonstrateListSum() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("4. СПИСОК ПОЛИНОМОВ И ИХ СУММА");
        System.out.println("=".repeat(80));

        List<Polynomial> polynomialList = new ArrayList<>();
        polynomialList.add(new Polynomial(new double[]{1, 0, 1}));   // 1 + x^2
        polynomialList.add(new Polynomial(new double[]{0, 2, 0, 1})); // 2x + x^3
        polynomialList.add(new Polynomial("3x^2 - 2x + 4"));          // 3x^2 - 2x + 4

        PolynomialUtils.printPolynomials(polynomialList, "Список полиномов");

        Polynomial sum = PolynomialUtils.sumOfPolynomials(polynomialList);
        System.out.println("\nСумма полиномов списка: " + sum);
    }

    private static void demonstrateSetSum() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("5. МНОЖЕСТВО ПОЛИНОМОВ И ИХ СУММА");
        System.out.println("=".repeat(80));

        Set<Polynomial> polynomialSet = new HashSet<>();
        polynomialSet.add(new Polynomial(new double[]{5}));           // 5
        polynomialSet.add(new Polynomial(new double[]{-3, 2}));       // -3 + 2x
        polynomialSet.add(new Polynomial("4x^2 - x + 7"));            // 4x^2 - x + 7
        polynomialSet.add(new Polynomial(1, 6));                      // 6x

        System.out.println("Множество полиномов:");
        int index = 1;
        for (Polynomial p : polynomialSet) {
            System.out.printf("P%d(x) = %s%n", index++, p);
        }

        Polynomial sum = PolynomialUtils.sumOfPolynomials(polynomialSet);
        System.out.println("\nСумма полиномов множества: " + sum);
    }

    private static void demonstrateAdditionalOperations() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("6. ДОПОЛНИТЕЛЬНЫЕ ОПЕРАЦИИ");
        System.out.println("=".repeat(80));

        // Создание полинома по корням
        Polynomial fromRoots = PolynomialUtils.fromRoots(1, 2, -3);
        System.out.println("Полином с корнями {1, 2, -3}:");
        System.out.println("  P(x) = (x-1)(x-2)(x+3) = " + fromRoots);

        // Проверка корней
        System.out.println("  Проверка: P(1) = " + fromRoots.evaluate(1));
        System.out.println("  P(2) = " + fromRoots.evaluate(2));
        System.out.println("  P(-3) = " + fromRoots.evaluate(-3));

        // Произведение полиномов из массива
        Polynomial[] polyArray = {
                new Polynomial(new double[]{1, 1}),     // 1 + x
                new Polynomial(new double[]{1, -1}),    // 1 - x
                new Polynomial(new double[]{1, 0, 1})   // 1 + x^2
        };

        System.out.println("\nПроизведение полиномов:");
        for (Polynomial p : polyArray) {
            System.out.println("  " + p);
        }

        Polynomial product = PolynomialUtils.productOfPolynomials(polyArray);
        System.out.println("Результат: " + product);

        // Демонстрация сложных операций
        System.out.println("\n" + "-".repeat(80));
        System.out.println("Демонстрация сложных арифметических выражений:");
        System.out.println("-".repeat(80));

        Polynomial p = new Polynomial(new double[]{1, 2, 3}); // 1 + 2x + 3x^2
        Polynomial q = new Polynomial(new double[]{2, -1});   // 2 - x
        Polynomial r = new Polynomial("x^2 - 4");              // x^2 - 4

        System.out.printf("P(x) = %s%n", p);
        System.out.printf("Q(x) = %s%n", q);
        System.out.printf("R(x) = %s%n", r);

        // (P + Q) * R
        Polynomial result = p.add(q).multiply(r);
        System.out.printf("%n(P + Q) * R = %s%n", result);

        // Проверка дистрибутивности: P*R + Q*R
        Polynomial check = p.multiply(r).add(q.multiply(r));
        System.out.printf("P*R + Q*R = %s%n", check);
        System.out.println("Результаты совпадают: " + result.equals(check));
    }
}