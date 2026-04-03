package com.example;

public class Main {
    public static void main(String[] args) {
        // Пример текста для анализа
        String text = """
            Java является объектно-ориентированным языком программирования. 
            Объектно-ориентированное программирование использует классы и объекты. 
            Python также поддерживает объектно-ориентированное программирование. 
            Машинное обучение часто использует Python. 
            Java широко применяется в корпоративной разработке. 
            Функциональное программирование отличается от объектно-ориентированного.
            """;

        TextAnalyzer analyzer = new TextAnalyzer(text);
        analyzer.printResult();

        // Второй пример для демонстрации
        System.out.println("\n\n");
        System.out.println("=" .repeat(80));
        System.out.println("ВТОРОЙ ПРИМЕР (сложный случай)");
        System.out.println("=" .repeat(80));

        String text2 = """
            Кошка ловит мышь. 
            Собака лает на кошку. 
            Мышь боится кошку. 
            Птица летает высоко. 
            Собака охраняет дом. 
            Рыба плавает в воде.
            """;

        TextAnalyzer analyzer2 = new TextAnalyzer(text2);
        analyzer2.printResult();

        // Третий пример с пользовательским вводом
        System.out.println("\n\n");
        System.out.println("=" .repeat(80));
        System.out.println("ПРИМЕР С РУЧНЫМ ВВОДОМ");
        System.out.println("=" .repeat(80));

        demoUserInput();
    }

    private static void demoUserInput() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        System.out.println("Введите текст для анализа (несколько предложений):");
        System.out.println("(Для завершения ввода введите пустую строку)");

        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            sb.append(line).append(" ");
        }

        if (sb.length() > 0) {
            TextAnalyzer analyzer = new TextAnalyzer(sb.toString());
            analyzer.printResult();
        } else {
            System.out.println("Текст не введён.");
        }

        scanner.close();
    }
}
