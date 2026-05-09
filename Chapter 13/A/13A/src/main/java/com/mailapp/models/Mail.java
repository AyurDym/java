package com.mailapp.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mail {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String subject;
    private String text;
    private LocalDateTime sendDate;

    public Mail() {
    }

    public Mail(Integer senderId, Integer receiverId, String subject,
                String text, LocalDateTime sendDate) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.subject = subject;
        this.text = text;
        this.sendDate = sendDate;
    }

    public Mail(Integer id, Integer senderId, Integer receiverId,
                String subject, String text, LocalDateTime sendDate) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.subject = subject;
        this.text = text;
        this.sendDate = sendDate;
    }

    // Getters и Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendDateAsString() {
        return sendDate != null ? sendDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "";
    }

    public int getTextLength() {
        return text != null ? text.length() : 0;
    }

    @Override
    public String toString() {
        return String.format("Mail{id=%d, from=%d, to=%d, subject='%s', length=%d}",
                id, senderId, receiverId, subject, getTextLength());
    }
}