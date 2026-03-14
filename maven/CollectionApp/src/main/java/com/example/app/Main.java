package com.example.app;

import com.example.collectionlib.CollectionUtils;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Демонстрация работы библиотеки CollectionUtils ===\n");

        // Создаем коллекцию книг (теперь с использованием Lombok конструктора)
        List<Book> books = Arrays.asList(
                new Book("Война и мир", "Толстой", 1869, 500.0),
                new Book("Преступление и наказание", "Достоевский", 1866, 450.0),
                new Book("Анна Каренина", "Толстой", 1877, 480.0),
                new Book("Евгений Онегин", "Пушкин", 1833, 300.0),
                new Book("Мертвые души", "Гоголь", 1842, 350.0),
                new Book("Идиот", "Достоевский", 1869, 400.0)
        );

        // 1. Фильтрация (книги Толстого)
        System.out.println("1. Фильтрация (книги Толстого):");
        List<Book> tolstoyBooks = CollectionUtils.filter(books,
                book -> "Толстой".equals(book.getAuthor()));
        tolstoyBooks.forEach(System.out::println);
        System.out.println();

        // 2. Сортировка по году
        System.out.println("2. Сортировка по году:");
        List<Book> sortedByYear = CollectionUtils.sort(books,
                Comparator.comparingInt(Book::getYear));
        sortedByYear.forEach(System.out::println);
        System.out.println();

        // 3. Поиск самой дорогой книги
        System.out.println("3. Самая дорогая книга:");
        Book maxPriceBook = Collections.max(books,
                Comparator.comparingDouble(Book::getPrice));
        System.out.println(maxPriceBook);
        System.out.println();

        // 4. Поиск самой старой книги
        System.out.println("4. Самая старая книга:");
        Book oldestBook = Collections.min(books,
                Comparator.comparingInt(Book::getYear));
        System.out.println(oldestBook);
        System.out.println();

        // 5. Объединение коллекций
        System.out.println("5. Объединение коллекций:");
        List<Book> books1 = books.subList(0, 3);
        List<Book> books2 = books.subList(3, 6);
        List<Book> unitedBooks = CollectionUtils.union(books1, books2);
        System.out.println("Размер объединенной коллекции: " + unitedBooks.size());
        System.out.println();

        // 6. Разбиение на части
        System.out.println("6. Разбиение на части по 2 книги:");
        List<List<Book>> partitions = CollectionUtils.partition(books, 2);
        for (int i = 0; i < partitions.size(); i++) {
            System.out.println("Часть " + (i + 1) + ":");
            for (Book book : partitions.get(i)) {
                System.out.println("  - " + book.getTitle());
            }
        }
        System.out.println();

        // 7. Разделение по цене
        System.out.println("7. Разделение по цене (>400 руб):");
        Map<Boolean, List<Book>> splitByPrice = CollectionUtils.splitByPredicate(books,
                book -> book.getPrice() > 400);

        System.out.println("Дорогие книги (>400 руб):");
        List<Book> expensiveBooks = splitByPrice.get(true);
        if (expensiveBooks != null) {
            for (Book book : expensiveBooks) {
                System.out.println("  - " + book.getTitle() + ": " + book.getPrice());
            }
        }

        System.out.println("Дешевые книги (<=400 руб):");
        List<Book> cheapBooks = splitByPrice.get(false);
        if (cheapBooks != null) {
            for (Book book : cheapBooks) {
                System.out.println("  - " + book.getTitle() + ": " + book.getPrice());
            }
        }
    }
}
