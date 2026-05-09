package com.mailapp.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Person {
    private Integer id;
    private String fullName;
    private LocalDate birthDate;

    public Person() {
    }

    public Person(String fullName, LocalDate birthDate) {
        this.fullName = fullName;
        this.birthDate = birthDate;
    }

    public Person(Integer id, String fullName, LocalDate birthDate) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
    }

    // Getters и Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthDateAsString() {
        return birthDate != null ? birthDate.toString() : "";
    }

    @Override
    public String toString() {
        return String.format("Person{id=%d, name='%s', birthDate=%s}",
                id, fullName, birthDate);
    }
}