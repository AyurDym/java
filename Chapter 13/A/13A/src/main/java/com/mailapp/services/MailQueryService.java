package com.mailapp.services;

import com.mailapp.database.DatabaseConnection;
import com.mailapp.models.Person;
import com.mailapp.models.Mail;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class MailQueryService {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    /**
     * Задание 1: Найти пользователя, длина писем которого наименьшая
     */
    public Person findUserWithShortestMails() throws SQLException {
        String sql = """
            SELECT p.id, p.full_name, p.birth_date,
                   SUM(LENGTH(m.text)) as total_length
            FROM persons p
            INNER JOIN mails m ON p.id = m.sender_id
            GROUP BY p.id, p.full_name, p.birth_date
            ORDER BY total_length ASC
            LIMIT 1
        """;

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return new Person(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        LocalDate.parse(rs.getString("birth_date"))
                );
            }
        }
        return null;
    }

    /**
     * Задание 2: Вывести информацию о пользователях,
     * а также количестве полученных и отправленных ими письмах
     */
    public List<Map<String, Object>> getUserMailStatistics() throws SQLException {
        String sql = """
            SELECT 
                p.id,
                p.full_name,
                p.birth_date,
                COALESCE(sent.sent_count, 0) as sent_count,
                COALESCE(received.received_count, 0) as received_count
            FROM persons p
            LEFT JOIN (
                SELECT sender_id, COUNT(*) as sent_count
                FROM mails
                GROUP BY sender_id
            ) sent ON p.id = sent.sender_id
            LEFT JOIN (
                SELECT receiver_id, COUNT(*) as received_count
                FROM mails
                GROUP BY receiver_id
            ) received ON p.id = received.receiver_id
            ORDER BY p.id
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> stats = new HashMap<>();

                Person person = new Person(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        LocalDate.parse(rs.getString("birth_date"))
                );

                stats.put("person", person);
                stats.put("sentCount", rs.getInt("sent_count"));
                stats.put("receivedCount", rs.getInt("received_count"));

                result.add(stats);
            }
        }
        return result;
    }

    /**
     * Задание 3: Вывести информацию о пользователях,
     * которые получили хотя бы одно сообщение с заданной темой
     */
    public List<Person> getUsersWhoReceivedBySubject(String subject) throws SQLException {
        String sql = """
            SELECT DISTINCT p.id, p.full_name, p.birth_date
            FROM persons p
            INNER JOIN mails m ON p.id = m.receiver_id
            WHERE LOWER(m.subject) = LOWER(?)
            ORDER BY p.id
        """;

        List<Person> users = new ArrayList<>();

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, subject);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new Person(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            LocalDate.parse(rs.getString("birth_date"))
                    ));
                }
            }
        }
        return users;
    }

    /**
     * Задание 4: Вывести информацию о пользователях,
     * которые не получали сообщения с заданной темой
     */
    public List<Person> getUsersWhoNeverReceivedBySubject(String subject) throws SQLException {
        String sql = """
            SELECT p.id, p.full_name, p.birth_date
            FROM persons p
            WHERE p.id NOT IN (
                SELECT DISTINCT m.receiver_id
                FROM mails m
                WHERE LOWER(m.subject) = LOWER(?)
            )
            ORDER BY p.id
        """;

        List<Person> users = new ArrayList<>();

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, subject);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new Person(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            LocalDate.parse(rs.getString("birth_date"))
                    ));
                }
            }
        }
        return users;
    }

    /**
     * Получить все письма пользователя
     */
    public List<Mail> getMailsByUser(Integer userId, boolean asSender) throws SQLException {
        String sql;
        if (asSender) {
            sql = "SELECT * FROM mails WHERE sender_id = ? ORDER BY send_date DESC";
        } else {
            sql = "SELECT * FROM mails WHERE receiver_id = ? ORDER BY send_date DESC";
        }

        List<Mail> mails = new ArrayList<>();

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    mails.add(new Mail(
                            rs.getInt("id"),
                            rs.getInt("sender_id"),
                            rs.getInt("receiver_id"),
                            rs.getString("subject"),
                            rs.getString("text"),
                            rs.getTimestamp("send_date") != null ?
                                    rs.getTimestamp("send_date").toLocalDateTime() : null
                    ));
                }
            }
        }
        return mails;
    }
}
