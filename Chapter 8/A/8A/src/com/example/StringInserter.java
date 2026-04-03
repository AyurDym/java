package com.example;

public class StringInserter {

    /**
     * Вставляет подстроку после k-го символа в исходной строке
     * @param original исходная строка
     * @param substring подстрока для вставки
     * @param k позиция после которой нужно вставить (k - количество символов)
     * @return новая строка с вставленной подстрокой
     */
    public static String insertAfterK(String original, String substring, int k) {
        // Проверка на null
        if (original == null || substring == null) {
            throw new IllegalArgumentException("Строки не могут быть null");
        }

        // Если k меньше 0, вставляем в начало
        if (k <= 0) {
            return substring + original;
        }

        // Если k больше или равно длине строки, вставляем в конец
        if (k >= original.length()) {
            return original + substring;
        }

        // Вставка после k-го символа
        return original.substring(0, k) + substring + original.substring(k);
    }

    public static void main(String[] args) {
        // Примеры использования
        System.out.println("=== Примеры вставки подстроки после k-го символа ===\n");

        // Пример 1: Базовая вставка
        String text1 = "HelloWorld";
        String result1 = insertAfterK(text1, "Beautiful", 5);
        System.out.printf("Исходная: %s%n", text1);
        System.out.printf("Вставка '%s' после %d-го символа: %s%n%n", "Beautiful", 5, result1);

        // Пример 2: Вставка в начало (k <= 0)
        String text2 = "Java Programming";
        String result2 = insertAfterK(text2, "Learn ", -1);
        System.out.printf("Исходная: %s%n", text2);
        System.out.printf("Вставка '%s' (k=-1, в начало): %s%n%n", "Learn ", result2);

        // Пример 3: Вставка в конец (k >= длины строки)
        String text3 = "Hello";
        String result3 = insertAfterK(text3, " World!", 10);
        System.out.printf("Исходная: %s%n", text3);
        System.out.printf("Вставка '%s' (k=10, в конец): %s%n%n", " World!", result3);

        // Пример 4: Работа с пробелами
        String text4 = "Hello how are you?";
        String result4 = insertAfterK(text4, "dear ", 6);
        System.out.printf("Исходная: %s%n", text4);
        System.out.printf("Вставка '%s' после %d-го символа: %s%n%n", "dear ", 6, result4);

        // Пример 5: Пустая подстрока
        String text5 = "TestString";
        String result5 = insertAfterK(text5, "", 4);
        System.out.printf("Исходная: %s%n", text5);
        System.out.printf("Вставка пустой строки после %d-го символа: %s%n%n", 4, result5);

        // Демонстрация с пользовательским вводом (раскомментировать для использования)
        // demoUserInput();
    }

    // Дополнительный метод для демонстрации с вводом от пользователя
    public static void demoUserInput() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        System.out.println("=== Демонстрация с пользовательским вводом ===");
        System.out.print("Введите исходную строку: ");
        String original = scanner.nextLine();

        System.out.print("Введите подстроку для вставки: ");
        String substring = scanner.nextLine();

        System.out.print("Введите позицию k (после какого символа вставить): ");
        int k = scanner.nextInt();

        String result = insertAfterK(original, substring, k);
        System.out.printf("Результат: %s%n", result);

        scanner.close();
    }
}