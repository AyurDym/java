package com.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NumberReader numberReader = new NumberReader();

        System.out.println("=== Программа для обработки чисел с плавающей запятой ===\n");

        // Путь к файлу по умолчанию
        String defaultPath = "resources/numbers.txt";
        String filePath;

        System.out.print("Введите путь к файлу (Enter для использования " + defaultPath + "): ");
        filePath = scanner.nextLine().trim();

        if (filePath.isEmpty()) {
            filePath = defaultPath;
        }

        try {
            // Чтение и обработка файла
            System.out.println("\nЧтение файла: " + filePath);
            numberReader.readFromFile(filePath);

            // Вывод прочитанных чисел
            System.out.println("\nПрочитанные числа:");
            for (int i = 0; i < numberReader.getNumbers().size(); i++) {
                System.out.printf("%d) %.6f%n", i + 1, numberReader.getNumbers().get(i));
            }

            // Вычисление и вывод статистики
            NumberStats stats = numberReader.calculateStats();
            System.out.println("\n" + stats);

        } catch (FileNotFoundException e) {
            System.err.println("\nОШИБКА: " + e.getMessage());
            System.err.println("Пожалуйста, проверьте правильность пути к файлу.");
        } catch (InvalidNumberException e) {
            System.err.println("\nОШИБКА КОРРЕКТНОСТИ ДАННЫХ: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("\nОШИБКА ВВОДА-ВЫВОДА: " + e.getMessage());
        } catch (OutOfMemoryError e) {
            System.err.println("\nКРИТИЧЕСКАЯ ОШИБКА: Недостаточно памяти для обработки данных");
        } catch (Exception e) {
            System.err.println("\nНЕПРЕДВИДЕННАЯ ОШИБКА: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\nПрограмма завершена.");
        scanner.close();
    }
}