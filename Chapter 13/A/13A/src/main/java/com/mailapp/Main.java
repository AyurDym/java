package com.mailapp;

import com.mailapp.database.DatabaseConnection;
import com.mailapp.models.Person;
import com.mailapp.models.Mail;
import com.mailapp.services.MailQueryService;
import com.mailapp.services.MailUpdateService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        MailQueryService queryService = new MailQueryService();
        MailUpdateService updateService = new MailUpdateService();

        try {
            System.out.println("=== НАЧАЛО РАБОТЫ ПРОГРАММЫ ===\n");

            // Проверка подключения
            System.out.println("Проверка подключения к БД...");
            DatabaseConnection.testConnection();
            System.out.println("Подключение успешно!\n");

            // Очистка старых данных
            clearAllData(updateService, queryService);

            // Добавление тестовых данных
            addTestData(updateService);

            // Выполнение заданий
            System.out.println("\n=== ЗАДАНИЕ 1 ===");
            task1(queryService);

            System.out.println("\n=== ЗАДАНИЕ 2 ===");
            task2(queryService);

            System.out.println("\n=== ЗАДАНИЕ 3 ===");
            task3(queryService);

            System.out.println("\n=== ЗАДАНИЕ 4 ===");
            task4(queryService);

        } catch (SQLException e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection();
            System.out.println("\n=== ПРОГРАММА ЗАВЕРШЕНА ===");
        }
    }

    private static void clearAllData(MailUpdateService updateService, MailQueryService queryService) throws SQLException {
        System.out.println("Очистка старых данных...");
        List<Map<String, Object>> users = queryService.getUserMailStatistics();
        for (Map<String, Object> user : users) {
            Person p = (Person) user.get("person");
            if (p != null && p.getId() != null) {
                updateService.deletePerson(p.getId());
            }
        }
        System.out.println("Очистка завершена\n");
    }

    private static void addTestData(MailUpdateService updateService) throws SQLException {
        System.out.println("Добавление тестовых данных...");

        // Создаем пользователей
        Person alice = updateService.addPerson(new Person("Alice Johnson", LocalDate.of(1990, 5, 15)));
        Person bob = updateService.addPerson(new Person("Bob Smith", LocalDate.of(1988, 11, 22)));
        Person charlie = updateService.addPerson(new Person("Charlie Brown", LocalDate.of(1995, 3, 10)));
        Person diana = updateService.addPerson(new Person("Diana Prince", LocalDate.of(1992, 7, 4)));

        System.out.println("Добавлены пользователи:");
        System.out.println("  - " + alice);
        System.out.println("  - " + bob);
        System.out.println("  - " + charlie);
        System.out.println("  - " + diana);

        // Создаем письма
        updateService.addMail(new Mail(alice.getId(), bob.getId(), "Java Programming", "Learning Java is fun!", LocalDateTime.of(2024, 1, 10, 10, 30)));
        updateService.addMail(new Mail(bob.getId(), alice.getId(), "Python", "Python is great!", LocalDateTime.of(2024, 1, 11, 14, 15)));
        updateService.addMail(new Mail(alice.getId(), charlie.getId(), "Java", "Hi", LocalDateTime.of(2024, 1, 12, 9, 0)));
        updateService.addMail(new Mail(charlie.getId(), alice.getId(), "SQL", "Database design", LocalDateTime.of(2024, 1, 13, 16, 45)));
        updateService.addMail(new Mail(diana.getId(), bob.getId(), "Java", "Spring Boot tutorial", LocalDateTime.of(2024, 1, 14, 11, 20)));
        updateService.addMail(new Mail(bob.getId(), diana.getId(), "JavaScript", "React hooks", LocalDateTime.of(2024, 1, 15, 13, 10)));
        updateService.addMail(new Mail(charlie.getId(), diana.getId(), "Java", "Multithreading in Java", LocalDateTime.of(2024, 1, 16, 15, 30)));

        System.out.println("Добавлено 7 писем\n");
    }

    private static void task1(MailQueryService queryService) throws SQLException {
        System.out.println("Пользователь, у которого наименьшая общая длина писем:");
        Person user = queryService.findUserWithShortestMails();
        if (user != null) {
            System.out.println("  Результат: " + user.getFullName());
        } else {
            System.out.println("  Данные не найдены");
        }
    }

    private static void task2(MailQueryService queryService) throws SQLException {
        System.out.println("Статистика по пользователям:");
        List<Map<String, Object>> stats = queryService.getUserMailStatistics();

        System.out.println("  +----+----------------------+------------+----------+----------+");
        System.out.println("  | ID | ФИО                   | Дата рожд. | Отправ.  | Получ.   |");
        System.out.println("  +----+----------------------+------------+----------+----------+");

        for (Map<String, Object> stat : stats) {
            Person p = (Person) stat.get("person");
            System.out.printf("  | %2d | %-20s | %-10s | %8d | %8d |\n",
                    p.getId(),
                    p.getFullName().length() > 20 ? p.getFullName().substring(0, 17) + "..." : p.getFullName(),
                    p.getBirthDate(),
                    stat.get("sentCount"),
                    stat.get("receivedCount"));
        }

        System.out.println("  +----+----------------------+------------+----------+----------+");
    }

    private static void task3(MailQueryService queryService) throws SQLException {
        String subject = "Java";
        System.out.println("Пользователи, получившие письма с темой '" + subject + "':");
        List<Person> users = queryService.getUsersWhoReceivedBySubject(subject);

        if (users.isEmpty()) {
            System.out.println("  Нет таких пользователей");
        } else {
            for (Person p : users) {
                System.out.println("  - " + p.getFullName());
            }
        }
    }

    private static void task4(MailQueryService queryService) throws SQLException {
        String subject = "Java";
        System.out.println("Пользователи, НЕ получившие письма с темой '" + subject + "':");
        List<Person> users = queryService.getUsersWhoNeverReceivedBySubject(subject);

        if (users.isEmpty()) {
            System.out.println("  Нет таких пользователей");
        } else {
            for (Person p : users) {
                System.out.println("  - " + p.getFullName());
            }
        }
    }
}
