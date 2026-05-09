package com.mailapp.services;

import com.mailapp.database.DatabaseConnection;
import com.mailapp.models.Person;
import com.mailapp.models.Mail;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MailUpdateService {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    // ==================== CRUD для Person ====================

    /**
     * Добавить нового пользователя
     */
    public Person addPerson(Person person) throws SQLException {
        String sql = "INSERT INTO persons (full_name, birth_date) VALUES (?, ?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, person.getFullName());
            pstmt.setString(2, person.getBirthDateAsString());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        person.setId(generatedKeys.getInt(1));
                        return person;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Обновить информацию о пользователе
     */
    public boolean updatePerson(Person person) throws SQLException {
        String sql = "UPDATE persons SET full_name = ?, birth_date = ? WHERE id = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, person.getFullName());
            pstmt.setString(2, person.getBirthDateAsString());
            pstmt.setInt(3, person.getId());

            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Удалить пользователя (письма удалятся автоматически благодаря CASCADE)
     */
    public boolean deletePerson(int personId) throws SQLException {
        String sql = "DELETE FROM persons WHERE id = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, personId);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Получить пользователя по ID
     */
    public Person getPersonById(int id) throws SQLException {
        String sql = "SELECT * FROM persons WHERE id = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Person(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            LocalDate.parse(rs.getString("birth_date"))
                    );
                }
            }
        }
        return null;
    }

    // ==================== CRUD для Mail ====================

    /**
     * Добавить новое письмо
     */
    public Mail addMail(Mail mail) throws SQLException {
        String sql = "INSERT INTO mails (sender_id, receiver_id, subject, text, send_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, mail.getSenderId());
            pstmt.setInt(2, mail.getReceiverId());
            pstmt.setString(3, mail.getSubject());
            pstmt.setString(4, mail.getText());
            pstmt.setString(5, mail.getSendDateAsString());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        mail.setId(generatedKeys.getInt(1));
                        return mail;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Обновить письмо
     */
    public boolean updateMail(Mail mail) throws SQLException {
        String sql = "UPDATE mails SET sender_id = ?, receiver_id = ?, subject = ?, text = ?, send_date = ? WHERE id = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, mail.getSenderId());
            pstmt.setInt(2, mail.getReceiverId());
            pstmt.setString(3, mail.getSubject());
            pstmt.setString(4, mail.getText());
            pstmt.setString(5, mail.getSendDateAsString());
            pstmt.setInt(6, mail.getId());

            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Удалить письмо
     */
    public boolean deleteMail(int mailId) throws SQLException {
        String sql = "DELETE FROM mails WHERE id = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, mailId);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Получить письмо по ID
     */
    public Mail getMailById(int id) throws SQLException {
        String sql = "SELECT * FROM mails WHERE id = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Mail(
                            rs.getInt("id"),
                            rs.getInt("sender_id"),
                            rs.getInt("receiver_id"),
                            rs.getString("subject"),
                            rs.getString("text"),
                            rs.getTimestamp("send_date") != null ?
                                    rs.getTimestamp("send_date").toLocalDateTime() : null
                    );
                }
            }
        }
        return null;
    }
}
