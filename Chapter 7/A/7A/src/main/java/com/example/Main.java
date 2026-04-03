package main.java.com.example;

public class Main {
    public static void main(String[] args) {
        // Реализация функционального интерфейса через лямбда-выражение
        DivisibleChecker divisibleBy31 = (number) -> number % 31 == 0;

        // Тестирование
        int[] testNumbers = {31, 62, 93, 124, 155, 30, 32, 0, -31, -62};

        System.out.println("Проверка деления на 31:");
        System.out.println("-----------------------");

        for (int num : testNumbers) {
            System.out.printf("%d делится на 31: %b%n",
                    num, divisibleBy31.check(num));
        }
    }
}