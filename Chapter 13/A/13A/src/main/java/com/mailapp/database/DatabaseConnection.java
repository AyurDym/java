package com.mailapp.database;

import java.sql.*;

public class DatabaseConnection {
    private static Connection connection = null;
    private static final String URL = "jdbc:sqlite:mail.db";

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
            createTables();
        }
        return connection;
    }

    private static void createTables() {
        String createPersonTable = """
            CREATE TABLE IF NOT EXISTS persons (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                full_name VARCHAR(100) NOT NULL,
                birth_date DATE NOT NULL
            )
        """;

        String createMailTable = """
            CREATE TABLE IF NOT EXISTS mails (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                sender_id INTEGER NOT NULL,
                receiver_id INTEGER NOT NULL,
                subject VARCHAR(200) NOT NULL,
                text TEXT NOT NULL,
                send_date DATETIME NOT NULL,
                FOREIGN KEY (sender_id) REFERENCES persons(id) ON DELETE CASCADE,
                FOREIGN KEY (receiver_id) REFERENCES persons(id) ON DELETE CASCADE
            )
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createPersonTable);
            stmt.execute(createMailTable);
            System.out.println("✓ Таблицы созданы/проверены");
        } catch (SQLException e) {
            System.err.println("Ошибка создания таблиц: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean testConnection() {
        try {
            getConnection();
            System.out.println("✓ Подключение к БД успешно");
            return true;
        } catch (SQLException e) {
            System.err.println("✗ Ошибка подключения: " + e.getMessage());
            return false;
        }
    }
}