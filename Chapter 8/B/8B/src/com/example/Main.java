package com.example;

public class Main {
    public static void main(String[] args) {
        // Пример текста учебника по программированию
        String textbook = """
            Что такое переменная в программировании? Переменная - это именованная область памяти, 
            которая хранит значение. Как объявить переменную в Java? Для объявления переменной 
            нужно указать тип и имя. Например: int number = 10;
            
            Какие существуют типы данных? В Java есть примитивные и ссылочные типы. 
            Примитивные типы: byte, short, int, long, float, double, char, boolean.
            
            Что такое условный оператор if? Он позволяет выполнять код по условию.
            Как работает цикл for? Цикл for повторяет блок кода заданное количество раз.
            
            [listing]
            public class Example {
                public static void main(String[] args) {
                    int x = 5;
                    if (x > 0) {
                        System.out.println("Positive");
                    }
                }
            }
            [/listing]
            
            Что такое массив? Массив - это структура данных для хранения элементов одного типа.
            Как получить длину массива? Для этого используется свойство length.
            Что такое метод? Метод - это блок кода, который выполняет определенную задачу.
            """;

        TextProcessor processor = new TextProcessor();

        System.out.println("Программа обработки текста учебника по программированию");
        System.out.println("=" .repeat(60));

        // Разбор текста
        processor.parseText(textbook);

        // Вывод обработанного текста
        processor.printAllText();

        // Поиск слов заданной длины в вопросительных предложениях
        int targetLength = 5; // Например, слова длины 5
        processor.printWordsInInterrogativeSentences(targetLength);

        // Дополнительный пример с разными длинами слов
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("Дополнительные примеры:");
        System.out.println("=" .repeat(60));

        int[] lengthsToCheck = {3, 4, 5, 6, 7};
        for (int length : lengthsToCheck) {
            System.out.println("\n--- Слова длины " + length + " ---");
            processor.printWordsInInterrogativeSentences(length);
        }
    }
}